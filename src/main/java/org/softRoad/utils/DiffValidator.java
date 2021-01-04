package org.softRoad.utils;

import com.google.common.base.Preconditions;
import org.softRoad.models.SoftRoadModel;

import javax.persistence.JoinColumn;
import javax.validation.*;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class DiffValidator implements ConstraintValidator<Diff, Object>
{

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private Class<?>[] groups;

    @Override
    public void initialize(Diff annotation)
    {
        ConstraintValidator.super.initialize(annotation);
        groups = annotation.groups();
    }

    @Override
    public boolean isValid(Object bean, ConstraintValidatorContext context)
    {
        context.buildConstraintViolationWithTemplate("").addBeanNode();

        if (bean == null)
            return true;

        if (bean instanceof Collection) {
            for (Object o : (Collection) bean)
                if (!isValid(o, context))
                    return false;
        } else {
            Preconditions.checkState(bean instanceof SoftRoadModel);
            Integer id = ModelUtils.getPrimaryKeyValue(bean, bean.getClass());
            if (id == null)
                return false;
            for (String fieldName : ((SoftRoadModel) bean).presentFields) {
                Field field = null;
                try {
                    field = bean.getClass().getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
                JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                if (joinColumn != null) {
                    SoftRoadModel item = null;
                    try {
                        item = ((SoftRoadModel) field.get(bean));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    List<String> presentFields = item.presentFields;
                    Field pkField = ModelUtils.getPrimaryKeyField(item, item.getClass());
                    Preconditions.checkState(pkField != null);
                    if (presentFields.size() != 1 || !presentFields.get(0).equals(pkField.getName()))
                        return false;
                } else {
                    Validator validator = factory.getValidator();
                    Set<ConstraintViolation<Object>> s = validator.validateProperty(bean, fieldName, groups);
                    if (!s.isEmpty())
                        return false;
                }
            }
        }
        return true;
    }

}
