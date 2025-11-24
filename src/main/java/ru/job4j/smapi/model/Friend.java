package ru.job4j.smapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.smapi.model.compoundkeys.FriendKey;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "friend")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

    @EmbeddedId
    private FriendKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("friendId")
    @JoinColumn(name = "friend_id")
    private User friendUser;

    private String status = "pending";;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
}
