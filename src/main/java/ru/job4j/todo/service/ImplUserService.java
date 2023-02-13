package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import java.util.NoSuchElementException;

/**
 * Реализация сервиса по работе с пользователями
 * @see ru.job4j.todo.service.UserService
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@Service
public class ImplUserService implements UserService {

    /**
     * Объект для доступа к методам UserRepository
     */
    private final UserRepository userRepository;

    /**
     * Выполняет сохранение пользователя. При успешном сохранении возвращает
     * сохраненного пользователя, иначе выбрасывается исключение.
     *
     * @param user сохраняемый пользователь
     * @return пользователя при успешном сохранении
     * @exception IllegalArgumentException, если сохранение пользователя не произошло
     */
    @Override
    public User add(User user) {
        return userRepository.add(user).orElseThrow(
                () -> new IllegalArgumentException(
                        String.format("Аккаунт с login = %s уже существует!", user.getLogin())));
    }

    /**
     * Выполняет обновление пользователя. Если старый пароль
     * подтвержден.
     *
     * @param user обновляемый пользователь
     * @exception NoSuchElementException, если пользователь не найден
     * @return пользователь при успешном обновлении
     */
    @Override
    public User update(User user) {
         return userRepository.update(user).orElseThrow(
                () -> new NoSuchElementException(
                        String.format("Пользователь с login = %s не обновлен", user.getLogin())));
    }

    /**
     * Выполняет поиск пользователя по логину (почтовому адресу).
     * При успешном нахождении возвращает пользователя, иначе выбрасывает исключение.
     *
     * @param login логин (почтовый адрес) пользователя
     * @return пользователя при успешном нахождении
     * @exception NoSuchElementException, если пользователь не найден
     */
    @Override
    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login).orElseThrow(
                () -> new NoSuchElementException(
                        String.format("Пользователь с login = %s не найден", login)));
    }

    /**
     * Выполняет сверку данных пользователя с входной формы с данными пользователя в базе по
     * логину (почтовому адресу) и паролю. При успешной проверке возвращает пользователя извлеченного
     * из базы данных, иначе выбрасывает исключение.
     * Для нахождения пользователя в базе данных используется метод
     * {@link ImplUserService#findUserByLogin(String)} (String)}.
     *
     * @param user пользователя
     * @return пользователя при успешном при совпадении пароля и логина (почтового адреса)
     * @exception IllegalArgumentException, если пароли пользователя не совпали
     */
    @Override
    public User validateUserLogin(User user) {
        User userFromDB = findUserByLogin(user.getLogin());
        if (!userFromDB.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Неверный пароль пользователя");
        }
        return userFromDB;
    }
}
