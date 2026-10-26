package ru.job4j.smapi.dto.frienddto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FriendResponseDto {
    private Integer userId;
    private String name;
    private String status;
    private LocalDateTime createdAt;
}
