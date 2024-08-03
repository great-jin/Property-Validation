package xyz.ibudai.validate.core.util;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ExpressionUtils {

    private static final ExpressionParser parser = new SpelExpressionParser();

    /**
     * Evaluate boolean expression
     *
     * @param t   object
     * @param exp expression
     * @return boolean value
     */
    public static <T> boolean evaluateBoolean(T t, String exp) {
        Expression expression = parser.parseExpression(exp);
        StandardEvaluationContext context = new StandardEvaluationContext(t);
        Boolean result = expression.getValue(context, Boolean.class);
        return Boolean.TRUE.equals(result);
    }
}
