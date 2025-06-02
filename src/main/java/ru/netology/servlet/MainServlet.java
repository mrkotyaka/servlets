package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.JavaConfig;
import ru.netology.controller.PostController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private final static String POST = "POST";
    private final static String GET = "GET";
    private final static String DELETE = "DELETE";
    private final static String API = "/api/posts";
    private final static String API_ID = "/api/posts/\\d+";

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext(JavaConfig.class);
        controller = context.getBean(PostController.class);
//        final var service = context.getBean(PostService.class);
//        final var isSame = service ==context.getBean(PostService.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals(GET) && path.equals(API)) {
                controller.all(resp);
                return;
            }
            if (method.equals(GET) && path.matches(API_ID)) {
                // easy way
                final var id = Long.parseLong(getIdPost(path));
                controller.getById(id, resp);
                return;
            }
            if (method.equals(POST) && path.equals(API)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(POST) && path.matches(API_ID)) {
                // new
                final var id = Long.parseLong(getIdPost(path));
                controller.save(id, req.getReader(), resp);
                return;
            }
            if (method.equals(DELETE) && path.matches(API_ID)) {
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

