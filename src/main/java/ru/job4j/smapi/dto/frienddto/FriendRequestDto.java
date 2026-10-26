package ru.job4j.smapi.dto.frienddto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FriendRequestDto {
    @NotNull(message = "friendId обязателен")
    private Integer friendId;
}