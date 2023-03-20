package ru.job4j.todo.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Тест класс реализации контроллеров
 * @see ru.job4j.todo.controller.TaskController
 * @author Alexander Emelyanov
 * @version 1.0
 */
class TaskControllerTest {

    /**
     * Объект для доступа к методам TaskService
     */
    private TaskService taskService;

    /**
     * Объект для доступа к методам CategoryService
     */
    private CategoryService categoryService;

    /**
     * Объект для доступа к методам TaskController
     */
    TaskController taskController;

    /**
     * Задача
     */
    private Task task;

    /**
     * Пользователь
     */
    private User user;

    /**
     * Запрос
     */
    HttpServletRequest request;

    /**
     * Сессия
     */
    HttpSession session;

    /**
     * Модель
     */
    Model model;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        taskService = mock(TaskService.class);
        categoryService = mock(CategoryService.class);
        taskController = new TaskController(taskService, categoryService);
        model = mock(Model.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        user = User.builder()
                .id(1)
                .name("name")
                .login("login")
                .timezone("Europe/Moscow")
                .password("password")
                .build();
        task = Task.builder()
                .id(1)
                .name("name")
                .created(LocalDateTime.now())
                .build();
    }

    /**
     * Выполняется проверка возвращения страницы списка всех задач.
     */
    @Test
    void whenGetAllTasks() {
        List<Task> tasks = new ArrayList<>();
                doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");
        doReturn(tasks).when(taskService).findAllTasks();

        String template = taskController.getTasks(model, request);

        verify(model).addAttribute("tasks", tasks);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("task/tasks");
    }

    /**
     * Выполняется проверка возвращения страницы списка новых задач.
     */
    @Test
    void whenGetNewTasks() {
        List<Task> tasks = new ArrayList<>();
                doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");
        doReturn(tasks).when(taskService).findNewTasks();

        String template = taskController.getTasks(model, request);

        verify(model).addAttribute("tasks", tasks);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("task/tasks");
    }

    /**
     * Выполняется проверка возвращения страницы списка выполненных задач.
     */
    @Test
    void whenGetDoneTasks() {
        List<Task> tasks = new ArrayList<>();
                doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");
        doReturn(tasks).when(taskService).findDoneTasks();

        String template = taskController.getTasks(model, request);

        verify(model).addAttribute("tasks", tasks);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("task/tasks");
    }

    /**
     * Выполняется проверка возвращения страницы добавления новой задачи.
     */
    @Test
    void whenAddTask() {
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");

        String template = taskController.addTask(model, request);

        verify(model).addAttribute("task", new Task());
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("task/addTask");
    }

    /**
     * Выполняется проверка возвращения страницы списка задач,
     * после сохранения задачи.
     */
    @Test
    void whenSaveTaskThenRedirectTasks() {
        List<Integer> categoryIds = List.of(1, 2);
        List<Category> categories = new ArrayList<>();
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");
        doReturn(categories).when(categoryService).findCategoriesByIds(categoryIds);

        String template = taskController.saveTask(task, categoryIds, request);

        Assertions.assertThat(template).isEqualTo("redirect:/tasks");
    }

    /**
     * Выполняется проверка возвращения страницы с подробным описанием задачи.
     */
    @Test
    void whenGetTaskDetails() {
        int taskId = 1;
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");
        doReturn(task).when(taskService).findTaskById(taskId);

        String template = taskController.getTaskDetails(taskId, model, request);

        verify(model).addAttribute("task", task);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("task/taskDetails");
    }

    /**
     * Выполняется проверка возвращения страницы с подробным описанием задачи,
     * после изменения статуса задачи на выполненную.
     */
    @Test
    void whenGetTaskDoneThenTaskDetails() {
        int taskId = 1;
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");
        doReturn(task).when(taskService).taskDone(taskId);

        String template = taskController.taskDone(taskId, model, request);

        verify(model).addAttribute("task", task);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("task/taskDetails");
    }

    /**
     * Выполняется проверка возвращения страницы для редактирования задачи.
     */
    @Test
    void whenGetEditTask() {
        int taskId = 1;
        List<Category> categories = new ArrayList<>();
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");
        doReturn(task).when(taskService).findTaskById(taskId);
        doReturn(categories).when(categoryService).findAllCategories();

        String template = taskController.editTask(taskId, model, request);

        verify(model).addAttribute("categories", categories);
        verify(model).addAttribute("task", task);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("task/editTask");
    }


    /**
     * Выполняется проверка возвращения страницы списка задач,
     * после удаления задачи.
     */
    @Test
    void whenDeleteTaskThenRedirectTasks() {
        int taskId = 1;

        String template = taskController.deleteTask(taskId);

        Assertions.assertThat(template).isEqualTo("redirect:/tasks");
    }
}