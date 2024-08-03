package xyz.ibudai.validate.core.annotation.trigger;

import xyz.ibudai.validate.core.annotation.group.NullGroup;

import java.lang.annotation.*;

@Repeatable(NullGroup.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Null {

    int group() default 0;

    String message() default "";

    String triggered() default "true";

}
