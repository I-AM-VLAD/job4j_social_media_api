package ru.job4j.smapi.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.repository.PostRepository;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional
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
