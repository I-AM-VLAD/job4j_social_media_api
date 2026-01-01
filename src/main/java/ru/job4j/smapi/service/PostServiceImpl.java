package ru.job4j.smapi.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Boolean updatePost(Post postReplace) {
        boolean flag = false;
        if (postReplace != null) {
            Post post = postRepository.findById(postReplace.getId()).orElseThrow();
            post.setTitle(postReplace.getTitle());
            post.setContent(postReplace.getContent());
            post.setImageUrl(postReplace.getImageUrl());
            post.setUpdatedAt(postReplace.getUpdatedAt());
            postRepository.save(post);
            flag = true;
        }
        return flag;
    }

    public Boolean deletePost(Integer id) {
        boolean flag = false;
        postRepository.deleteById(id);
        if (postRepository.findById(id).isEmpty()) {
            flag = true;
        }
        return flag;
    }

}
