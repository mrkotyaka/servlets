package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;

// Stub
public interface PostRepository {
    List<Post> all();
    Optional<Post> getById(long id);
    Post save(Post post);
    Post save(long id, Post post);
    void removeById(long id);
}
