package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Реализация хранилища категорий
 * @see ru.job4j.todo.repository.CategoryRepository
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Repository
@AllArgsConstructor
@Slf4j
public class HibernateCategoryRepository implements CategoryRepository {

    /**
     * SQL запрос по выбору всех категорий из таблицы categories
     */
    private final static String FIND_ALL_CATEGORIES = "from Category c order by c.id";

    /**
     * SQL запрос по выбору всех категорий из таблицы categories по списку идентификаторов
     */
    private final static String FIND_ALL_CATEGORIES_BY_IDS = "from Category c where c.id in :ids order by c.id";

    /**
     * SQL запрос по удалению категории из таблицы categories с фильтром по id
     */
    private final static String DELETE_CATEGORY_BY_ID = "delete Category where id = :id";

    /**
     * SQL запрос по выбору категории из таблицы categories с фильтром по id
     */
    private final static String FIND_CATEGORY_BY_ID = "select distinct c from Category c where c.id = :id";

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
            log.info("Исключение при работе с методами CategoryRepository", e);
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Выполняет возврат всех категорий из базы данных.
     *
     * @return список категорий
     */
    @Override
    public List<Category> findAllCategories() {
        return this.execute(
                session -> session.createQuery(FIND_ALL_CATEGORIES).list());
    }

    /**
     * Выполняет возврат всех категорий из базы данных с фильтром по идентификатору.
     *
     * @param categoryIds список идентификаторов категорий
     * @return список категорий
     */
    @Override
    public List<Category> findCategoriesByIds(List<Integer> categoryIds) {
        return this.execute(
                session -> {
                    Query<Category> query = session.createQuery(FIND_ALL_CATEGORIES_BY_IDS, Category.class);
                    query.setParameter("ids", categoryIds);
                    return query.list();
                });
    }

    /**
     * Выполняет поиск категории по идентификатору. Возвращает Optional
     * с объектом категории. Возвращаемый Optional может содержать null,
     * если категория не найдена.
     *
     * @param id идентификатор категории
     * @return Optional.ofNullable() с объектом category
     */
    @Override
    public Optional<Category> findCategoryById(int id) {
        return this.execute(
                session -> {
                    Query<Category> query = session.createQuery(FIND_CATEGORY_BY_ID, Category.class);
                    query.setParameter("id", id);
                    return Optional.ofNullable(query.uniqueResult());
                });
    }

    /**
     * Выполняет обновление категории.
     *
     * @param category категории
     * @return Optional.ofNullable() с обновленным объектом category
     */
    @Override
    public Optional<Category> update(Category category) {
        return this.execute(
                session -> {
                    session.update(category);
                    return Optional.ofNullable(category);
                }
        );
    }

    /**
     * Выполняет добавление категории. Возвращает
     * категорию с проинициализированным идентификатором.
     *
     * @param category категория
     * @return Optional.ofNullable() с сохраненным объектом category
     */
    @Override
    public Optional<Category> add(Category category) {
        return this.execute(
                session -> {
                    session.save(category);
                    return Optional.ofNullable(category);
                }
        );
    }

    /**
     * Выполняет удаление категории по идентификатору.
     *
     * @param id идентификатор категории
     */
    @Override
    public void deleteCategoryById(int id) {
        this.execute(session -> session.createQuery(
                        DELETE_CATEGORY_BY_ID)
                .setParameter("id", id)
                .executeUpdate());
    }
}