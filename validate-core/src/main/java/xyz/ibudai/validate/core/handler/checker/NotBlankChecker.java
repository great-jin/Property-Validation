package xyz.ibudai.validate.core.handler.checker;

import xyz.ibudai.validate.common.context.ValidateContext;
import xyz.ibudai.validate.common.exception.ValidateException;
import xyz.ibudai.validate.core.annotation.group.NotBlankGroup;
import xyz.ibudai.validate.core.annotation.trigger.NotBlank;
import xyz.ibudai.validate.core.handler.Checker;
import xyz.ibudai.validate.core.util.ExceptionUtils;
import xyz.ibudai.validate.core.util.ExpressionUtils;
import xyz.ibudai.validate.core.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class NotBlankChecker implements Checker {

    @Override
    public void check(ValidateContext context) throws Exception {
        Object obj = context.getData();
        Field field = context.getField();
        NotBlank annotation = this.filter(field, context.getGroup());
        if (Objects.isNull(annotation)) {
            return;
        }
        String triggered = annotation.triggered();
        if (StringUtils.isBlank(triggered) || !ExpressionUtils.evaluateBoolean(obj, triggered)) {
            return;
        }

        Object o = field.get(obj);
        if (Objects.isNull(o) || StringUtils.isBlank(o.toString())) {
            String msg = annotation.message();
            if (StringUtils.isBlank(msg)) {
                msg = String.format("The field of {%s} can't be blank", field.getName());
            }
            ExceptionUtils.throwWithMsg(msg, obj, context.isSerialize());
        }
    }

    private NotBlank filter(Field field, int group) {
        if (!field.isAnnotationPresent(NotBlank.class) && !field.isAnnotationPresent(NotBlankGroup.class)) {
            return null;
        }

        NotBlank[] annotations = field.getAnnotationsByType(NotBlank.class);
        annotations = Arrays.stream(annotations)
                .filter(it -> it.group() == group)
                .toArray(NotBlank[]::new);
        if (annotations.length == 0) {
            return null;
        }
        if (annotations.length > 1) {
            throw new ValidateException("Field group is duplicate!");
        }
        return annotations[0];
    }
}
