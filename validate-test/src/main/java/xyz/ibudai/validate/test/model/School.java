package xyz.ibudai.validate.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.ibudai.validate.core.annotation.trigger.NotBlank;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class School {

    @NotBlank
    private String id;

    List<String> names;

    List<Teacher> teachers;

    public School(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public School(String id, List<Teacher> teachers) {
        this.id = id;
        this.teachers = teachers;
    }
}
