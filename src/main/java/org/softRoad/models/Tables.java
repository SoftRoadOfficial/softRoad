package org.softRoad.models;

import static org.softRoad.utils.ModelUtils.getTableName;

public class Tables
{
    public final static String AUDIT_LOGS = getTableName(AuditLog.class);
    public final static String CATEGORIES = getTableName(Category.class);
    public final static String CITIES = getTableName(City.class);
    public final static String COMMENTS = getTableName(Comment.class);
    public final static String CONSULTANT_PROFILES = getTableName(ConsultantProfile.class);
    public final static String CONSULTANT_PROFILE_CATEGORY = getTableName(ConsultantProfileCategory.class);
    public final static String CONSULTANT_PROFILE_TAG = getTableName(ConsultantProfileTag.class);
    public final static String CONSULTATIONS = getTableName(Consultation.class);
    public final static String FEES = getTableName(Fee.class);
    public final static String PROCEDURES = getTableName(Procedure.class);
    public final static String PROCEDURE_CATEGORY = getTableName(ProcedureCategory.class);
    public final static String PROCEDURE_CITY = getTableName(ProcedureCity.class);
    public final static String PROCEDURE_TAG = getTableName(ProcedureTag.class);
    public final static String ROLES = getTableName(Role.class);
    public final static String STEPS = getTableName(Step.class);
    public final static String TAGS = getTableName(Tag.class);
    public final static String UPDATE_REQUESTS = getTableName(UpdateRequest.class);
    public final static String USERS = getTableName(User.class);
    public final static String USER_ROLES = getTableName(UserRole.class);
    public final static String USER_VERIFICATIONS = getTableName(UserVerification.class);
}
