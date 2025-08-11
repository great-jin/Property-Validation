package xyz.ibudai.validate.core.annotation;

import java.lang.annotation.*;

@Repeatable(Size.Group.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {

    int group() default 0;

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    boolean nullable() default false;

    String message() default "";

    String triggered() default "true";


    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Group {

        Size[] value() default {};

    }
}
