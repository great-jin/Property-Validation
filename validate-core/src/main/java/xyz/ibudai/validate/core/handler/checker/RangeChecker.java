package xyz.ibudai.validate.core.handler.checker;

import xyz.ibudai.validate.common.context.ValidateContext;
import xyz.ibudai.validate.common.exception.ValidateException;
import xyz.ibudai.validate.core.annotation.group.RangeGroup;
import xyz.ibudai.validate.core.annotation.trigger.Length;
import xyz.ibudai.validate.core.annotation.trigger.Range;
import xyz.ibudai.validate.core.handler.Checker;
import xyz.ibudai.validate.core.util.ExceptionUtils;
import xyz.ibudai.validate.core.util.ExpressionUtils;
import xyz.ibudai.validate.core.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class RangeChecker implements Checker {

    @Override
    public void check(ValidateContext context) throws Exception {
        Object obj = context.getData();
        Field field = context.getField();
        Range annotation = this.filter(field, context.getGroup());
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
            throw new ValidateException(String.format("The field of {%s} can't be null", field.getName()));
        }
        int min = annotation.min();
        int max = annotation.min();
        int val = Integer.parseInt(o.toString());
        if (val < min || val > max) {
            String msg = annotation.message();
            if (StringUtils.isBlank(msg)) {
                msg = String.format("The value of field {%s=%s} is exceed", field.getName(), val);
            }
            ExceptionUtils.throwWithMsg(msg, obj, context.isSerialize());
        }
    }

    private Range filter(Field field, int group) {
        if (!Number.class.isAssignableFrom(field.getType())) {
            return null;
        }
        if (!field.isAnnotationPresent(Range.class) && !field.isAnnotationPresent(RangeGroup.class)) {
            return null;
        }

        Range[] annotations = field.getAnnotationsByType(Range.class);
        annotations = Arrays.stream(annotations)
                .filter(it -> it.group() == group)
                .toArray(Range[]::new);
        if (annotations.length == 0) {
            return null;
        }
        if (annotations.length > 1) {
            throw new ValidateException("Field group is duplicate!");
        }
        return annotations[0];
    }
}
