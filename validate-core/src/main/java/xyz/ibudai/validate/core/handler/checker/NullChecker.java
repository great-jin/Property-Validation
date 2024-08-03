package xyz.ibudai.validate.core.handler.checker;

import xyz.ibudai.validate.common.context.ValidateContext;
import xyz.ibudai.validate.common.exception.ValidateException;
import xyz.ibudai.validate.core.annotation.group.NullGroup;
import xyz.ibudai.validate.core.annotation.trigger.NotNull;
import xyz.ibudai.validate.core.annotation.trigger.Null;
import xyz.ibudai.validate.core.handler.Checker;
import xyz.ibudai.validate.core.util.ExceptionUtils;
import xyz.ibudai.validate.core.util.ExpressionUtils;
import xyz.ibudai.validate.core.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class NullChecker implements Checker {

    @Override
    public void check(ValidateContext context) throws Exception {
        Object obj = context.getData();
        Field field = context.getField();
        Null annotation = this.filter(field, context.getGroup());
        if (Objects.isNull(annotation)) {
            return;
        }
        String triggered = annotation.triggered();
        if (StringUtils.isBlank(triggered) || !ExpressionUtils.evaluateBoolean(obj, triggered)) {
            return;
        }

        Object o = field.get(obj);
        if (Objects.nonNull(o)) {
            String msg = annotation.message();
            if (StringUtils.isBlank(msg)) {
                msg = String.format("The field of {%s} must be null", field.getName());
            }
            ExceptionUtils.throwWithMsg(msg, obj, context.isSerialize());
        }
    }

    private Null filter(Field field, int group) {
        if (!field.isAnnotationPresent(Null.class) && !field.isAnnotationPresent(NullGroup.class)) {
            return null;
        }

        Null[] annotations = field.getAnnotationsByType(Null.class);
        annotations = Arrays.stream(annotations)
                .filter(it -> it.group() == group)
                .toArray(Null[]::new);
        if (annotations.length == 0) {
            return null;
        }
        if (annotations.length > 1) {
            throw new ValidateException("Field group is duplicate!");
        }
        return annotations[0];
    }
}
