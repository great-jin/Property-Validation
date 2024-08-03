package xyz.ibudai.validate.core.handler.checker;

import xyz.ibudai.validate.common.context.ValidateContext;
import xyz.ibudai.validate.common.exception.ValidateException;
import xyz.ibudai.validate.core.annotation.group.BlankGroup;
import xyz.ibudai.validate.core.annotation.trigger.Blank;
import xyz.ibudai.validate.core.handler.Checker;
import xyz.ibudai.validate.core.util.ExceptionUtils;
import xyz.ibudai.validate.core.util.ExpressionUtils;
import xyz.ibudai.validate.core.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class BlankChecker implements Checker {

    /**
     * The field value must be blank string
     */
    @Override
    public void check(ValidateContext context) throws Exception {
        Object obj = context.getData();
        Field field = context.getField();
        Blank annotation = this.filter(field, context.getGroup());
        if (Objects.isNull(annotation)) {
            return;
        }
        String triggered = annotation.triggered();
        if (StringUtils.isBlank(triggered) || !ExpressionUtils.evaluateBoolean(obj, triggered)) {
            // If expression is blank or false then quit
            return;
        }

        Object o = field.get(obj);
        if (Objects.isNull(o)) {
            throw new ValidateException(String.format("The field of {%s} can't be null", field.getName()));
        }
        if (StringUtils.isNotBlank(o.toString())) {
            String msg = annotation.message();
            if (StringUtils.isBlank(msg)) {
                msg = String.format("The field of {%s} must be blank", field.getName());
            }
            ExceptionUtils.throwWithMsg(msg, obj, context.isSerialize());
        }
    }

    private Blank filter(Field field, int group) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return null;
        }
        if (!field.isAnnotationPresent(Blank.class) && !field.isAnnotationPresent(BlankGroup.class)) {
            return null;
        }

        Blank[] annotations = field.getAnnotationsByType(Blank.class);
        annotations = Arrays.stream(annotations)
                .filter(it -> it.group() == group)
                .toArray(Blank[]::new);
        if (annotations.length == 0) {
            return null;
        }
        if (annotations.length > 1) {
            throw new ValidateException("Field group is duplicate!");
        }
        return annotations[0];
    }
}
