package ru.job4j.smapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Integer id;

    @NotBlank(message = "title не может быть пустым")
    @Length(min = 1,
            max = 50,
            message = "title должно быть не менее 1 и не более 50 символов")
    private String title;

    @NotBlank(message = "content не может быть пустым")
    private String content;

    private String imageUrl;
}
