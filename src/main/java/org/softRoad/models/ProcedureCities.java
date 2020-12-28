package org.softRoad.models;

import org.softRoad.models.query.QueryUtils;

import javax.persistence.Table;

@Table(name = "procedure_cities")
public class ProcedureCities {
    public final static String PROCEDURE_ID = "procedure_id";
    public final static String CITIES_ID = "cities_id";

    public static String field(String fieldName) {
        return QueryUtils.field(ProcedureCities.class, fieldName);
    }
}
