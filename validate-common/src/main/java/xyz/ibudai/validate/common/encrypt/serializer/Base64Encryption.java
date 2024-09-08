package xyz.ibudai.validate.common.encrypt.serializer;

import xyz.ibudai.validate.common.encrypt.DataEncryption;

import java.util.Base64;

public class Base64Encryption implements DataEncryption {

    @Override
    public String encode(String val, String key1, String key2) {
        return Base64.getEncoder().encodeToString(val.getBytes());
    }

    @Override
    public String decode(String val, String key1, String key2) {
        return new String(Base64.getDecoder().decode(val));
    }
}
