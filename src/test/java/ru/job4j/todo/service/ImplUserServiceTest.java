package ru.job4j.todo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

class ImplUserServiceTest {

    /**
     Моск объекта репозитория
     */
    private UserRepository userRepository;

    /**
     * Объект для внедрения моков
     */
    private UserService userService;

    /**
     * Категория
     */
    User user;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new ImplUserService(userRepository);
        user = User.builder()
                .id(1)
                .name("user")
                .timezone("Europe/Moscow")
                .login("login")
                .password("password")
                .build();
    }

    /**
     * Выполняется проверка возврата пользователя, при возврате от
     * userRepository Optional.of(user), т.е. если пользователь был сохранен.
     */
    @Test
    void whenAddUserThenReturnUser() {
        doReturn(Optional.of(user)).when(userRepository).add(user);
        User userFromDB = userService.add(user);
        assertThat(userFromDB).isEqualTo(user);
        assertThat(userFromDB).isNotNull();
    }

    /**
     * Выполняется проверка выброса исключения, при возврате от
     * userRepository Optional.empty(), если пользователь не был сохранен.
     */
    @Test
    void whenAddUserThenThrowsException() {
        doReturn(Optional.empty()).when(userRepository).add(user);

        assertThrows(IllegalArgumentException.class, () -> userService.add(user));
    }

    /**
     * Выполняется проверка обновление пользователя, при возврате от
     * userRepository, т.е. если пользователь был обновлен.
     */
    @Test
    void whenUpdateUserThenReturnUser() {
        doReturn(Optional.of(user)).when(userRepository).update(user);
        User updatedUser = userService.update(user);

        assertThat(updatedUser).isEqualTo(user);
    }

    /**
     * Выполняется проверка выброса исключения, при возврате от
     * userRepository false, т.е. если пользователь не был обновлен.
     */
    @Test
    void whenUpdateUserThenThrowsException() {
        doReturn(Optional.empty()).when(userRepository).update(user);

        assertThrows(NoSuchElementException.class, () -> userService.update(user));
    }

    /**
     * Выполняется проверка возвращения пользователя, при возврате
     * от userRepository Optional.of(user), т.е. если пользователь найден по login.
     */
    @Test
    void whenFindUserByLoginThenReturnUser() {
        doReturn(Optional.of(user)).when(userRepository).findUserByLogin(user.getLogin());
        User userFromDB = userService.findUserByLogin(user.getLogin());

        assertThat(userFromDB).isEqualTo(user);
    }

    /**
     * Выполняется проверка выброса исключения, при возврате от
     * userRepository Optional.empty(), если пользователь не найден по login.
     */
    @Test
    void whenFindUserByLoginThenThrowsException() {
        doReturn(Optional.empty()).when(userRepository).findUserByLogin(user.getLogin());

        assertThrows(NoSuchElementException.class,
                () -> userService.findUserByLogin(user.getLogin()));
    }

    /**
     * Выполняется проверка валидации пользователя, при возврате
     * от userRepository Optional.of(user), т.е. если пользователь найден по login.
     */
    @Test
    void whenValidateUserByLoginThenReturnUser() {
        doReturn(Optional.of(user)).when(userRepository).findUserByLogin(anyString());
        User userFromDB = userService.validateUserLogin(user);

        assertThat(userFromDB).isEqualTo(user);
    }

    /**
     * Выполняется проверка выброса исключения, при возврате от
     * userRepository Optional.empty(), если пользователь не найден по login.
     */
    @Test
    void whenValidateUserByLoginThenThrowsException() {
        User user2 = User.builder()
                .id(1)
                .name("user")
                .timezone("Europe/Moscow")
                .login("login")
                .password("password1")
                .build();
        doReturn(Optional.of(user2)).when(userRepository).findUserByLogin(anyString());

        assertThrows(IllegalArgumentException.class,
                () -> userService.validateUserLogin(user));
    }
}