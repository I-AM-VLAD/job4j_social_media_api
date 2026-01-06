package ru.job4j.smapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.smapi.dto.PostDto;
import ru.job4j.smapi.dto.UserDto;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.service.PostService;
import ru.job4j.smapi.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "PostController", description = "PostController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
   private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<PostDto> save(@Valid @RequestBody PostDto postDto) {
        var post = postService.save(postDto);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(postDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable int userId) {
        if (postService.deletePost(userId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody PostDto postDto) {
        if (postService.updatePost(postDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public List<UserDto> toDto(List<Integer> userIds) {
        List<User> users = userService.findUsers(userIds);
        List<UserDto> dtos = new ArrayList<>();
        for (User user : users) {
            UserDto newUserDto = new UserDto();
            newUserDto.setId(user.getId());
            newUserDto.setName(user.getName());
            newUserDto.setPosts(user.getPosts());
            dtos.add(newUserDto);
        }
        return dtos;
    }

}
