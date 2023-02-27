package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.List;

/**
 * Реализация сервиса по работе с задачами
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see TaskService
 */
@AllArgsConstructor
@Service
public class ImplCategoryService implements CategoryService {

    /**
     * Объект для доступа к методам TaskRepository
     */
    private final CategoryRepository categoryRepository;

    /**
     * Возвращает список всех задач
     *
     * @return список всех задач
     */
    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAllCategories();
    }

    @Override
    public List<Category> findCategoriesByIds(List<Integer> categoryIds) {
        return categoryRepository.findCategoriesByIds(categoryIds);
    }
}
