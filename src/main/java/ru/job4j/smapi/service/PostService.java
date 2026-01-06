package ru.job4j.smapi.service;

import ru.job4j.smapi.dto.PostDto;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PostService {
    Post save(PostDto postDto);
    Boolean updatePost(PostDto postReplaceDto);
    Boolean deletePost(Integer id);
}
