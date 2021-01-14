package org.softRoad.models;

import org.softRoad.models.query.QueryUtils;

import javax.persistence.Table;

@Table(name = "procedure_category")
public class ProcedureCategory {
    public final static String PROCEDURE_ID = "procedure_id";
    public final static String CATEGORIES_ID = "categories_id";

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(ProcedureCategory.class, fieldName, fieldNames);
    }
}
