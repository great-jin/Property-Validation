package xyz.ibudai.validate.core.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import xyz.ibudai.validate.common.consts.SystemProperty;
import xyz.ibudai.validate.common.encrypt.DataEncryption;

import java.io.IOException;

public class EncryptedSerializer extends JsonSerializer<String> {

    private DataEncryption dataEncryption;

    public EncryptedSerializer() {
    }

    public EncryptedSerializer(DataEncryption dataEncryption) {
        this.dataEncryption = dataEncryption;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String key1 = System.getProperty(SystemProperty.ENCODE_KEY1);
        String key2 = System.getProperty(SystemProperty.ENCODE_KEY2);
        String encryptedValue = dataEncryption.encode(value, key1, key2);
        gen.writeString(encryptedValue);
    }
}
