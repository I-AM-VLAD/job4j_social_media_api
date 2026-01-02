package ru.job4j.smapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.smapi.dto.UserDto;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.service.PostService;
import ru.job4j.smapi.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
   private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post) {
        postService.save(post);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(post);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable int userId) {
        if (postService.deletePost(userId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Post post) {
        if (postService.updatePost(post)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public List<UserDto> toDto(List<Integer> userIds) {
        List<User> users = new ArrayList<>();
        List<UserDto> dtos = new ArrayList<>();
        for (Integer id : userIds) {
            Optional<User> user = userService.getById(id);
            if (user.isPresent()) {
                users.add(user.get());
            }
        }
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
