package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * Сервис по работе с задачами
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see Task
 */
public interface CategoryService {

    /**
     * Возвращает список новых задач
     *
     * @return список новых задач
     */
    List<Category> findAllCategories();

    List<Category> findCategoriesByIds(List<Integer> categoryIds);
}
