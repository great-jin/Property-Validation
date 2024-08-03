package xyz.ibudai.validate.core.annotation.trigger;

import xyz.ibudai.validate.core.annotation.group.RangeGroup;

import java.lang.annotation.*;

@Repeatable(RangeGroup.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {

    int group() default 0;

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "";

    String triggered() default "true";

}
