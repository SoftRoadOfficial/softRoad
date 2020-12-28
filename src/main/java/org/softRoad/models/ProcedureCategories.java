package org.softRoad.models;

import org.softRoad.models.query.QueryUtils;

import javax.persistence.Table;

@Table(name = "procedure_categories")
public class ProcedureCategories {
    public final static String PROCEDURE_ID = "procedure_id";
    public final static String CATEGORIES_ID = "categories_id";

    public static String field(String fieldName) {
        return QueryUtils.field(ProcedureCategories.class, fieldName);
    }
}
