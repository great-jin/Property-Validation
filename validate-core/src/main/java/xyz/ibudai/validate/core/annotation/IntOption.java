package xyz.ibudai.validate.core.annotation;

import java.lang.annotation.*;

@Repeatable(IntOption.Group.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntOption {

    int group() default 0;

    int[] value() default {};

    String message() default "";

    String triggered() default "true";


    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Group {

        IntOption[] value() default {};

    }
}
