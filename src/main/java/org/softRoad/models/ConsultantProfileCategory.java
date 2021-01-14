package org.softRoad.models;

import org.softRoad.models.query.QueryUtils;

import javax.persistence.Table;

@Table(name = "consultant_profile_category")
public class ConsultantProfileCategory {
    public final static String CONSULTANT_ID = "consultant_id";
    public final static String CATEGORIES_ID = "category_id";

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(ConsultantProfileCategory.class, fieldName, fieldNames);
    }
}
