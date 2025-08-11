package xyz.ibudai.validate.core.annotation;

import java.lang.annotation.*;

@Repeatable(Range.Group.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {

    int group() default 0;

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "";

    String triggered() default "true";


    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Group {

        Range[] value() default {};

    }
}
