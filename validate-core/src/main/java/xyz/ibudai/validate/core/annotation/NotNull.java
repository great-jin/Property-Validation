package xyz.ibudai.validate.core.annotation;

import java.lang.annotation.*;

@Repeatable(NotNull.Group.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

    int group() default 0;

    String message() default "";

    String triggered() default "true";


    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Group {

        NotNull[] value() default {};

    }
}
