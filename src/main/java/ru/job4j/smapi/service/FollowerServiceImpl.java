package ru.job4j.smapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.smapi.model.Follower;
import ru.job4j.smapi.model.Friend;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.model.compoundkeys.FollowerKey;
import ru.job4j.smapi.model.compoundkeys.FriendKey;
import ru.job4j.smapi.repository.FollowerRepository;
import ru.job4j.smapi.repository.FriendRepository;

public class FollowerServiceImpl implements FollowerService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private FollowerRepository followerRepository;

    public void createFriend(String status, User user, User userFriend) {
        if (status.equals("accepted")) {

            var friendKey = new FriendKey();
            friendKey.setUserId(user.getId());
            friendKey.setFriendId(userFriend.getId());

            var friend = new Friend();
            friend.setId(friendKey);
            friend.setUser(user);
            friend.setFriendUser(userFriend);
            friend.setStatus(status);
            friendRepository.save(friend);

            var friendKeyReverse = new FriendKey();
            friendKeyReverse.setUserId(userFriend.getId());
            friendKeyReverse.setFriendId(user.getId());

            var friendReverse = new Friend();
            friendReverse.setId(friendKeyReverse);
            friendReverse.setUser(userFriend);
            friendReverse.setFriendUser(user);
            friendReverse.setStatus(status);
            friendRepository.save(friendReverse);

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
        }
        if (status.equals("pending") || status.equals("rejected")) {
            var follower = new Follower();
            var followerKey = new FollowerKey();
            followerKey.setUserId(user.getId());
            followerKey.setFollowerId(userFriend.getId());

            follower.setId(followerKey);
            follower.setUser(user);
            follower.setFollowerUser(userFriend);
            followerRepository.save(follower);
        }
    }

    public void deleteFriend(Friend friend) {
        User user = friend.getUser();
        User friendUser = friend.getFriendUser();
        FollowerKey followerKey = new FollowerKey();
        followerKey.setUserId(user.getId());
        followerKey.setFollowerId(friendUser.getId());

        followerRepository.deleteById(followerKey);

        var friendKeyReverse = new FriendKey();
        friendKeyReverse.setUserId(friendUser.getId());
        friendKeyReverse.setFriendId(user.getId());
        friendRepository.deleteById(friendKeyReverse);

        friendRepository.delete(friend);

    }
}
