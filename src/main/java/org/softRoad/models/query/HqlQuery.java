package org.softRoad.models.query;

import java.util.HashMap;
import java.util.Map;

public class HqlQuery
{
    private String hql;
    private final Map<String, Object> params = new HashMap<>();

    public HqlQuery()
    {
    }

    public HqlQuery(String hql)
    {
        this.hql = hql;
    }

    public HqlQuery(String format, String... args)
    {
        this.hql = String.format(format, args);
    }

    public Map<String, Object> getParams()
    {
        return params;
    }

    public String getHql()
    {
        return hql;
    }

    public void setHql(String hql)
    {
        this.hql = hql;
    }

    public boolean isEmpty()
    {
        return params.isEmpty();
    }

    public HqlQuery setParameter(String key, Object value)
    {
        params.put(key, value);
        return this;
    }

    @Override
    public String toString()
    {
        return "HqlQuery{" + "sql='" + hql + '\'' + ", params=" + params + '}';
    }
}
