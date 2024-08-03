package xyz.ibudai.validate.core.annotation.trigger;

import xyz.ibudai.validate.core.annotation.group.NotNullGroup;

import java.lang.annotation.*;

@Repeatable(NotNullGroup.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

    int group() default 0;

    String message() default "";

    String triggered() default "true";

}
