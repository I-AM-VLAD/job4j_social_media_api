package ru.job4j.smapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.job4j.smapi.model.Post;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;

    @NotBlank(message = "name не может быть пустым")
    @Length(min = 3,
            max = 20,
            message = "name должно быть не менее 3 и не более 20 символов")
    private String name;

    private List<Post> posts = new ArrayList<>();
}
