package xyz.ibudai.validate.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import xyz.ibudai.validate.core.annotation.json.Sensitive;

import java.util.Objects;

public class SensitiveFilter extends SimpleBeanPropertyFilter {

    @Override
    public void serializeAsField(Object obj, JsonGenerator generator, SerializerProvider provider, PropertyWriter writer) throws Exception {
        if (Objects.isNull(writer.getAnnotation(Sensitive.class))) {
            writer.serializeAsField(obj, generator, provider);
        }
    }
}
