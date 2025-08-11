package xyz.ibudai.validate.core.annotation;

import java.lang.annotation.*;

@Repeatable(Length.Group.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {

    int group() default 0;

    int min() default 1;

    int max() default Integer.MAX_VALUE;

    boolean nullable() default false;

    String message() default "";

    String triggered() default "true";


    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Group {

        Length[] value() default {};

    }
}
