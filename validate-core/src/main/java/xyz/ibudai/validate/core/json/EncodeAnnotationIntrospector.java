package xyz.ibudai.validate.core.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.Annotated;
import xyz.ibudai.validate.common.encrypt.DataEncryption;
import xyz.ibudai.validate.core.annotation.json.Encryption;
import xyz.ibudai.validate.core.json.serializer.DecryptedSerializer;
import xyz.ibudai.validate.core.json.serializer.EncryptedSerializer;

import java.lang.reflect.Constructor;

public class EncodeAnnotationIntrospector extends AnnotationIntrospector {

    @Override
    public Version version() {
        return VersionUtil.parseVersion("2.17.2", "com.fasterxml.jackson.core", "jackson-databind");
    }

    @Override
    public Object findSerializer(Annotated annotated) {
        Class<Encryption> encodeClass = Encryption.class;
        if (annotated.hasAnnotation(encodeClass)) {
            // 返回加解密序列化器
            Encryption annotation = annotated.getAnnotation(encodeClass);
            DataEncryption dataEncryption = this.initAlgorithm(annotation);
            return new EncryptedSerializer(dataEncryption);
        }
        return null;
    }

    @Override
    public Object findDeserializer(Annotated annotated) {
        Class<Encryption> encodeClass = Encryption.class;
        if (annotated.hasAnnotation(encodeClass)) {
            // 返回加解密序列化器
            Encryption annotation = annotated.getAnnotation(encodeClass);
            DataEncryption dataEncryption = this.initAlgorithm(annotation);
            return new DecryptedSerializer<>(dataEncryption);
        }
        return null;
    }

    private DataEncryption initAlgorithm(Encryption encryption) {
        Class<? extends DataEncryption> algorithm = encryption.algorithm();
        try {
            Constructor<? extends DataEncryption> constructor = algorithm.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
