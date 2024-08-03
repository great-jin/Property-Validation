package xyz.ibudai.validate.core.handler.checker;

import xyz.ibudai.validate.common.context.ValidateContext;
import xyz.ibudai.validate.common.exception.ValidateException;
import xyz.ibudai.validate.core.annotation.group.SizeGroup;
import xyz.ibudai.validate.core.annotation.trigger.Length;
import xyz.ibudai.validate.core.annotation.trigger.Size;
import xyz.ibudai.validate.core.handler.Checker;
import xyz.ibudai.validate.core.util.ExceptionUtils;
import xyz.ibudai.validate.core.util.ExpressionUtils;
import xyz.ibudai.validate.core.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class SizeChecker implements Checker {

    @Override
    public void check(ValidateContext context) throws Exception {
        Object obj = context.getData();
        Field field = context.getField();
        Size annotation = this.filter(field, context.getGroup());
        if (Objects.isNull(annotation)) {
            return;
        }
        String triggered = annotation.triggered();
        if (StringUtils.isBlank(triggered) || !ExpressionUtils.evaluateBoolean(obj, triggered)) {
            // If expression is blank or false then quit
            return;
        }

        Collection<?> col = (Collection<?>) field.get(obj);
        if (Objects.isNull(col) || col.isEmpty()) {
            if (annotation.nullable()) {
                return;
            }
            throw new ValidateException(String.format("The field of {%s} is empty", field.getName()));
        }
        int min = annotation.min();
        int max = annotation.max();
        if (col.size() < min || col.size() > max) {
            String msg = annotation.message();
            if (StringUtils.isBlank(msg)) {
                msg = String.format("The size of field {%s} is exceed", field.getName());
            }
            ExceptionUtils.throwWithMsg(msg, obj, context.isSerialize());
        }
    }

    private Size filter(Field field, int group) {
        if (!Collection.class.isAssignableFrom(field.getType())) {
            return null;
        }
        if (!field.isAnnotationPresent(Size.class) && !field.isAnnotationPresent(SizeGroup.class)) {
            return null;
        }

        Size[] annotations = field.getAnnotationsByType(Size.class);
        annotations = Arrays.stream(annotations)
                .filter(it -> it.group() == group)
                .toArray(Size[]::new);
        if (annotations.length == 0) {
            return null;
        }
        if (annotations.length > 1) {
            throw new ValidateException("Field group is duplicate!");
        }
        return annotations[0];
    }
}
