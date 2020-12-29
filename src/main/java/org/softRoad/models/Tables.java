package org.softRoad.models;

import javafx.scene.control.Tab;
import org.hibernate.event.spi.FlushEntityEvent;
import org.softRoad.models.query.QueryUtils;
import org.softRoad.utils.ModelUtils;

import javax.swing.text.html.HTML;

public class Tables {
    public final static String AUDIT_LOG = ModelUtils.getTableName(AuditLog.class);
    public final static String CATEGORY = ModelUtils.getTableName(Category.class);
    public final static String CITY = ModelUtils.getTableName(City.class);
    public final static String COMMENT = ModelUtils.getTableName(Comment.class);
    public final static String CONSULTANT_PROFILE = ModelUtils.getTableName(ConsultantProfile.class);
    public final static String CONSULTANT_PROFILE_CATEGORIES = ModelUtils.getTableName(ConsultantProfileCategories.class);
    public final static String CONSULTATION = ModelUtils.getTableName(Consultation.class);
    public final static String FEE = ModelUtils.getTableName(Fee.class);
    public final static String PROCEDURE = ModelUtils.getTableName(Procedure.class);
    public final static String PROCEDURE_CATEGORIES = ModelUtils.getTableName(ProcedureCategories.class);
    public final static String PROCEDURE_CITIES = ModelUtils.getTableName(ProcedureCities.class);
    public final static String ROLE = ModelUtils.getTableName(Role.class);
    public final static String STEP = ModelUtils.getTableName(Step.class);
    public final static String TAG = ModelUtils.getTableName(HTML.Tag.class);
    public final static String UPDATE_REQUEST = ModelUtils.getTableName(UpdateRequest.class);
    public final static String USER = ModelUtils.getTableName(User.class);
    public final static String USER_ROLE = ModelUtils.getTableName(UserRole.class);
    public final static String USER_VERIFICATION = ModelUtils.getTableName(UserVerification.class);
}
