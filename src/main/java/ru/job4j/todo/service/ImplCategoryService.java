package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.List;

/**
 * Реализация сервиса по работе с задачами
 * @see ru.job4j.todo.service.CategoryService
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@Service
public class ImplCategoryService implements CategoryService {

    /**
     * Объект для доступа к методам CategoryRepository
     */
    private final CategoryRepository categoryRepository;

    /**
     * Возвращает список всех категорий
     *
     * @return список категорий
     */
    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAllCategories();
    }

    /**
     * Выполняет возврат всех категорий с фильтром по идентификатору.
     *
     * @param categoryIds список идентификаторов категорий
     * @return список категорий
     */
    @Override
    public List<Category> findCategoriesByIds(List<Integer> categoryIds) {
        return categoryRepository.findCategoriesByIds(categoryIds);
    }
}
