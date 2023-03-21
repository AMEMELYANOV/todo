package ru.job4j.todo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

/**
 * Тест класс реализации сервисов
 * @see ru.job4j.todo.service.CategoryService
 * @author Alexander Emelyanov
 * @version 1.0
 */
class ImplCategoryServiceTest {

    /**
     Моск объекта репозитория
     */
    private CategoryRepository categoryRepository;

    /**
     * Объект для доступа к методам CategoryService
     */
    private CategoryService categoryService;

    /**
     * Категория
     */
    Category category;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    public void setup() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryService = new ImplCategoryService(categoryRepository);
        category = Category.builder()
                .id(1)
                .name("category")
                .build();
    }

    /**
     * Выполняется проверка возвращения списка категорий,
     * если в списке есть элементы.
     */
    @Test
    void whenFindAllCategoryThenReturnList() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        doReturn(categories).when(categoryRepository).findAllCategories();
        List<Category> categoryList = categoryService.findAllCategories();

        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(1);
    }

    /**
     * Выполняется проверка возвращения списка категорий,
     * если список пустой.
     */
    @Test
    void whenFindAllCategoriesThenReturnEmptyList() {
        doReturn(Collections.emptyList()).when(categoryRepository).findAllCategories();
        List<Category> categoryList = categoryService.findAllCategories();

        assertThat(categoryList).isEmpty();
    }

    /**
     * Выполняется проверка возвращения списка категорий,
     * если в списке есть элементы, т.е. если категории найдены
     * по идентификатору.
     */
    @Test
    void whenFindByIdsThenReturnCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        List<Integer> ids = List.of(category.getId());
        doReturn(categories).when(categoryRepository).findCategoriesByIds(ids);
        List<Category> categoryList = categoryService.findCategoriesByIds(ids);

        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(1);
    }

    /**
     * Выполняется проверка возвращения списка категорий,
     * если в списке нет элементов, т.е. если категории не найдены
     * по идентификатору, вернется пустой список.
     */
    @Test
    void whenFindByIdsThenThrowsException() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        List<Integer> ids = List.of(category.getId());
        doReturn(categories).when(categoryRepository).findCategoriesByIds(ids);
        List<Category> categoryList = categoryService.findCategoriesByIds(ids);

        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(1);
    }
}