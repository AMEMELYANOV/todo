package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import ru.job4j.todo.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест класс реализации хранилища пользователей
 * @see ru.job4j.todo.repository.UserRepository
 * @author Alexander Emelyanov
 * @version 1.0
 */
class HibernateUserRepositoryTest {

    /**
     * Объект репозитория UserRepository
     */
    UserRepository userRepository;

    /**
     * Пользователь
     */
    User user;

    /**
     * Создание объекта (bean), используемого для
     * подключения к базе данных приложения,
     * параметры считываются из файла /resources/hibernate.cfg.xml
     *
     * @return объект (фабрика сессий)
     */
    @Bean(destroyMethod = "close")
    SessionFactory sf() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        userRepository = new HibernateUserRepository(sf());
        user = User.builder()
                .name("administrator")
                .login("login")
                .password("password")
                .timezone("Europe/Moscow")
                .build();
        userRepository.add(user);
    }

    /**
     * Создается объект user и сохраняется в базе данных.
     * Через вызов метода {@link UserRepository#findUserByLogin(String)}
     * получаем user из базы данных.
     * Выполняем проверку на эквивалентность начальному объекту user
     * по полю name.
     */
    @Test
    void whenFindUserByIdThenGetUserFromDB() {
        User userFromDB = userRepository.findUserByLogin(user.getLogin()).get();

        assertThat(userFromDB.getName()).isEqualTo(user.getName());
    }

    /**
     * Создается объект user и сохраняется в базе данных.
     * Через вызов метода {@link UserRepository#findUserByLogin(String)}
     * по логин, которого нет в базе данных, получаем Optional.empty
     */
    @Test
    void whenFindUserByIdThenGetOptionalEmpty() {
        Optional<User> userFromDB = userRepository.findUserByLogin(user.getLogin() + 1);

        assertThat(userFromDB).isEqualTo(Optional.empty());
    }

    /**
     * Создается объект user и сохраняется в базе данных.
     * Выполняется изменение данных с обновлением объекта user в
     * базе данных при помощи метода {@link UserRepository#update(User)}.
     * Результат обновления записывается в переменную updatedUser,
     * далее проверяется его эквивалентность объекту user
     * по полю id.
     */
    @Test
    public void whenUpdateUserThenGetTheSameFromDatabase() {
        user.setName("user2");
        User updatedUser = userRepository.update(user).get();

        assertThat(user.getId()).isEqualTo(updatedUser.getId());
    }


    /**
     * Создается объект user и сохраняется в базе данных.
     * По полю id объект user находится в базе данных, сохраняется в объект userFromDB
     * при помощи метода {@link UserRepository#findUserByLogin(String)}
     * и проверяется его эквивалентность объекту user по полю name.
     */
    @Test
    void whenSaveUserThenGetTheSameFromDatabase() {
        User userFromDB = userRepository.findUserByLogin(user.getLogin()).get();

        assertThat(user.getName()).isEqualTo(userFromDB.getName());
    }
}