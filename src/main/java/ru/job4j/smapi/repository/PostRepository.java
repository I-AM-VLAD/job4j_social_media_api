package ru.job4j.smapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    Page<Post> findByOrderByCreatedAtDesc(Pageable pageable);
}
