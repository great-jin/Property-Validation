package xyz.ibudai.validate.test.validate;

import org.junit.Test;
import xyz.ibudai.validate.core.FieldValidate;
import xyz.ibudai.validate.test.model.School;
import xyz.ibudai.validate.test.model.Student;
import xyz.ibudai.validate.test.model.Teacher;

import java.util.List;

public class ValidateTest {

    @Test
    public void demo1() throws Exception {
        Student student = new Student();
        student.setUsername("Alex");
        student.setCity("Guangzhou");

        FieldValidate.validate(student);
        System.out.println("Validate success!");
    }

    @Test
    public void demo2() throws Exception {
        Student student = new Student();
        student.setUsername("Alex");
        student.setPassword("123");
        student.setCity("Shanghai");

        FieldValidate.validate(student, 1);
        System.out.println("Validate success!");
    }

    @Test
    public void demo3() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId("1");
        teacher.setName("Alex");

        FieldValidate.validate(teacher);
        System.out.println("Validate success!");
    }

    @Test
    public void demo4() {
        try {
            School school1 = new School();
            school1.setTeachers(List.of(
                    new Teacher("123"),
                    new Teacher("456")
            ));

            FieldValidate.validate(school1);
            System.out.println("school1 Validate success!");
        } catch (Exception e) {
            System.err.println("1:" + e.getMessage());
        }

        try {
            School school2 = new School();
            school2.setTeachers(List.of(
                    new Teacher("123", "Alex"),
                    new Teacher("456", "Beth")
            ));

            FieldValidate.validate(school2);
            System.out.println("school2 Validate success!");
        } catch (Exception e) {
            System.err.println("2:" + e.getMessage());
        }
    }

    @Test
    public void demo5() {
        try {
            List<School> list = List.of(
                    new School(
                            "1",
                            List.of(
                                    new Teacher(),
                                    new Teacher()
                            )
                    ),
                    new School(
                            "2",
                            List.of(
                                    new Teacher("1", "123")
                            )
                    )
            );

            FieldValidate.validate(list, true);
            System.out.println("school1 Validate success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
