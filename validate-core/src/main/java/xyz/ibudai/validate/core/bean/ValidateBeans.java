package xyz.ibudai.validate.core.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import xyz.ibudai.validate.common.consts.ValidateConst;
import xyz.ibudai.validate.core.filter.SensitiveFilter;

public class ValidateBeans {

    public static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapperConfig();
    }

    private static void mapperConfig() {
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter(ValidateConst.FILTER_SENSITIVE, new SensitiveFilter());
        mapper.setFilterProvider(filterProvider);
    }
}
