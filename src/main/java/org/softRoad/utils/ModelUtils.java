package org.softRoad.utils;

import com.google.common.base.Strings;
import org.softRoad.exception.InvalidDataException;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.JoinColumn;

public class ModelUtils
{
    public static Integer getPrimaryKeyValue(Object bean, Class<?> aClass)
    {
        Field idField = getPrimaryKeyField(bean, aClass);
        if (idField == null)
            return null;
        try {
            return (Integer) idField.get(bean);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getPrimaryKeyField(Object bean, Class<?> aClass)
    {
        Field[] fields = aClass.getFields();
        for (Field f : fields) {
            Id idField = f.getAnnotation(Id.class);
            if (idField != null) {
                return f;
            }
        }
        return null;
    }

    public static String getTableName(Class<?> aClass)
    {
        if (aClass == null)
            return "";
        Table table = aClass.getAnnotation(Table.class);
        if (table != null && !Strings.isNullOrEmpty(table.name())) {
            return table.name();
        }
        return aClass.getName().toLowerCase();
    }

    public static String getColumnName(String field, Class<?> aClass)
    {
        if (aClass == null)
            return field;
        Field declaredField = null;
        try {
            declaredField = aClass.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            throw new InvalidDataException("Invalid field ");
        }
        Column column = declaredField.getAnnotation(Column.class);
        if (column == null || Strings.isNullOrEmpty(column.name()))
            return field;
        return column.name();
    }

    public static Object getColumnValue(Object model, Class<?> modelType, String fieldName)
    {
        try {
            Field field = modelType.getDeclaredField(fieldName);
            field.setAccessible(true);
            JoinColumn annotation = field.getAnnotation(JoinColumn.class);
            if (annotation != null) {
                getPrimaryKeyValue(model, model.getClass());
            } else {
                return field.get(model);
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            throw new InvalidDataException("Invalid field");
        }
        return null;
    }
}
