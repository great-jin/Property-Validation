package xyz.ibudai.validate.core.handler.checker;

import xyz.ibudai.validate.common.context.ValidateContext;
import xyz.ibudai.validate.common.exception.ValidateException;
import xyz.ibudai.validate.core.annotation.group.LengthGroup;
import xyz.ibudai.validate.core.annotation.trigger.Blank;
import xyz.ibudai.validate.core.annotation.trigger.Length;
import xyz.ibudai.validate.core.handler.Checker;
import xyz.ibudai.validate.core.util.ExceptionUtils;
import xyz.ibudai.validate.core.util.ExpressionUtils;
import xyz.ibudai.validate.core.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class LengthChecker implements Checker {

    @Override
    public void check(ValidateContext context) throws Exception {
        Object obj = context.getData();
        Field field = context.getField();
        Length annotation = this.filter(field, context.getGroup());
        if (Objects.isNull(annotation)) {
            return;
        }
        String triggered = annotation.triggered();
        if (StringUtils.isBlank(triggered) || !ExpressionUtils.evaluateBoolean(obj, triggered)) {
            // If expression is blank or false then quit
            return;
        }

        Object o = field.get(obj);
        if (Objects.isNull(o) || StringUtils.isBlank(o.toString())) {
            if (annotation.nullable()) {
                return;
            }
            throw new ValidateException(String.format("The field of {%s} can't be blank", field.getName()));
        }
        int min = annotation.min();
        int max = annotation.max();
        String val = String.valueOf(o);
        if (val.length() < min || val.length() > max) {
            String msg = annotation.message();
            if (StringUtils.isBlank(msg)) {
                msg = String.format("The length of field {%s=%s} is exceed", field.getName(), val);
            }
            ExceptionUtils.throwWithMsg(msg, obj, context.isSerialize());
        }
    }

    private Length filter(Field field, int group) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return null;
        }
        if (!field.isAnnotationPresent(Length.class) && !field.isAnnotationPresent(LengthGroup.class)) {
            return null;
        }

        Length[] annotations = field.getAnnotationsByType(Length.class);
        annotations = Arrays.stream(annotations)
                .filter(it -> it.group() == group)
                .toArray(Length[]::new);
        if (annotations.length == 0) {
            return null;
        }
        if (annotations.length > 1) {
            throw new ValidateException("Field group is duplicate!");
        }
        return annotations[0];
    }
}
