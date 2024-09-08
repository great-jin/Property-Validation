package xyz.ibudai.validate.test.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import xyz.ibudai.validate.core.annotation.json.Encryption;
import xyz.ibudai.validate.core.bean.ValidateBeans;

public class JsonTest {

    @Test
    public void demo1() throws JsonProcessingException {
        ObjectMapper mapper = ValidateBeans.mapper;

        String s = mapper.writeValueAsString(new User("Alex", "123"));
        System.out.println(s);

        System.out.println(mapper.readValue(s, User.class));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {

        private String username;

        @Encryption
        private String password;
    }
}
