package org.softRoad.utils;

import javax.persistence.Id;
import java.lang.reflect.Field;

public class ModelUtils {
    public static Integer getPrimaryKeyValue(Object bean, Class<?> aClass) {
        Field idField = getPrimaryKeyField(bean, aClass);
        if (idField == null)
            return null;
        try {
            return (Integer) idField.get(bean);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getPrimaryKeyField(Object bean, Class<?> aClass) {
        Field[] fields = aClass.getFields();
        for (Field f : fields) {
            Id idField = f.getAnnotation(Id.class);
            if (idField != null) {
                return f;
            }
        }
        return null;
    }
}
