package org.softRoad.models.query;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class HqlNativeQuery
{
    private final EntityManager entityManager;
    private HqlQuery baseQuery;
    private final List<HqlQuery> filters = new ArrayList<>();
    private SearchCriteria searchCriteria;
    private final Class<?> resultClass;

    HqlNativeQuery(EntityManager entityManager, Class<?> resultClass)
    {
        this.entityManager = entityManager;
        this.resultClass = resultClass;
    }

    public HqlNativeQuery baseQuery(HqlQuery hqlQuery)
    {
        this.baseQuery = hqlQuery;
        return this;
    }

    public HqlNativeQuery addFilter(HqlQuery hqlQuery)
    {
        filters.add(hqlQuery);
        return this;
    }

    public HqlNativeQuery searchCriteria(SearchCriteria searchCriteria)
    {
        this.searchCriteria = searchCriteria;
        return this;
    }

    public Query build()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(baseQuery.getHql()).append("");
        if (searchCriteria != null && searchCriteria.getCondition() != null)
            filters.add(0, QueryUtils.getConditionHqlQuery(searchCriteria.getCondition(), resultClass));

        String filterStr = filters.stream()
                .map((q) -> q.getHql())
                .collect(Collectors.joining(" and "));

        if (!Strings.isNullOrEmpty(filterStr))
            sb.append(" where ").append(filterStr).append(" ");

        if (searchCriteria != null && !searchCriteria.getSearchOrders().isEmpty())
            sb.append(QueryUtils.getOrderBySql(searchCriteria.getSearchOrders(), resultClass)).append(" ");

        Query query = entityManager.createNativeQuery(sb.toString(), resultClass);

        baseQuery.getParams().entrySet().forEach(e -> query.setParameter(e.getKey(), e.getValue()));
        filters.forEach(f -> f.getParams().entrySet().forEach(e -> query.setParameter(e.getKey(), e.getValue())));

        if (searchCriteria != null) {
            query.setFirstResult(searchCriteria.getOffset());
            query.setMaxResults(searchCriteria.getPageSize());
        }

        return query;
    }
}
