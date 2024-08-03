package xyz.ibudai.validate.test.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.ibudai.validate.common.consts.ValidateConst;
import xyz.ibudai.validate.core.annotation.json.Sensitive;
import xyz.ibudai.validate.core.annotation.trigger.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter(ValidateConst.FILTER_SENSITIVE)
public class Teacher {

    @NotBlank(group = 2)
    private String id;

    @NotBlank(triggered = "T(xyz.ibudai.validate.core.util.StringUtils).isNotBlank(id)")
    private String name;

    @Sensitive
    private String password;

    public Teacher(String id) {
        this.id = id;
    }

    public Teacher(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
