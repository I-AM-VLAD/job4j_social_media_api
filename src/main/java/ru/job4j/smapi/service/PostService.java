package ru.job4j.smapi.service;

import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PostService {
    Post save(Post post);
    Boolean updatePost(Post postReplace);
    Boolean deletePost(Integer id);
}
