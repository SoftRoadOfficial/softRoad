package org.softRoad.models;

import org.softRoad.models.query.QueryUtils;

import javax.persistence.Table;

@Table(name = "procedure_tag")
public class ProcedureTag {

    public final static String PROCEDURE_ID = "procedure_id";
    public final static String TAG_ID = "tag_id";

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(ProcedureTag.class, fieldName, fieldNames);
    }
}
