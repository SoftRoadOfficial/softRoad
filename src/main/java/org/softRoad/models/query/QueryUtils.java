package org.softRoad.models.query;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.softRoad.utils.ModelUtils;

import java.util.List;
import java.util.stream.Collectors;

public class QueryUtils {

    @SuppressWarnings("AccessStaticViaInstance")
    public static Sort getSort(SearchCriteria searchCriteria) {
        List<SearchOrder> orders = searchCriteria.getSearchOrders();
        Sort sort = null;
        if (!orders.isEmpty()) {
            String[] des = (String[]) orders.stream()
                    .filter(SearchOrder::isDescending)
                    .map(SearchOrder::getField).toArray();
            String[] asc = (String[]) orders.stream()
                    .filter(searchOrder -> !searchOrder.isDescending())
                    .map(SearchOrder::getField).toArray();
            sort = Sort.ascending(asc).descending(des);
        }
        return sort;
    }

    /**
     * Works for pageBased offset only
     */
    public static Page getPage(SearchCriteria searchCriteria) {
        return Page.of(searchCriteria.getOffset() / searchCriteria.getPageSize(), searchCriteria.getPageSize());
    }

    public static String getOrderBySql(SearchCriteria searchCriteria, Class<?> aClass) {
        List<SearchOrder> orders = searchCriteria.getSearchOrders();
        if (orders.isEmpty())
            return null;
        return " order by " + orders.stream()
                .map(searchOrder -> ModelUtils.getColumnName(searchOrder.getField(), aClass)
                        + (searchOrder.isDescending() ? " DESC" : " ASC"))
                .collect(Collectors.joining(", "));
    }

    public static String addOrderBy(String query, SearchCriteria searchCriteria, Class<?> aClass) {
        String orderBySql = getOrderBySql(searchCriteria, aClass);
        if (orderBySql != null)
            query = query + orderBySql;
        return query;
    }

    public static SearchConditionHqlQuery getConditionHqlWhereQuery (SearchCondition searchCondition, Class<?> objClass){
        SearchConditionHqlQuery conditionHqlQuery = getConditionHqlQuery(searchCondition, objClass);
        conditionHqlQuery.setSql(" WHERE " + conditionHqlQuery.getSql());
        return conditionHqlQuery;
    }

    public static SearchConditionHqlQuery getConditionHqlQuery(SearchCondition searchCondition, Class<?> objClass) {
        return doGetConditionHqlQuery(searchCondition, objClass, 0);
    }

    private static SearchConditionHqlQuery doGetConditionHqlQuery(SearchCondition searchCondition, Class<?> objClass, Integer index) {
        SearchConditionHqlQuery searchConditionHqlQuery = new SearchConditionHqlQuery();
        if (searchCondition instanceof SimpleCondition) {
            SimpleCondition simpleCondition = (SimpleCondition) searchCondition;
            String columnName = ModelUtils.getColumnName(simpleCondition.getField(), objClass);
            String fieldName = simpleCondition.getField() + "_" + index.toString();
            searchConditionHqlQuery.setSql(columnName + simpleCondition.getOperator().getSymbol() + ":" + fieldName);
            searchConditionHqlQuery.getParams().put(fieldName, simpleCondition.getValue());
        } else if (searchCondition instanceof ComplexCondition) {
            ComplexCondition complexCondition = (ComplexCondition) searchCondition;
            List<SearchCondition> conditions = complexCondition.getConditions();
            StringBuilder sqlResult = new StringBuilder();
            sqlResult.append("(");
            for (int i = 0; i < conditions.size(); i++) {
                SearchConditionHqlQuery result = doGetConditionHqlQuery(conditions.get(i), objClass, index);
                searchConditionHqlQuery.getParams().putAll(result.getParams());
                index += result.getParams().size();
                if (i == 0)
                    sqlResult.append(result.getSql());
                else
                    sqlResult.append(" ").append(complexCondition.getOperator()).append(" ").append(result.getSql());
            }
            sqlResult.append(")");
            searchConditionHqlQuery.setSql(sqlResult.toString());
        }
        return searchConditionHqlQuery;
    }
}
