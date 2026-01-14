# Homework assignment for lesson “2.1. Servlet Containers”

As your solution, please send links to your GitHub projects in your personal student account on the website [netology.ru](https://netology.ru).

**Important information**

1. Before you start, please study the links on the main page of the [homework repository](../README.md).
2. If you encounter any problems, please create an issue [according to the established rules](../report-requirements.md).

## How to submit assignments

1. Create a Maven project on your computer.
1. Initialize an empty Git repository in it.
1. Add the ready-made [.gitignore](../.gitignore) file to it.
1. Add the rest of the necessary files to the same directory.
1. Make the necessary commits.
1. Create a public repository on GitHub and link your local repository to the remote one.
1. Push: make sure your code appears on GitHub.
1. Send a link to your project in your personal account on the [netology.ru](https://netology.ru) website.

## CRUD

### Legend

As part of the lecture, we implemented a nearly complete In-Memory CRUD (Create Read Update Delete) server based on servlets. This server is missing two things:

1. Bring the code into proper form: move methods to constants, remove duplicate code.
1. Implement a repository — for now, a placeholder is installed instead of a repository.

### Task
1. Refactor the code.
1. Implement the repository taking into account that repository methods can be called concurrently, i.e., in different threads.

How `save` should work:

1. If a post with id=0 comes from the client, it means that a new post is being created. You save it in the list and assign it a new id. It is enough to keep a counter with an integer and increase it by 1 when each new post is created.
1. If a post with id !=0 comes from the client, it means that this is a save (update) of an existing post. You search for it in the list by id and update it. Think for yourself what you will do if there is no post with such an id: there may be different strategies here.

### Result

As a solution, send a link to your GitHub repository in your personal student account on the [netology.ru](https://netology.ru) website.

## WebApp Runner* (task with an asterisk)

This is an optional task; its completion does not affect your grade.

### Legend

It is not always convenient to “carry” a full-fledged Tomcat with you: download it, unpack it, etc. The [WebApp Runner](https://github.com/heroku/webapp-runner) library (formerly com.github.jsimone webapp-runner) is used quite often.

Embedding WebApp Runner into your project allows you to run it like this: `java -jar target/dependency/webapp-runner.jar target/<appname>.war`. This is quite convenient for hosting on cloud platforms.

### Task
Add the `webapp-runner` download to your build according to the [instructions](https://github.com/heroku/webapp-runner#using-with-maven-in-your-project).

Ensure that the build is successful and that your war file actually runs with the command specified above.

### Result

Implement the new functionality in the `feature/webapp-runner` branch of your repository from the previous homework assignment and open a Pull Request.

As a result, send a link to your Pull Request on GitHub in your personal student account on the [netology.ru](https://netology.ru) website.

Once your homework assignment has been accepted, perform a `merge` for the Pull Request.


# Homework assignment for lesson “2.2. Dependency Lookup, Dependency Injection, IoC, Spring, Application Context”

As a solution, send links to your GitHub projects in your personal student account on the [netology.ru](https://netology.ru) website.

**Important information**

1. Before you start, please study the links on the main page of the [homework repository](../README.md).
2. If you encounter any difficulties, please create an issue [according to the established rules](../report-requirements.md).

## How to submit assignments
1. Take the project from the previous lecture.
1. Create branches `feature/di-annotation` and `feature/di-java` in it, in which you implement the corresponding functionality.
1. Push, make sure your code appears on GitHub, and create a Pull Request from both branches.
1. Send the link to the pull request in your personal account on the [netology.ru](https://netology.ru) website.

## DI

### Legend

In this lecture, we looked at how to use Spring to bind dependencies.

The question arises, why not use it in your application with servlets and replace the code below with DI with Spring:
```java
@Override
public void init() {
    final var repository = new PostRepository();
    final var service = new PostService(repository);
    controller = new PostController(service);
}
```

### Task

Replace the code in the `init` method with Spring DI using bean configuration methods:

1. Annotation Config — `feature/di-annotation` branch.
1. Java Config — `feature/di-java` branch.

Please note that all functionality (CRUD) implemented previously must continue to work.

### Result

As a solution, send a link to your Pull Request in your personal student account on the [netology.ru](https://netology.ru) website.
