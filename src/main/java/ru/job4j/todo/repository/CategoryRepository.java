package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище категорий
 * @see ru.job4j.todo.model.Category
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface CategoryRepository {

    /**
     * Выполняет возврат всех категорий из базы данных.
     *
     * @return список категорий
     */
    List<Category> findAllCategories();

    /**
     * Выполняет возврат всех категорий из базы данных с фильтром по идентификатору.
     *
     * @param categoryIds список идентификаторов категорий
     * @return список категорий
     */
    List<Category> findCategoriesByIds(List<Integer> categoryIds);

    /**
     * Выполняет поиск категории по идентификатору. Возвращает Optional
     * с объектом категории. Возвращаемый Optional может содержать null,
     * если категория не найдена.
     *
     * @param id идентификатор категории
     * @return Optional.ofNullable() с объектом category
     */
    Optional<Category> findCategoryById(int id);

    /**
     * Выполняет обновление категории.
     *
     * @param category категории
     * @return Optional.ofNullable() с обновленным объектом category
     */
    Optional<Category> update(Category category);

    /**
     * Выполняет добавление категории. Возвращает
     * категорию с проинициализированным идентификатором.
     *
     * @param category категория
     * @return Optional.ofNullable() с сохраненным объектом category
     */
    Optional<Category> add(Category category);

    /**
     * Выполняет удаление категории по идентификатору.
     *
     * @param id идентификатор категории
     */
    void deleteCategoryById(int id);
}
