package ru.job4j.smapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Save postDto ",
            description = "Save postDto. The response is postDto",
            tags = { "PostDto", "save" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = PostDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
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

    @Operation(
            summary = "Delete post by postId ",
            description = "Delete post. The response is Void",
            tags = { "Void", "deleteById" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteById(@PathVariable int postId) {
        if (postService.deletePost(postId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Update postDto ",
            description = "Update postDto. The response is Void",
            tags = { "Void", "update" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
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
