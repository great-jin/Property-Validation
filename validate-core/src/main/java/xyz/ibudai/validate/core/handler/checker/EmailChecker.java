package xyz.ibudai.validate.core.handler.checker;

import xyz.ibudai.validate.common.context.ValidateContext;
import xyz.ibudai.validate.common.exception.ValidateException;
import xyz.ibudai.validate.core.annotation.group.EmailGroup;
import xyz.ibudai.validate.core.annotation.trigger.Email;
import xyz.ibudai.validate.core.handler.Checker;
import xyz.ibudai.validate.core.util.ExceptionUtils;
import xyz.ibudai.validate.core.util.ExpressionUtils;
import xyz.ibudai.validate.core.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailChecker implements Checker {

    private static final String REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,6}$";
    private static final Pattern PATTERN = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

    /**
     * The field value must be email
     */
    @Override
    public void check(ValidateContext context) throws Exception {
        Object obj = context.getData();
        Field field = context.getField();
        Email annotation = this.filter(field, context.getGroup());
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

            String msg = annotation.message();
            if (StringUtils.isBlank(msg)) {
                msg = String.format("The field of {%s} can't be blank", field.getName());
            }
            ExceptionUtils.throwWithMsg(msg, obj, context.isSerialize());
        }

        String val = o.toString();
        Matcher matcher = PATTERN.matcher(val);
        if (!matcher.matches()) {
            String msg = annotation.message();
            if (StringUtils.isBlank(msg)) {
                msg = String.format("The email of {%s} is illegal", field.getName());
            }
            ExceptionUtils.throwWithMsg(msg, obj, context.isSerialize());
        }
    }

    private Email filter(Field field, int group) {
        if (!String.class.isAssignableFrom(field.getType())) {
            return null;
        }
        if (!field.isAnnotationPresent(Email.class) && !field.isAnnotationPresent(EmailGroup.class)) {
            return null;
        }

        Email[] annotations = field.getAnnotationsByType(Email.class);
        annotations = Arrays.stream(annotations)
                .filter(it -> it.group() == group)
                .toArray(Email[]::new);
        if (annotations.length == 0) {
            return null;
        }
        if (annotations.length > 1) {
            throw new ValidateException("Field group is duplicate!");
        }
        return annotations[0];
    }
}
