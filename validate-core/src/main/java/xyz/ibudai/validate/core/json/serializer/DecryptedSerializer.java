package xyz.ibudai.validate.core.json.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import xyz.ibudai.validate.common.consts.SystemProperty;
import xyz.ibudai.validate.common.encrypt.DataEncryption;

import java.io.IOException;

public class DecryptedSerializer<T> extends JsonDeserializer<String> {

    private DataEncryption dataEncryption;

    public DecryptedSerializer() {
    }

    public DecryptedSerializer(DataEncryption dataEncryption) {
        this.dataEncryption = dataEncryption;
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String val = jsonParser.getText();
        String key1 = System.getProperty(SystemProperty.ENCODE_KEY1);
        String key2 = System.getProperty(SystemProperty.ENCODE_KEY2);
        return dataEncryption.decode(val, key1, key2);
    }
}
