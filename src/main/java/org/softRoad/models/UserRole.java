package org.softRoad.models;

import org.softRoad.models.query.QueryUtils;

import javax.persistence.Table;
import java.beans.Transient;

@Table(name = "user_role")
public class UserRole {
    public final static String USER_ID = "user_id";
    public final static String ROLE_ID = "role_id";

    public static String field(String fieldName) {
        return QueryUtils.field(UserRole.class, fieldName);
    }
}
