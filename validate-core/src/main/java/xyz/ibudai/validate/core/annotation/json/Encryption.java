package xyz.ibudai.validate.core.annotation.json;

import xyz.ibudai.validate.common.encrypt.DataEncryption;
import xyz.ibudai.validate.common.encrypt.serializer.Base64Encryption;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Encryption {

    Class<? extends DataEncryption> algorithm() default Base64Encryption.class;
}
