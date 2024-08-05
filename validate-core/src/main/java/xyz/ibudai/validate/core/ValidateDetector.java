package xyz.ibudai.validate.core;


import xyz.ibudai.validate.core.annotation.Validate;

import java.lang.reflect.Parameter;

public class ValidateDetector {

    public static <T> void detect(T t, Parameter parameter) throws Exception {
        if (!parameter.isAnnotationPresent(Validate.class)) {
            return;
        }

        Validate annotation = parameter.getAnnotation(Validate.class);
        int group = annotation.group();
        FieldValidate.validate(t, group);
    }

    public static <T> void detect(T t, Parameter parameter, boolean serialize) throws Exception {
        if (!parameter.isAnnotationPresent(Validate.class)) {
            return;
        }

        Validate annotation = parameter.getAnnotation(Validate.class);
        int group = annotation.group();
        FieldValidate.validate(t, group, serialize);
    }
}
