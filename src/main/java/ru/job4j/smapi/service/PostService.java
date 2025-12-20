package ru.job4j.smapi.service;

import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;

import java.time.LocalDateTime;

public interface PostService {
    void save(Post post);
    void updatePost(Post postReplace);
    void deletePost(Integer id);
}
