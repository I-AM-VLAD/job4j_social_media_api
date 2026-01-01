package ru.job4j.smapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.job4j.smapi.model.Post;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    private List<Post> posts = new ArrayList<>();
}
