package ru.job4j.todo.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

/**
 * Тест класс реализации контроллеров
 * @see ru.job4j.todo.controller.LoginController
 * @author Alexander Emelyanov
 * @version 1.0
 */
class LoginControllerTest {

    /**
     * Объект для доступа к методам UserService
     */
    private UserService userService;

    /**
     * Объект для доступа к методам LoginController
     */
    LoginController loginController;

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
        userService = mock(UserService.class);
        loginController = new LoginController(userService);
        model = mock(Model.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        user = User.builder()
                .id(1)
                .name("name")
                .login("login")
                .timezone("Europe/Moscow")
                .build();
    }

    /**
     * Выполняется проверка возвращения страницы логина.
     */
    @Test
    void whenGetLogin() {
        String error = null;
        String logout = null;
        String errorMessage = null;
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");

        String result = loginController.loginPage(error, logout, model, request);

        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(result).isEqualTo("user/login");
    }

    /**
     * Выполняется проверка возвращения страницы логина,
     * при пользовательской ошибке ввода пароля.
     */
    @Test
    void whenLoginPageIfPasswordParameterNotNullThenError() {
        String error = "true";
        String logout = null;
        String errorMessage = "Имя аккаунта или пароль введены неправильно!";
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");

        String template = loginController.loginPage(error, logout, model, request);

        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("user/login");
    }

    /**
     * Выполняется проверка возвращения страницы логина,
     * при выходе пользователя.
     */
    @Test
    void whenLoginPageIfLogoutParameterNotNullThenError() {
        String error = null;
        String logout = "true";
        String errorMessage = "Вы вышли!";
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");

        String template = loginController.loginPage(error, logout, model, request);

        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("user/login");
    }

    /**
     * Выполняется проверка возвращения страницы со списком задач,
     * при удачном входе пользователя.
     */
    @Test
    void whenLoginUserThenTasks() {
        doReturn(user).when(userService).validateUserLogin(user);
        doReturn(session).when(request).getSession();

        String result = loginController.loginUser(user, request);

        verify(session).setAttribute("user", user);
        Assertions.assertThat(result).isEqualTo("redirect:/tasks");
    }

    /**
     * Выполняется проверка возвращения страницы логина,
     * при выходе пользователя.
     */
    @Test
    void whenLogout() {
        String template = loginController.logoutPage(session);

        verify(session).invalidate();
        Assertions.assertThat(template).isEqualTo("redirect:/login?logout=true");
    }
}