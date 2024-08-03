package xyz.ibudai.validate.test.validate;

import org.junit.Test;
import xyz.ibudai.validate.core.FieldValidate;
import xyz.ibudai.validate.test.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserTest {

    @Test
    public void demo1() throws Exception {
        User user = new User();
        user.setId("123");
        FieldValidate.validate(user);
    }

    @Test
    public void demo2() throws Exception {
        User user = new User();
        user.setId("123");
        try {
            user.setName("a");
            FieldValidate.validate(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            user.setName("abcd");
            FieldValidate.validate(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            user.setName("abcdef");
            FieldValidate.validate(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void demo3() throws Exception {
        User user = new User();
        user.setId("123");
        user.setName("abcd");
        user.setBirthday(new Date());
        FieldValidate.validate(user);
    }

    @Test
    public void demo4() throws Exception {
        User user = new User();
        user.setId("123");
        user.setName("abcd");
        user.setCity("Beijing");
        FieldValidate.validate(user);
    }

    @Test
    public void demo5() throws Exception {
        User user = new User();
        user.setId("123");
        user.setName("abcd");
        user.setCity("Beijing");
        user.setLocked(2);
        FieldValidate.validate(user);
    }

    @Test
    public void demo6() throws Exception {
        User user = new User();
        user.setId("123");
        user.setName("abcd");
        user.setCity("Beijing");
        user.setLocked(1);

        try {
            user.setRoleList(new ArrayList<>());
            FieldValidate.validate(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                list.add(String.valueOf(i));
            }
            user.setRoleList(list);
            FieldValidate.validate(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
