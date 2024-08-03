package xyz.ibudai.validate.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.ibudai.validate.common.context.ValidateContext;
import xyz.ibudai.validate.core.handler.Checker;
import xyz.ibudai.validate.core.handler.checker.SizeChecker;

import java.lang.reflect.Field;
import java.util.*;

public class FieldValidate {

    private static final Logger LOG = LoggerFactory.getLogger(FieldValidate.class);
    private static final int DEFAULT_GROUP = 0;
    private static final List<Checker> checkerList;
    private static final Checker sizeChecker = new SizeChecker();

    static {
        // Load class by SPI
        checkerList = new ArrayList<>();
        ServiceLoader<Checker> loaders = ServiceLoader.load(Checker.class);
        for (Checker checker : loaders) {
            checkerList.add(checker);
        }
    }

    /**
     * Validate the object with default group
     *
     * @param t target
     */
    public static <T> void validate(T t) throws Exception {
        validate(t, DEFAULT_GROUP, false);
    }

    /**
     * Validate the object with default group
     *
     * @param t         target
     * @param serialize print target on exception
     */
    public static <T> void validate(T t, boolean serialize) throws Exception {
        validate(t, DEFAULT_GROUP, serialize);
    }

    /**
     * Validate the object with specify group
     *
     * @param t     target
     * @param group group
     */
    public static <T> void validate(T t, int group) throws Exception {
        validate(t, group, false);
    }

    /**
     * Validate the object with group and serialize switch
     *
     * @param t         target
     * @param group     group
     * @param serialize print target on exception
     */
    public static <T> void validate(T t, int group, boolean serialize) throws Exception {
        if (Objects.isNull(t)) {
            LOG.debug("The object is null");
            return;
        }

        Class<?> cls = t.getClass();
        if (Collection.class.isAssignableFrom(cls)) {
            Collection<?> col = (Collection<?>) t;
            if (col.isEmpty()) {
                return;
            }

            for (Object o : col) {
                validate(o, group, serialize);
            }
            return;
        }

        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            ValidateContext context = new ValidateContext(t, field, group, serialize);
            if (Collection.class.isAssignableFrom(field.getType())) {
                sizeChecker.check(context);
                Collection<?> col = (Collection<?>) field.get(t);
                if (Objects.isNull(col) || col.isEmpty()) {
                    LOG.debug("The field of {} is empty", field.getName());
                    continue;
                }

                for (Object o : col) {
                    validate(o, group, serialize);
                }
                continue;
            }
            fieldCheck(context);
        }
    }

    /**
     * Validate by handler
     *
     * @param context validate context
     */
    private static void fieldCheck(ValidateContext context) throws Exception {
        for (Checker checker : checkerList) {
            checker.check(context);
        }
    }
}
