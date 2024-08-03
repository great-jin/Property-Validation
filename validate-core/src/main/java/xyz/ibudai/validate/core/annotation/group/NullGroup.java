package xyz.ibudai.validate.core.annotation.group;

import xyz.ibudai.validate.core.annotation.trigger.Null;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullGroup {

    Null[] value() default {};

}
