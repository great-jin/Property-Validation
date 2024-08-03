package xyz.ibudai.validate.core.annotation.trigger;

import xyz.ibudai.validate.core.annotation.group.StringOptionGroup;

import java.lang.annotation.*;

@Repeatable(StringOptionGroup.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringOption {

    int group() default 0;

    String[] value() default {};

    boolean nullable() default false;

    String message() default "";

    String triggered() default "true";

}
