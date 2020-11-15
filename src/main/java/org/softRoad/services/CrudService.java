package org.softRoad.services;

import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.SoftRoadModel;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.util.HashMap;

public class CrudService<T extends SoftRoadModel> {

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
        obj.presentFields.forEach(fieldName -> {
            try {
                Field field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                String columnName = joinColumn != null ? joinColumn.name() : column == null || column.name().isEmpty() ? fieldName : column.name();
                params.put(columnName, field.get(obj));
                if (!columnName.equals("id")) {
                    if (queryBuilder.length() > 0)
                        queryBuilder.append(", ");
                    queryBuilder.append(columnName).append("=:").append(columnName);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new InvalidDataException(e.getMessage());
            }
        });
        T.update(queryBuilder.toString() + " where id=:id", params);
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
