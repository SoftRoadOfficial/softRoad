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

    public static SearchConditionSqlResult getSqlResult(SearchCondition searchCondition, Class<?> objClass) {
        return new SearchConditionSqlResult();
    }
}
