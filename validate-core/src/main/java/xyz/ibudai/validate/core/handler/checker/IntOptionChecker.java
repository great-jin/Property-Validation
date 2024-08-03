package xyz.ibudai.validate.core.handler.checker;

import xyz.ibudai.validate.common.context.ValidateContext;
import xyz.ibudai.validate.common.exception.ValidateException;
import xyz.ibudai.validate.core.annotation.group.IntOptionGroup;
import xyz.ibudai.validate.core.annotation.trigger.IntOption;
import xyz.ibudai.validate.core.handler.Checker;
import xyz.ibudai.validate.core.util.ExceptionUtils;
import xyz.ibudai.validate.core.util.ExpressionUtils;
import xyz.ibudai.validate.core.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class IntOptionChecker implements Checker {

    @Override
    public void check(ValidateContext context) throws Exception {
        Object obj = context.getData();
        Field field = context.getField();
        IntOption annotation = this.filter(field, context.getGroup());
        if (Objects.isNull(annotation)) {
            return;
        }
        String triggered = annotation.triggered();
        if (StringUtils.isBlank(triggered) || !ExpressionUtils.evaluateBoolean(obj, triggered)) {
            return;
        }
        int[] value = annotation.value();
        if (Objects.isNull(value) || value.length == 0) {
            throw new ValidateException("The options can't be empty");
        }
        Object o = field.get(obj);
        if (Objects.isNull(o) || StringUtils.isBlank(o.toString())) {
            throw new ValidateException(String.format("The field of {%s} can't be null", field.getName()));
        }

        int val = Integer.parseInt(o.toString());
        if (Arrays.stream(value).noneMatch(it -> it == val)) {
            String msg = annotation.message();
            if (StringUtils.isBlank(msg)) {
                msg = String.format("The field of {%s=%s} is illegal", field.getName(), val);
            }
            ExceptionUtils.throwWithMsg(msg, obj, context.isSerialize());
        }
    }

    private IntOption filter(Field field, int group) {
        if (!Number.class.isAssignableFrom(field.getType())) {
            return null;
        }
        if (!field.isAnnotationPresent(IntOption.class) && !field.isAnnotationPresent(IntOptionGroup.class)) {
            return null;
        }

        IntOption[] annotations = field.getAnnotationsByType(IntOption.class);
        annotations = Arrays.stream(annotations)
                .filter(it -> it.group() == group)
                .toArray(IntOption[]::new);
        if (annotations.length == 0) {
            return null;
        }
        if (annotations.length > 1) {
            throw new ValidateException("Field group is duplicate!");
        }
        return annotations[0];
    }
}
