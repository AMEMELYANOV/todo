package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;

import java.util.List;

/**
 * Хранилище категорий
 * @see Category
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

    List<Category> findCategoriesByIds(List<Integer> categoryIds);
}
