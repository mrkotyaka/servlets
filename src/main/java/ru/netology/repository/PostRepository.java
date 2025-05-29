package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Stub
public class PostRepository {
    private List<Post> posts;
    private List<Long> postIdList;
    private long postIds = 0;

    final Object lock = new Object();

    public PostRepository() {
        posts = new ArrayList<>();
        postIdList = new ArrayList<>();
        postIdList.add(0L);
    }

    public List<Post> all() {
        return posts; //Collections.emptyList();
    }

    public Optional<Post> getById(long id) {
        return posts.stream().filter(post1 -> post1.getId() == id).findFirst();
    }

    public Post save(Post post) {
        synchronized (lock) {
            while (postIdList.contains(postIds)) {
                postIds++;
            }

            post.setId(postIds);
            posts.add(post);
            postIdList.add(postIds);

//    System.out.println(postIdList + " " + post.getId() + " " + post.getContent());
        }
        return post;
    }

    public Post save(long id, Post post) {

        if (id == 0) return save(post);

        synchronized (lock) {
            if (postIdList.contains(id)) {
                for (Post p : posts) {
                    if (p.getId() == id) {
                        p.setContent(post.getContent());

//          System.out.println(postIdList + " " + post.getId() + " " + post.getContent());

                        return post;
                    }
                }
            }

            post.setId(id);
            posts.add(post);
            postIdList.add(id);

//    System.out.println(postIdList + " " + post.getId() + " " + post.getContent());

            return post;
        }
    }

    public boolean removeById(long id) {
        synchronized (lock) {
            if (posts.removeIf(post -> post.getId() == id)) {
                postIdList.remove(id);

                System.out.println(postIdList + " " + id);

                return true;
            }
            return false;
        }
    }
}
