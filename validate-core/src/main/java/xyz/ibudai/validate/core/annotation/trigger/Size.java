package xyz.ibudai.validate.core.annotation.trigger;

import xyz.ibudai.validate.core.annotation.group.SizeGroup;

import java.lang.annotation.*;

@Repeatable(SizeGroup.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {

    int group() default 0;

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    boolean nullable() default false;

    String message() default "";

    String triggered() default "true";

}
