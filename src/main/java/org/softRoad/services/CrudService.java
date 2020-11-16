package org.softRoad.services;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.SoftRoadModel;
import org.softRoad.utils.ModelUtils;

import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.util.HashMap;

public class CrudService<T extends SoftRoadModel> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public Response create(T obj) {
        obj.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    public T get(Integer id) {
        return T.find("id=?1", id).firstResult();
    }

    @Transactional
    public Response update(T obj) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder();

        Table table = obj.getClass().getAnnotation(Table.class);
        Preconditions.checkState(table != null && !Strings.isNullOrEmpty(table.name()));
        queryBuilder.append("update ").append(table.name()).append(" set ");

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
                String columnName = joinColumn != null ? joinColumn.name() : Strings.isNullOrEmpty(column.name()) ? fieldName : column.name();

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
        for (String key : params.keySet())
            nativeQuery.setParameter(key, params.get(key));
        nativeQuery.executeUpdate();
        return Response.ok().build();
    }

    @Transactional
    public Response delete(Integer id) {
        T databaseObj = this.get(id);
        if (databaseObj == null)
            throw new InvalidDataException("Invalid id");
        databaseObj.delete();
        return Response.ok().build();
    }
}
