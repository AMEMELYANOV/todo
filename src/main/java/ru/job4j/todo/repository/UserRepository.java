package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * Хранилище пользователей
 * @see ru.job4j.todo.model.User
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface UserRepository {

    /**
     * Выполняет поиск пользователя по логину.
     *
     * @param login логин (почтовый адрес)
     * @return Optional с найденным объектом user
     */
    Optional<User> findUserByLogin(String login);

    /**
     * Выполняет обновление пользователя
     *
     * @param user пользователь
     * @return Optional с обновленным объектом user
     */
    Optional<User> update(User user);

    /**
     * Выполняет добавление пользователя. Возвращает
     * пользователя с проинициализированным id.
     *
     * @param user пользователь
     * @return Optional с сохраненным объектом user
     */
    Optional<User> add(User user);
}
