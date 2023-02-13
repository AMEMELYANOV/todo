package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;
import java.util.function.Function;

/**
 * Реализация хранилища пользователей
 * @see TaskRepository
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Repository
@AllArgsConstructor
@Slf4j
public class HibernateUserRepository implements UserRepository {

    /**
     * SQL запрос по выбору задачи из таблицы tasks с фильтром по id
     */
    private final static String FIND_USER_BY_LOGIN = "from User u where u.login = :login";

    /**
     * Объект для выполнения подключения к базе данных приложения
     */
    private final SessionFactory sessionFactory;

    /**
     * Выполняет переданный метод оборачиваю в транзакцию
     *
     * @param command выполняемый метод
     * @return объект результат выполнения переданного метода
     */
    private <T> Optional<T> execute(final Function<Session, T> command) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            transaction.commit();
            return Optional.ofNullable(rsl);
        } catch (final ConstraintViolationException e) {
            session.getTransaction().rollback();
            log.info("Повторяющееся значение ключа нарушает ограничение уникальности todo_users_login_key", e);
            return Optional.empty();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            log.info("Исключение при работе с методами UserRepository", e);
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    /**
     * Выполняет поиск пользователя по логину.
     *
     * @param login логин (почтовый адрес)
     * @return Optional с найденным объектом user
     */
    @Override
    public Optional<User> findUserByLogin(String login) {
        return this.execute(
                session -> {
                    Query<User> query = session.createQuery(FIND_USER_BY_LOGIN, User.class);
                    query.setParameter("login", login);
                    return query.uniqueResult();
                });
    }

    /**
     * Выполняет обновление пользователя.
     *
     * @param user пользователь
     * @return Optional с обновленным объектом user
     */
    @Override
    public Optional<User> update(User user) {
        return this.execute(
                session -> {
                    session.update(user);
                    return user;
                }
        );
    }

    /**
     * Выполняет добавление пользователя. Возвращает
     * пользователя с проинициализированным id.
     *
     * @param user пользователь
     * @return Optional с сохраненным объектом user
     */
    @Override
    public Optional<User> add(User user) {
        return this.execute(
                session -> {
                    session.save(user);
                    return user;
                }
        );
    }
}