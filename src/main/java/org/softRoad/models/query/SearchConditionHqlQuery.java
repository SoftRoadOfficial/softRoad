package org.softRoad.models.query;

import java.util.HashMap;
import java.util.Map;

public class SearchConditionHqlQuery {
    private String sql;
    private final Map<String, Object> params = new HashMap<>();

    public Map<String, Object> getParams() {
        return params;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "SearchConditionSqlResult{" +
                "sql='" + sql + '\'' +
                ", params=" + params +
                '}';
    }
}
