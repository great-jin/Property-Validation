package xyz.ibudai.validate.test.combind;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Annotation1
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Annotation2 {

    @AliasFor(annotation = Annotation1.class)
    String name() default "";
}
