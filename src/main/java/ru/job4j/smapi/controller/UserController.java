package ru.job4j.smapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.smapi.dto.UserDto;
import ru.job4j.smapi.service.UserService;

@AllArgsConstructor
@RestController
public class UserController implements UserControllerInterface {

    @Autowired
    private UserService userService;

    public ResponseEntity<Void> deleteById(int userId) {
        if (userService.deleteById(userId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> update(UserDto userDto) {
        if (userService.update(userDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
