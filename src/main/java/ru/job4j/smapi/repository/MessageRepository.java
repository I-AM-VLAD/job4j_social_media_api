package ru.job4j.smapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.smapi.model.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
