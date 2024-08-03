package xyz.ibudai.validate.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.ibudai.validate.core.annotation.group.NotBlankGroup;
import xyz.ibudai.validate.core.annotation.group.StringOptionGroup;
import xyz.ibudai.validate.core.annotation.trigger.NotBlank;
import xyz.ibudai.validate.core.annotation.trigger.StringOption;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @NotBlankGroup({
            @NotBlank(message = "username can't be blank"),
            @NotBlank(message = "username can't be blank", group = 1)
    })
    private String username;

    @NotBlankGroup({
            @NotBlank(message = "password can't be null", group = 1)
    })
    private String password;

    @StringOptionGroup({
            @StringOption(value = {"Beijing", "Shanghai"}, triggered = "username == 'Alex'"),
            @StringOption(value = {"Guangzhou", "Shenzhen"}, group = 1)
    })
    private String city;

    public Student(String username) {
        this.username = username;
    }
}
