package xyz.ibudai.validate.core.annotation.group;

import xyz.ibudai.validate.core.annotation.trigger.Length;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LengthGroup {

    Length[] value() default {};

}
