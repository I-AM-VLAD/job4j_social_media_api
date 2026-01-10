package ru.job4j.smapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.smapi.dto.UserDto;

@Tag(name = "UserController", description = "UserController management APIs")
@RequestMapping("/api/user")
public interface UserControllerInterface {

    @Operation(
            summary = "Save UserDto ",
            description = "Save UserDto. The response is userDto",
            tags = { "UserDto", "save" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    ResponseEntity<UserDto> save(@Valid @RequestBody UserDto userDto);

    @Operation(
            summary = "Delete user by userId ",
            description = "Delete user. The response is Void",
            tags = { "Void", "deleteById" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteById(@PathVariable int userId);

    @Operation(
            summary = "Update userDto ",
            description = "Update userDto. The response is Void",
            tags = { "Void", "update" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @PutMapping
    ResponseEntity<Void> update(@Valid @RequestBody UserDto userDto);
}
