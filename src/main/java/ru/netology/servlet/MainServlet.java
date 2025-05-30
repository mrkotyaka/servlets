package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private final static String POST = "POST";
    private final static String GET = "GET";
    private final static String DELETE = "DELETE";
    private final static String PATH = "/api/posts";
    private final static String PATH_ID = "/api/posts/\\d+";

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals(GET) && path.equals(PATH)) {
                controller.all(resp);
                return;
            }
            if (method.equals(GET) && path.matches(PATH_ID)) {
                // easy way
                final var id = Long.parseLong(getIdPost(path));
                controller.getById(id, resp);
                return;
            }
            if (method.equals(POST) && path.equals(PATH)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(POST) && path.matches(PATH_ID)) {
                // new
                final var id = Long.parseLong(getIdPost(path));
                controller.save(id, req.getReader(), resp);
                return;
            }
            if (method.equals(DELETE) && path.matches(PATH_ID)) {
                // easy way
                final var id = Long.parseLong(getIdPost(path));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getIdPost(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}

