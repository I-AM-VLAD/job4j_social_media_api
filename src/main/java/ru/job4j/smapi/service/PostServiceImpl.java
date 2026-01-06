package ru.job4j.smapi.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.smapi.dto.PostDto;
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
    public Post save(PostDto postDto) {
        Post post = fromDtoToPost(postDto);
        return postRepository.save(post);
    }

    @Transactional
    public Boolean updatePost(PostDto postReplaceDto) {
        boolean flag = false;
        if (postReplaceDto != null) {
            Post post = postRepository.findById(postReplaceDto.getId()).orElseThrow();
            post.setTitle(postReplaceDto.getTitle());
            post.setContent(postReplaceDto.getContent());
            post.setImageUrl(postReplaceDto.getImageUrl());
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

    public Post fromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageUrl(postDto.getImageUrl());
        return post;
    }

}
