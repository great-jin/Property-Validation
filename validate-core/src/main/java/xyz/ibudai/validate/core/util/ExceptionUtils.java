package xyz.ibudai.validate.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.ibudai.validate.common.exception.ValidateException;
import xyz.ibudai.validate.core.bean.ValidateBeans;

public class ExceptionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionUtils.class);

    public static void throwWithMsg(String msg, Object obj, boolean serialize) {
        StringBuilder builder = new StringBuilder(msg);
        if (serialize) {
            try {
                builder.append(", Object: ");
                builder.append(ValidateBeans.mapper.writeValueAsString(obj));
            } catch (JsonProcessingException e) {
                LOG.debug("Parse object failed", e);
            }
        }
        throw new ValidateException(builder.toString());
    }
}
