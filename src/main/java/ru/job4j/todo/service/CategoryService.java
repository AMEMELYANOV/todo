package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;

import java.util.List;

/**
 * Сервис по работе с категориями
 * @see ru.job4j.todo.model.Category
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface CategoryService {

    /**
     * Возвращает список всех категорий
     *
     * @return список категорий
     */
    List<Category> findAllCategories();

    /**
     * Выполняет возврат всех категорий с фильтром по идентификатору.
     *
     * @param categoryIds список идентификаторов категорий
     * @return список категорий
     */
    List<Category> findCategoriesByIds(List<Integer> categoryIds);
}
