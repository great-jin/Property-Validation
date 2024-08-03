package xyz.ibudai.validate.core.annotation.trigger;

import xyz.ibudai.validate.core.annotation.group.BlankGroup;

import java.lang.annotation.*;

@Repeatable(BlankGroup.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Blank {

    int group() default 0;

    String message() default "";

    String triggered() default "true";

}
