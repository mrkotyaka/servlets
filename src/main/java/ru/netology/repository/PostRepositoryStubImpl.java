package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    private final Map<Long, Post> postMap;
    private final AtomicLong postIds;

    public PostRepositoryStubImpl() {
        postMap = new ConcurrentHashMap<>();
        postIds = new AtomicLong(1);
    }

    public List<Post> all() {
        return new ArrayList<>(postMap.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(postMap.get(id));
    }

    public Post save(Post post) {
        while (postMap.containsKey(postIds.longValue())) postIds.getAndIncrement();

        post.setId(postIds.getAndIncrement());
        postMap.put(post.getId(), post);

        return post;
    }

    public Post save(long id, Post post) {
        if (id == 0) return save(post);

        post.setId(id);
        postMap.put(id, post);

        return post;
    }

    public void removeById(long id) {
        if (postMap.containsKey(id)) {
            postMap.remove(id);
        } else {
            throw new NotFoundException("Post with id " + id + " not found");
        }
    }
}
