package ru.job4j.smapi.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.smapi.dto.frienddto.FriendRequestDto;
import ru.job4j.smapi.dto.frienddto.FriendResponseDto;
import ru.job4j.smapi.model.Friend;
import ru.job4j.smapi.security.userdetails.UserDetailsImpl;
import ru.job4j.smapi.service.FriendService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/friends")

public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping
    // Здесь я делаю запрос к другу, устанавливается status = "pending"
    public ResponseEntity<FriendResponseDto> requestToFutureFriend(@Valid @RequestBody FriendRequestDto friendRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        var friend = friendService.requestAtoB(friendRequestDto, userDetails);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(friend.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(new FriendResponseDto(
                        friend.getFriendUser().getId(),
                        friend.getFriendUser().getName(),
                        friend.getStatus(),
                        friend.getCreatedAt())
                );
    }

    @GetMapping
    // Здесь мой друг проверяет, какие к нему есть запросы в друзья (может быть не один)

    public ResponseEntity<List<FriendResponseDto>> getIncomingFriendRequests(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<Friend> requestsWithStatusPending = friendService.getIncomingFriendRequests(userDetails);
        List<FriendResponseDto> listResponse = new ArrayList<>();

        for (Friend oneRequest : requestsWithStatusPending) {
            listResponse.add(
                new FriendResponseDto(
                        oneRequest.getUser().getId(),
                        oneRequest.getUser().getName(),
                        oneRequest.getStatus(),
                        oneRequest.getCreatedAt()
                )
            );
        }
        return ResponseEntity.ok(listResponse);
    }


    public ResponseEntity<Void> updateStatus(@Valid @RequestBody FriendRequestDto friendRequestDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                                             String newStatus) {
        if (friendService.updateStatus(friendRequestDto, userDetails, newStatus)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping ("/accepted")
    // Здесь мой друг ставит конкретное значение accepted к конкретному юзеру

    public ResponseEntity<Void> updateStatusOnAccepted(@Valid @RequestBody FriendRequestDto friendRequestDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return updateStatus(friendRequestDto, userDetails, "accepted");
    }

    @PutMapping ("/rejected")
    // Здесь мой друг ставит конкретное значение rejected к конкретному юзеру

    public ResponseEntity<Void> updateStatusOnRejected(@Valid @RequestBody FriendRequestDto friendRequestDto,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return updateStatus(friendRequestDto, userDetails, "rejected");
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFriend(@RequestBody FriendRequestDto friendRequestDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (friendService.deleteFriend(friendRequestDto, userDetails)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
