package ru.job4j.smapi.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.smapi.dto.frienddto.FriendRequestDto;
import ru.job4j.smapi.model.Follower;
import ru.job4j.smapi.model.Friend;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.model.compoundkeys.FollowerKey;
import ru.job4j.smapi.model.compoundkeys.FriendKey;
import ru.job4j.smapi.repository.FollowerRepository;
import ru.job4j.smapi.repository.FriendRepository;
import ru.job4j.smapi.repository.UserRepository;
import ru.job4j.smapi.security.userdetails.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowerRepository followerRepository;

    public Friend requestAtoB(FriendRequestDto friendRequestDto, UserDetailsImpl userDetails) {
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        User user = optionalUser.get();
        Optional<User> optionalUserFriend = userRepository.findById(friendRequestDto.getFriendId());
        User userFriend = optionalUserFriend.get();

        var friendKey = new FriendKey();
        friendKey.setUserId(user.getId());
        friendKey.setFriendId(userFriend.getId());
        var friend = new Friend();
        friend.setId(friendKey);
        friend.setUser(user);
        friend.setFriendUser(userFriend);
        friend.setStatus("pending");

        friendRepository.save(friend);

        return friend;
    }

    public List<Friend> getIncomingFriendRequests(UserDetailsImpl userDetails) {

        return friendRepository.findByIdFriendIdAndStatus(userDetails.getId(), "pending");
    }

    public boolean updateStatus(FriendRequestDto friendRequestDto, UserDetailsImpl userDetails,
                                     String newStatus) {
        List<Friend> friendsWithStatusPending  = getIncomingFriendRequests(userDetails);
        Friend selectedFriend = null;
        boolean flag = false;

        for (Friend oneFriend : friendsWithStatusPending) {
                if (oneFriend.getId().getUserId().equals(friendRequestDto.getFriendId())) {
                    oneFriend.setStatus(newStatus);
                    selectedFriend = oneFriend;
                }
        }

        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        User user = optionalUser.get();
        Optional<User> optionalUserFriend = userRepository.findById(friendRequestDto.getFriendId());
        User userFriend = optionalUserFriend.get();

        var reverseFriendKey = new FriendKey();
        reverseFriendKey.setUserId(userFriend.getId());
        reverseFriendKey.setFriendId(user.getId());

        if (newStatus.equals("accepted")) {
            // здесь создаем статус accepted у моего дуга
            friendRepository.save(selectedFriend);

            // здесь создается статус accepted у меня
            Friend reverseFriend = new Friend(reverseFriendKey, userFriend, user, "accepted");

            friendRepository.save(reverseFriend);

            var follower = new Follower();
            var followerKey = new FollowerKey();
            followerKey.setUserId(user.getId());
            followerKey.setFollowerId(userFriend.getId());

            follower.setId(followerKey);
            follower.setUser(user);
            follower.setFollowerUser(userFriend);
            followerRepository.save(follower);

            var followerReverse = new Follower();
            var followerKeyReverse = new FollowerKey();
            followerKeyReverse.setUserId(userFriend.getId());
            followerKeyReverse.setFollowerId(user.getId());

            followerReverse.setId(followerKeyReverse);
            followerReverse.setUser(userFriend);
            followerReverse.setFollowerUser(user);
            followerRepository.save(followerReverse);

            flag = true;
        }

        if (newStatus.equals("rejected")) {
            // здесь создаем статус rejected у моего дуга
            friendRepository.save(selectedFriend);

            // здесь создается статус rejected у меня
            Friend reverseFriend = new Friend(reverseFriendKey, userFriend, user, "rejected");

            friendRepository.save(reverseFriend);

            var follower = new Follower();
            var followerKey = new FollowerKey();
            followerKey.setUserId(userFriend.getId());
            followerKey.setFollowerId(user.getId());

            follower.setId(followerKey);
            follower.setUser(user);
            follower.setFollowerUser(userFriend);
            followerRepository.save(follower);

            flag = true;
        }

        return flag;
    }

    public boolean deleteFriend(FriendRequestDto friendRequestDto, UserDetailsImpl userDetails) {
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        User user = optionalUser.get();
        Optional<User> optionalFriendUser = userRepository.findById(friendRequestDto.getFriendId());
        boolean flag = false;

        if (optionalFriendUser.isPresent()) {
            User friendUser = optionalFriendUser.get();
            FollowerKey followerKey = new FollowerKey();
            followerKey.setUserId(user.getId());
            followerKey.setFollowerId(friendUser.getId());

            followerRepository.deleteById(followerKey);

            var friendKey = new FriendKey();
            friendKey.setUserId(user.getId());
            friendKey.setFriendId(friendUser.getId());

            var friendKeyReverse = new FriendKey();
            friendKeyReverse.setUserId(friendUser.getId());
            friendKeyReverse.setFriendId(user.getId());
            if (friendRepository.existsById(friendKeyReverse) && friendRepository.existsById(friendKey)) {
                flag = true;
            }

            friendRepository.deleteById(friendKey);
            friendRepository.deleteById(friendKeyReverse);

        }
        return flag;

    }
}
