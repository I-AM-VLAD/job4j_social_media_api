package ru.job4j.smapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    Page<Post> findByOrderByCreatedAtDesc(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post post SET post.title = :title"
            + " WHERE post.id = :id")
    int updateTitle(@Param("title") String title, @Param("id") Integer id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post post SET post.content = :content"
            + " WHERE post.id = :id")
    int updateContent(@Param("content") String content, @Param("id") Integer id);

    @Modifying
    @Query("UPDATE Post post SET post.imageUrl = null WHERE post.id = :id")
    int deleteImage(@Param("id") Integer id);

    @Modifying
    @Query("DELETE FROM Post p WHERE p.id = :id")
    int deleteByIdHql(@Param("id") Integer id);

    @Query("SELECT p FROM Post p WHERE p.user.id "
            + " IN (SELECT f.followerUser.id FROM Follower f WHERE f.user.id = :userId)"
   + " ORDER BY p.createdAt DESC")
    Page<Post> findPostsOfFollowers(@Param("userId") Integer userId, Pageable pageable);




}
