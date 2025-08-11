package xyz.ibudai.validate.core.annotation;

import java.lang.annotation.*;

@Repeatable(Email.Group.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

    int group() default 0;

    String message() default "";

    boolean nullable() default true;

    String triggered() default "true";


    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Group {

        Email[] value() default {};

    }
}
