package xyz.ibudai.validate.core.annotation.trigger;

import xyz.ibudai.validate.core.annotation.group.NotBlankGroup;

import java.lang.annotation.*;

@Repeatable(NotBlankGroup.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {

    int group() default 0;

    String message() default "";

    String triggered() default "true";

}
