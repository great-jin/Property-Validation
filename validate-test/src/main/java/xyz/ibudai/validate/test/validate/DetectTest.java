package xyz.ibudai.validate.test.validate;

import org.junit.Before;
import org.junit.Test;
import xyz.ibudai.validate.core.ValidateDetector;
import xyz.ibudai.validate.core.annotation.Validate;
import xyz.ibudai.validate.test.model.Teacher;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DetectTest {

    private Parameter parameters;

    @Before
    public void init() throws NoSuchMethodException {
        Class<DetectTest> cls = DetectTest.class;
        Method method = cls.getDeclaredMethod("testMethod", Teacher.class);
        method.setAccessible(true);

        parameters = method.getParameters()[0];
    }

    @Test
    public void demo1() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId("");
        teacher.setName("Alex");

        ValidateDetector.detect(teacher, parameters);
        System.out.println(teacher);
    }

    @Test
    public void demo2() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId("1");
        teacher.setName("Alex");

        ValidateDetector.detect(teacher, parameters);
        System.out.println(teacher);
    }

    private void testMethod(@Validate Teacher teacher) {
        System.out.println(teacher.toString());
    }
}
