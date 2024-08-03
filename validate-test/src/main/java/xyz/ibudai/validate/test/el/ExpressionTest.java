package xyz.ibudai.validate.test.el;

import org.junit.Test;
import xyz.ibudai.validate.core.util.ExpressionUtils;
import xyz.ibudai.validate.test.model.Student;

public class ExpressionTest {

    @Test
    public void demo1() {
        Student alex = new Student("Alex");
        Student beth = new Student("Beth");

        String expression = "true";
        System.out.println(ExpressionUtils.evaluateBoolean(alex, expression));
        System.out.println(ExpressionUtils.evaluateBoolean(beth, expression));
    }

    /**
     * $: 用于属性占位符解析。
     * #: 用于引用上下文中的变量和方法。
     * {}: 与 $ 一起使用，用于明确标识属性占位符的边界，特别是在复杂字符串中。
     */
    @Test
    public void demo2() {
        Student alex = new Student("Alex");
        Student beth = new Student("Beth");

        // "#root" 表示当前对象引用
        String expression1 = "#root.username =='Alex'";
        System.out.println(ExpressionUtils.evaluateBoolean(alex, expression1));
        System.out.println(ExpressionUtils.evaluateBoolean(beth, expression1));

        // 可省略直接通过字段属性名进行引用
        String expression2 = "username =='Alex'";
        System.out.println(ExpressionUtils.evaluateBoolean(alex, expression2));
        System.out.println(ExpressionUtils.evaluateBoolean(beth, expression2));
    }

    /**
     * 通过 "T()" 调用类
     */
    @Test
    public void demo3() {
        Student alex = new Student("  ");

        String expression = "T(xyz.ibudai.validate.core.util.StringUtils).isNotBlank(username)";
        System.out.println(ExpressionUtils.evaluateBoolean(alex, expression));
    }
}
