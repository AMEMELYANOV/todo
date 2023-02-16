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
 * @see PriorityRepository
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