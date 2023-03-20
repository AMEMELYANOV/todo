package ru.job4j.todo.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

/**
 * Тест класс реализации контроллеров
 * @see ru.job4j.todo.controller.UserController
 * @author Alexander Emelyanov
 * @version 1.0
 */
class UserControllerTest {

    /**
     * Объект для доступа к методам UserService
     */
    private UserService userService;

    /**
     * Объект для доступа к методам UserController
     */
    UserController userController;

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
     * Ошибки валидации
     */
    Errors errors;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        model = mock(Model.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        errors = mock(Errors.class);
        user = User.builder()
                .id(1)
                .name("name")
                .login("login")
                .timezone("Europe/Moscow")
                .password("password")
                .build();
    }

    /**
     * Выполняется проверка возвращения страницы редактирования пользователя,
     * при пользовательской ошибке ввода пароля.
     */
    @Test
    void whenGetUserEditPageIfPasswordParameterNotNullThenError() {
        String password = "true";
        String errorMessage = "Неверно введен старый пароль";
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");

        String template = userController.getUserEdit(password, model, request);

        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("user/userEdit");
    }

    /**
     * Выполняется проверка возвращения страницы редактирования пользователя.
     */
    @Test
    void whenGetUserEditPageSuccess() {
        String password = null;
        String errorMessage = null;
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");

        String template = userController.getUserEdit(password, model, request);

        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("user/userEdit");
    }

    /**
     * Выполняется проверка возвращения страницы списка задач при удачном
     * обновлении пользователя.
     */
    @Test
    void whenUserEditPagePostSuccess() {
        String oldPassword = "password";
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");
        doReturn(user).when(userService).findUserByLogin(user.getLogin());

        String template = userController.userEdit(user, errors, oldPassword, request);

        verify(userService).update(user);
        verify(session).setAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("redirect:/tasks");
    }

    /**
     * Выполняется проверка возвращения страницы редактирования пользователя
     * при наличии ошибок формы редактирования.
     */
    @Test
    void whenUserEditPageHasErrors() {
        String oldPassword = null;
        doReturn(true).when(errors).hasErrors();

        String template = userController.userEdit(user, errors, oldPassword, request);

        Assertions.assertThat(template).isEqualTo("user/userEdit");
    }

    /**
     * Выполняется проверка возвращения страницы редактирования пользователя
     * при наличии ошибок ввода пароля на форме редактирования.
     */
    @Test
    void whenUserEditPageIfOldPasswordIncorrect() {
        String oldPassword = "pass";
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");
        doReturn(user).when(userService).findUserByLogin(user.getLogin());

        String template = userController.userEdit(user, errors, oldPassword, request);

        Assertions.assertThat(template).isEqualTo("redirect:/userEdit?password=true");
    }
}