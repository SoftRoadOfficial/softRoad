package org.softRoad.models.query;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.softRoad.utils.ModelUtils;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

public class QueryUtils
{

    @SuppressWarnings("AccessStaticViaInstance")
    public static Sort getSort(SearchCriteria searchCriteria)
    {
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
     *
     * @param searchCriteria
     * @return page
     */
    public static Page getPage(SearchCriteria searchCriteria)
    {
        return Page.of(searchCriteria.getOffset() / searchCriteria.getPageSize(), searchCriteria.getPageSize());
    }

    public static String getOrderBySql(List<SearchOrder> orders, Class<?> aClass)
    {
        if (orders.isEmpty())
            return "";
        return " order by " + orders.stream()
                .map(searchOrder -> ModelUtils.getColumnName(searchOrder.getField(), aClass)
                + (searchOrder.isDescending() ? " DESC" : " ASC"))
                .collect(Collectors.joining(", "));
    }

    public static HqlQuery getConditionHqlWhereQuery(SearchCondition searchCondition, Class<?> objClass)
    {
        HqlQuery hqlQuery = getConditionHqlQuery(searchCondition, objClass);
        if (!hqlQuery.isEmpty())
            hqlQuery.setHql(" WHERE " + hqlQuery.getHql());
        return hqlQuery;
    }

    public static HqlQuery getConditionHqlWhereQuery(SearchCondition searchCondition, Class<?> objClass,
            HqlQuery additionalFilter)
    {
        HqlQuery hqlQuery = getConditionHqlQuery(searchCondition, objClass, additionalFilter);
        if (!hqlQuery.isEmpty())
            hqlQuery.setHql(" WHERE " + hqlQuery.getHql());
        return hqlQuery;
    }

    public static HqlQuery getConditionHqlQuery(SearchCondition searchCondition, Class<?> objClass,
            HqlQuery additionalFilter)
    {
        HqlQuery hqlQuery = getConditionHqlQuery(searchCondition, objClass);
        if (hqlQuery.isEmpty())
            return additionalFilter;
        if (!additionalFilter.isEmpty()) {
            hqlQuery.setHql(additionalFilter.getHql() + " and " + hqlQuery.getHql());
            hqlQuery.getParams().putAll(additionalFilter.getParams());
        }
        return hqlQuery;
    }

    public static HqlQuery getConditionHqlQuery(SearchCondition searchCondition, Class<?> objClass)
    {
        return doGetConditionHqlQuery(searchCondition, objClass, 0);
    }

    private static HqlQuery doGetConditionHqlQuery(SearchCondition searchCondition, Class<?> objClass, Integer index)
    {
        HqlQuery hqlQuery = new HqlQuery();
        if (searchCondition instanceof SimpleCondition) {
            SimpleCondition simpleCondition = (SimpleCondition) searchCondition;
            String tableName = ModelUtils.getTableName(objClass);
            String columnName = ModelUtils.getColumnName(simpleCondition.getField(), objClass);
            String fieldName = simpleCondition.getField() + "_" + index;
            hqlQuery.setHql(" " + tableName + "." + columnName + " " + simpleCondition.getOperator().getSymbol()
                    + " :" + fieldName);
            hqlQuery.getParams().put(fieldName, simpleCondition.getOperator() != SimpleCondition.Operator.LIKE
                    ? simpleCondition.getValue() : "%" + simpleCondition.getValue() + "%");
        } else if (searchCondition instanceof ComplexCondition) {
            ComplexCondition complexCondition = (ComplexCondition) searchCondition;
            List<SearchCondition> conditions = complexCondition.getConditions();
            StringBuilder hqlResult = new StringBuilder();
            hqlResult.append("(");
            for (int i = 0; i < conditions.size(); i++) {
                HqlQuery result = doGetConditionHqlQuery(conditions.get(i), objClass, index);
                hqlQuery.getParams().putAll(result.getParams());
                index += result.getParams().size();
                if (i == 0)
                    hqlResult.append(result.getHql());
                else
                    hqlResult.append(" ").append(complexCondition.getOperator()).append(" ").append(result.getHql());
            }
            hqlResult.append(")");
            hqlQuery.setHql(hqlResult.toString());
        }
        return hqlQuery;
    }

    public static HqlNativeQuery nativeQuery(EntityManager entityManager, Class<?> resultClass)
    {
        return new HqlNativeQuery(entityManager, resultClass);
    }

    public static String fields(Class objClass, String fieldName, String... fieldNames)
    {
        String table = ModelUtils.getTableName(objClass);
        StringBuilder res = new StringBuilder(table + "." + fieldName);
        for (String name : fieldNames) {
            res.append(", ").append(table).append(".").append(name);
        }
        return res.toString();
    }
}
