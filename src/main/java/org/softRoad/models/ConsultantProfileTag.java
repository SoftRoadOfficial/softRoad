package org.softRoad.models;

import org.softRoad.models.query.QueryUtils;

import javax.persistence.Table;

@Table(name = "consultant_profile_tag")
public class ConsultantProfileTag {

    public final static String CONSULTANT_ID = "consultant_id";
    public final static String TAG_ID = "tag_id";

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(ConsultantProfileTag.class, fieldName, fieldNames);
    }
}
