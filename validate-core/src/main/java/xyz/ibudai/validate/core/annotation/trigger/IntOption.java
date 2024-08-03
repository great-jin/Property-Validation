package xyz.ibudai.validate.core.annotation.trigger;

import xyz.ibudai.validate.core.annotation.group.IntOptionGroup;

import java.lang.annotation.*;

@Repeatable(IntOptionGroup.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntOption {

    int group() default 0;

    int[] value() default {};

    String message() default "";

    String triggered() default "true";

}
