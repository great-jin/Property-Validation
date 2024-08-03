package xyz.ibudai.validate.core.annotation.trigger;

import xyz.ibudai.validate.core.annotation.group.EmailGroup;

import java.lang.annotation.*;

@Repeatable(EmailGroup.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

    int group() default 0;

    String message() default "";

    boolean nullable() default true;

    String triggered() default "true";

}
