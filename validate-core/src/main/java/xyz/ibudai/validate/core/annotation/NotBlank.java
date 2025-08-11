package xyz.ibudai.validate.core.annotation;

import java.lang.annotation.*;

@Repeatable(NotBlank.Group.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {

    int group() default 0;

    String message() default "";

    String triggered() default "true";


    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Group {

        NotBlank[] value() default {};

    }
}
