package org.softRoad.models;

import org.softRoad.models.query.QueryUtils;

import javax.persistence.Table;

@Table(name = "consultant_profile_categories")
public class ConsultantProfileCategories {
    public final static String CONSULTANT_PROFILE_ID = "consultant_profile_id";
    public final static String CATEGORIES_ID = "categories_id";

    public static String field(String fieldName) {
        return QueryUtils.field(ConsultantProfileCategories.class, fieldName);
    }
}
