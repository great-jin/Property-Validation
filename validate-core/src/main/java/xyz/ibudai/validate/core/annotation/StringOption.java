package xyz.ibudai.validate.core.annotation;

import java.lang.annotation.*;

@Repeatable(StringOption.Group.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringOption {

    int group() default 0;

    String[] value() default {};

    boolean nullable() default false;

    String message() default "";

    String triggered() default "true";


    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Group {

        StringOption[] value() default {};

    }
}
