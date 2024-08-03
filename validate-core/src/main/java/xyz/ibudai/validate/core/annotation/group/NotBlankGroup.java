package xyz.ibudai.validate.core.annotation.group;

import xyz.ibudai.validate.core.annotation.trigger.NotBlank;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankGroup {

    NotBlank[] value() default {};

}
