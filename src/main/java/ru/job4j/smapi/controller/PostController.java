package ru.job4j.smapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.service.PostService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
   private PostService postService;

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
}
