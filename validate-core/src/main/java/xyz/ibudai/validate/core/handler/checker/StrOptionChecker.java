package xyz.ibudai.validate.core.handler.checker;

import xyz.ibudai.validate.common.context.ValidateContext;
import xyz.ibudai.validate.common.exception.ValidateException;
import xyz.ibudai.validate.core.annotation.group.StringOptionGroup;
import xyz.ibudai.validate.core.annotation.trigger.StringOption;
import xyz.ibudai.validate.core.handler.Checker;
import xyz.ibudai.validate.core.util.ExceptionUtils;
import xyz.ibudai.validate.core.util.ExpressionUtils;
import xyz.ibudai.validate.core.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class StrOptionChecker implements Checker {

    @Override
    public void check(ValidateContext context) throws Exception {
        Object obj = context.getData();
        Field field = context.getField();
        StringOption annotation = this.filter(field, context.getGroup());
        if (Objects.isNull(annotation)) {
            return;
        }
        String triggered = annotation.triggered();
        if (StringUtils.isBlank(triggered) || !ExpressionUtils.evaluateBoolean(obj, triggered)) {
            return;
        }
        Object o = field.get(obj);
        if (Objects.isNull(o) || StringUtils.isBlank(o.toString())) {
            if (annotation.nullable()) {
                return;
            }
            throw new ValidateException(String.format("The field of {%s} can't be blank", field.getName()));
        }

        String[] options = annotation.value();
        if (Objects.isNull(options) || options.length == 0) {
            throw new ValidateException("The options can't be empty");
        }
        String val = String.valueOf(o);
        if (Arrays.stream(options).noneMatch(it -> Objects.equals(it, val))) {
            String msg = annotation.message();
            if (StringUtils.isBlank(msg)) {
                msg = String.format("The field of {%s=%s} is illegal", field.getName(), val);
            }
            ExceptionUtils.throwWithMsg(msg, obj, context.isSerialize());
        }
    }

    private StringOption filter(Field field, int group) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return null;
        }
        if (!field.isAnnotationPresent(StringOption.class) && !field.isAnnotationPresent(StringOptionGroup.class)) {
            return null;
        }

        StringOption[] annotations = field.getAnnotationsByType(StringOption.class);
        annotations = Arrays.stream(annotations)
                .filter(it -> it.group() == group)
                .toArray(StringOption[]::new);
        if (annotations.length == 0) {
            return null;
        }
        if (annotations.length > 1) {
            throw new ValidateException("Field group is duplicate!");
        }
        return annotations[0];
    }
}
