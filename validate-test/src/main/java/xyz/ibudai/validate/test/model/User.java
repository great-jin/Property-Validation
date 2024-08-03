package xyz.ibudai.validate.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.ibudai.validate.core.annotation.trigger.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotBlank
    private String id;

    @Length(min = 2, max = 5)
    private String name;

    @Range(min = 18, max = 24)
    private String age;

    @Null
    private Date birthday;

    @StringOption(value = {"Beijing"})
    private String city;

    @IntOption(value = {0, 1})
    private Integer locked;

    @Size(min = 1, max = 10)
    private List<String> roleList;
}
