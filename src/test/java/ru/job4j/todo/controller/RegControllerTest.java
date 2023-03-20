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
 * @see ru.job4j.todo.controller.RegController
 * @author Alexander Emelyanov
 * @version 1.0
 */
class RegControllerTest {

    /**
     * Объект для доступа к методам UserService
     */
    private UserService userService;

    /**
     * Объект для доступа к методам RegController
     */
    RegController regController;

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
        regController = new RegController(userService);
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
     * Выполняется проверка возвращения страницы регистрации.
     */
    @Test
    void whenGetRegistrationPage() {
        String password = null;
        String account = null;
        String errorMessage = null;
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");

        String template = regController.regPage(password, account, model, request);

        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("user/registration");
    }

    /**
     * Выполняется проверка возвращения страницы регистрации,
     * при пользовательской ошибке ввода пароля.
     */
    @Test
    void whenRegistrationPageIfPasswordParameterNotNullThenError() {
        String password = "true";
        String account = null;
        String errorMessage = "Пароли должны совпадать!";
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");

        String template = regController.regPage(password, account, model, request);

        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("user/registration");
    }

    /**
     * Выполняется проверка возвращения страницы регистрации,
     * если email пользователя уже существует.
     */
    @Test
    void whenRegistrationPageIfAccountParameterNotNullThenError() {
        String password = null;
        String account = "true";
        String errorMessage = "Пользователь с таким email существует!";
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute("user");

        String template = regController.regPage(password, account, model, request);

        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("user", user);
        Assertions.assertThat(template).isEqualTo("user/registration");
    }

    /**
     * Выполняется проверка возвращения страницы регистрации,
     * при неправильном заполнении формы ввода.
     */
    @Test
    void whenRegistrationSaveIfError() {
        String repassword = "password";
        doReturn(true).when(errors).hasErrors();

        String template = regController.regSave(user, errors, repassword);

        verify(userService, times(0)).add(user);
        Assertions.assertThat(template).isEqualTo("user/registration");
    }

    /**
     * Выполняется проверка возвращения страницы регистрации,
     * при несовпадении паролей при повторном вводе.
     */
    @Test
    void whenRegistrationSaveIfPasswordNotEqual() {
        String repassword = "pwd";

        String template = regController.regSave(user, errors, repassword);

        verify(userService, times(0)).add(user);
        Assertions.assertThat(template).isEqualTo("redirect:/registration?password=true");
    }

    /**
     * Выполняется проверка возвращения страницы логина,
     * при успешной регистрации пользователя.
     */
    @Test
    void whenRegistrationSaveSuccess() {
        String repassword = "password";

        String template = regController.regSave(user, errors, repassword);

        verify(userService, times(1)).add(user);
        Assertions.assertThat(template).isEqualTo("redirect:/login");
    }
}