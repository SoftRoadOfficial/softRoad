package org.softRoad.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.softRoad.exception.ForbiddenException;
import org.softRoad.exception.InternalException;
import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.AuditLog;
import org.softRoad.models.AuditLog.Action;
import org.softRoad.models.SoftRoadModel;
import org.softRoad.models.query.HqlQuery;
import org.softRoad.models.query.QueryUtils;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.AccessControlManager;
import org.softRoad.security.Permission;
import org.softRoad.utils.ModelUtils;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrudService<T extends SoftRoadModel> {
    private final Class<?> objClass;

    @Inject
    EntityManager entityManager;

    @Inject
    protected AccessControlManager acm;

    @Inject
    ObjectMapper mapper;

    public CrudService(Class<?> objClass) {
        this.objClass = objClass;
    }

    @Transactional
    private void log(Action type, T obj) {
        AuditLog log = new AuditLog();
        log.user = acm.getCurrentUser();
        log.action = type;
        log.objectId = ModelUtils.getPrimaryKeyValue(obj, objClass);
        log.objectType = objClass.getName();
        try {
            log.payload = mapper.writeValueAsString(convert(obj));
        } catch (JsonProcessingException ex) {
            throw new InternalException(ex);
        }
        AuditLog.persist(log);
    }

    protected void checkPermission(PermissionType type) {
        checkState(hasPermission(type));
    }

    protected boolean hasPermission(PermissionType type) {
        return acm.hasPermission(Permission.valueOf(type.name() + "_" + objClass.getSimpleName().toUpperCase()));
    }

    protected void checkState(boolean b) {
        if (!b)
            throw new ForbiddenException("Permission denied");
    }

    private Map<String, String> convert(T obj) {
        Map<String, String> map = new HashMap<>();
        obj.presentFields.forEach(fieldName -> {
            Object columnValue = ModelUtils.getColumnValue(obj, obj.getClass(), fieldName);
            map.put(fieldName, (columnValue == null ? null : columnValue.toString()));
        });
        return map;
    }

    @Transactional
    public Response create(T obj) {
        checkPermission(PermissionType.CREATE);
        obj.persist();
        log(Action.CREATE, obj);
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    public T get(Integer id) {
        checkPermission(PermissionType.READ);
        return (T) entityManager.createNativeQuery(String.format("select * from %s where id=:id",
                ModelUtils.getTableName(objClass), objClass), objClass)
                .setParameter("id", id).getResultStream().findFirst().get();
    }

    @Transactional
    public Response update(T obj) {
        checkPermission(PermissionType.UPDATE);
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder();

        String table = ModelUtils.getTableName(obj.getClass());
        queryBuilder.append("update ").append(table).append(" set ");

        Field primaryKeyField = ModelUtils.getPrimaryKeyField(obj, obj.getClass());
        Preconditions.checkState(primaryKeyField != null, "Invalid update input, did you use DiffValidator?");
        String pkName = primaryKeyField.getName();

        int ind = 0;
        for (String fieldName : obj.presentFields) {
            try {
                Field field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                String columnName = joinColumn != null ? joinColumn.name() : column == null || Strings.isNullOrEmpty(
                        column.name()) ? fieldName : column.name();

                Object value = field.get(obj);
                if (joinColumn != null)
                    value = ModelUtils.getPrimaryKeyValue(value, value.getClass());

                params.put(columnName, value);

                if (!columnName.equals(pkName)) {
                    if (ind > 0)
                        queryBuilder.append(", ");
                    ind++;
                    queryBuilder.append(columnName).append("=:").append(columnName);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new InvalidDataException(e.getMessage());
            }
        }
        queryBuilder.append(" where ").append(pkName).append("=:").append(pkName);
        Query nativeQuery = entityManager.createNativeQuery(queryBuilder.toString());
        params.keySet().forEach(key -> nativeQuery.setParameter(key, params.get(key)));
        nativeQuery.executeUpdate();
        log(Action.UPDATE, obj);
        return Response.ok().build();
    }

    @Transactional
    public Response delete(Integer id) {
        checkPermission(PermissionType.DELETE);
        T databaseObj = this.get(id);
        if (databaseObj == null)
            throw new InvalidDataException("Invalid id");
        databaseObj.delete();
        log(Action.DELETE, databaseObj);
        return Response.ok().build();
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll(SearchCriteria searchCriteria) {
        checkPermission(PermissionType.READ);
        return QueryUtils.nativeQuery(entityManager, objClass)
                .baseQuery(new HqlQuery("select * from " + ModelUtils.getTableName(objClass)))
                .searchCriteria(searchCriteria)
                .build().getResultList();
    }

    protected enum PermissionType {
        CREATE,
        DELETE,
        UPDATE,
        READ
    }
}
