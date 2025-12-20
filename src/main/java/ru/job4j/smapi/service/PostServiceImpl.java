package ru.job4j.smapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.repository.PostRepository;

import java.time.LocalDateTime;

public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public void save(Post post) {
        postRepository.save(post);
    }

    public void updatePost(Post postReplace) {
        Post post = postRepository.findById(postReplace.getId()).orElseThrow();
        post.setTitle(postReplace.getTitle());
        post.setContent(postReplace.getContent());
        post.setImageUrl(postReplace.getImageUrl());
        post.setUpdatedAt(postReplace.getUpdatedAt());
        postRepository.save(post);
    }

    public void deletePost(Integer id) {
        postRepository.deleteByIdHql(id);
    }

}
