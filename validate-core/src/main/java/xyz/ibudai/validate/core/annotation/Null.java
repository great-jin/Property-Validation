package xyz.ibudai.validate.core.annotation;

import java.lang.annotation.*;

@Repeatable(Null.Group.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Null {

    int group() default 0;

    String message() default "";

    String triggered() default "true";


    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Group {

        Null[] value() default {};

    }
}
