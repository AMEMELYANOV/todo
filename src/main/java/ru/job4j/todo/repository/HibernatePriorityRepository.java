package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Optional;
import java.util.function.Function;

/**
 * Реализация хранилища приоритетов
 * @see ru.job4j.todo.repository.PriorityRepository
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Repository
@AllArgsConstructor
@Slf4j
public class HibernatePriorityRepository implements PriorityRepository {

    /**
     * SQL запрос по выбору задачи из таблицы priority с фильтром по name
     */
    private final static String FIND_PRIORITY_BY_NAME = "from Priority p where p.name = :name";

    /**
     * SQL запрос по выбору приоритета из таблицы priorities с фильтром по id
     */
    private final static String FIND_PRIORITY_BY_ID = "select distinct p from Priority p where p.id = :id";

    /**
     * SQL запрос по удалению приоритета из таблицы priorities с фильтром по id
     */
    private final static String DELETE_PRIORITY_BY_ID = "delete Priority where id = :id";

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
        } catch (final Exception e) {
            session.getTransaction().rollback();
            log.info("Исключение при работе с методами PriorityRepository", e);
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    /**
     * Выполняет поиск приоритета по идентификатору. Возвращает Optional
     * с объектом приоритета. Возвращаемый Optional может содержать null,
     * если приоритет не найден.
     *
     * @param id идентификатор приоритета
     * @return Optional.ofNullable() с объектом priority
     */
    public Optional<Priority> findPriorityById(int id) {
        return this.execute(
                session -> {
                    Query<Priority> query = session.createQuery(FIND_PRIORITY_BY_ID, Priority.class);
                    query.setParameter("id", id);
                    return query.uniqueResult();
                });
    }

    /**
     * Выполняет обновление приоритета.
     *
     * @param priority приоритета
     * @return Optional.ofNullable() с обновленным объектом priority
     */
    public Optional<Priority> update(Priority priority) {
        return this.execute(
                session -> {
                    session.update(priority);
                    return priority;
                }
        );
    }

    /**
     * Выполняет добавление приоритета. Возвращает
     * приоритет с проинициализированным идентификатором.
     *
     * @param priority приоритет
     * @return Optional.ofNullable() с сохраненным объектом priority
     */
    public Optional<Priority> add(Priority priority) {
        return this.execute(
                session -> {
                    session.save(priority);
                    return priority;
                }
        );
    }

    /**
     * Выполняет удаление приоритета по идентификатору.
     *
     * @param id идентификатор приоритета
     */
    public void deletePriorityById(int id) {
        this.execute(session -> session.createQuery(
                        DELETE_PRIORITY_BY_ID)
                .setParameter("id", id)
                .executeUpdate());
    }

    /**
     * Выполняет поиск приоритета по имени. Возвращает Optional
     * с объектом приоритета. Возвращаемый Optional может содержать null,
     * если приоритет не найден.
     *
     * @param name имя приоритета
     * @return Optional с объектом priority
     */
    @Override
    public Optional<Priority> findPriorityByName(String name) {
        return this.execute(
                session -> {
                    Query<Priority> query = session.createQuery(FIND_PRIORITY_BY_NAME, Priority.class);
                    query.setParameter("name", name);
                    return query.uniqueResult();
                });
    }
}