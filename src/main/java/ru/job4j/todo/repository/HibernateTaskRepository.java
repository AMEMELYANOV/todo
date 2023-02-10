package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Реализация хранилища задач
 * @see ru.job4j.todo.repository.TaskRepository
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Repository
@AllArgsConstructor
@Slf4j
public class HibernateTaskRepository implements TaskRepository {

    /**
     * SQL запрос по выбору всех задач из таблицы tasks
     */
    private final static String FIND_ALL_TASKS = "from Task t order by t.id";

    /**
     * SQL запрос по выбору новых задач из таблицы tasks
     */
    private final static String FIND_NEW_TASKS = "from Task t where t.done = false order by t.id ";

    /**
     * SQL запрос по выбору выполненных задач из таблицы tasks
     */
    private final static String FIND_DONE_TASKS = "from Task t where t.done = true order by t.id ";

    /**
     * SQL запрос по выбору задачи из таблицы tasks с фильтром по id
     */
    private final static String FIND_TASK_BY_ID = "from Task t where t.id = :id";

    /**
     * SQL запрос по удалению сеансов из таблицы tasks с фильтром по id
     */
    private final static String DELETE_TASK_BY_ID = "delete Task where id = :id";

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
    private <T> T execute(final Function<Session, T> command) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            log.info("Исключение при работе с методами TaskRepository", e);
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Возвращает список всех задач
     *
     * @return список всех сеансов
     */
    @Override
    public List<Task> findAllTasks() {
        return this.execute(
                session -> session.createQuery(FIND_ALL_TASKS).list());
    }

    /**
     * Возвращает список всех новых задач
     *
     * @return список всех сеансов
     */
    @Override
    public List<Task> findNewTasks() {
        return this.execute(
                session -> session.createQuery(FIND_NEW_TASKS).list());
    }

    /**
     * Возвращает список всех выполненных задач
     *
     * @return список всех сеансов
     */
    @Override
    public List<Task> findDoneTasks() {
        return this.execute(
                session -> session.createQuery(FIND_DONE_TASKS).list());
    }

    /**
     * Выполняет поиск задачи по идентификатору. Возвращает Optional
     * с объектом задачи. Возвращаемый Optional может содержать null,
     * если задача не найдена
     *
     * @param id идентификатор задачи
     * @return Optional.ofNullable() с объектом task
     */
    @Override
    public Optional<Task> findTaskById(int id) {
        return this.execute(
                session -> {
                    Query<Task> query = session.createQuery(FIND_TASK_BY_ID, Task.class);
                    query.setParameter("id", id);
                    return Optional.ofNullable(query.uniqueResult());
                });
    }

    /**
     * Выполняет обновление задачи.
     *
     * @param task задача
     * @return Optional.ofNullable() с обновленным объектом task
     */
    @Override
    public Optional<Task> update(Task task) {
        return this.execute(
                session -> {
                    session.update(task);
                    return Optional.ofNullable(task);
                }
        );
    }

    /**
     * Выполняет добавление задачи. Возвращает
     * задачу с проинициализированным id.
     *
     * @param task задача
     * @return Optional.ofNullable() с сохраненным объектом task
     */
    @Override
    public Optional<Task> add(Task task) {
        return this.execute(
                session -> {
                    session.save(task);
                    return Optional.ofNullable(task);
                }
        );
    }

    /**
     * Выполняет удаление задачи.
     *
     * @param id идентификатор задачи
     */
    @Override
    public void deleteTaskById(int id) {
        this.execute(session -> session.createQuery(
                        DELETE_TASK_BY_ID)
                .setParameter("id", id)
                .executeUpdate());
    }
}