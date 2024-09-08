package xyz.ibudai.validate.test.combind;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.Objects;

public class MyTest {

    @Test
    public void demo1() {
        boolean assignableFrom = Test1.class.isAnnotationPresent(Annotation1.class);
        System.out.println(assignableFrom);

        Annotation1 annotation = Test1.class.getAnnotation(Annotation1.class);
        System.out.println(annotation);
    }

    @Test
    public void demo2() {
        read(Test1.class);


        read(Object.class);
    }

    @Annotation2(name = "test1")
    public static class Test1 {

    }

    private void read(Class<?> cls) {
        Annotation[] annotations = cls.getAnnotations();
        for (Annotation annotation : annotations) {

        }
    }
}
