package xyz.ibudai.validate.common.encrypt;

public interface DataEncryption {

    String encode(String val, String key1, String key2);

    String decode(String val, String key1, String key2);
}
