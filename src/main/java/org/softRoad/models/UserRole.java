package org.softRoad.models;

import org.softRoad.models.query.QueryUtils;

import javax.persistence.Table;

@Table(name = "user_role")
public class UserRole {
    public final static String USER_ID = "user_id";
    public final static String ROLE_ID = "role_id";

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(UserRole.class, fieldName, fieldNames);
    }
}
