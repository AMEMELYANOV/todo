package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест класс реализации хранилища приоритетов
 * @see ru.job4j.todo.repository.CategoryRepository
 * @author Alexander Emelyanov
 * @version 1.0
 */
class HibernateCategoryRepositoryTest {

    /**
     * Объект репозитория CategoryRepository
     */
    CategoryRepository categoryRepository;

    /**
     * Категория
     */
    Category category;

    /**
     * Создание объекта (bean), используемого для
     * подключения к базе данных приложения,
     * параметры считываются из файла /resources/hibernate.cfg.xml
     *
     * @return объект (фабрика сессий)
     */
    @Bean(destroyMethod = "close")
    SessionFactory sf() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        categoryRepository = new HibernateCategoryRepository(sf());
        category = Category.builder()
                .name("category")
                .build();
    }

    /**
     * Создается объект category и сохраняется в базе данных.
     * Через вызов метода {@link CategoryRepository#findAllCategories()}
     * получаем список объектов categories из базы данных.
     * Выполняем проверку размера списка и содержание элементов
     * на эквивалентность объекту category по полю name.
     */
    @Test
    void whenFindAllCategoryThenGetCategoriesFromDB() {
        categoryRepository.add(category);
        List<Category> categories = categoryRepository.findAllCategories();

        assertThat(categories.size()).isEqualTo(1);
        assertThat(categories.get(0).getName()).isEqualTo(category.getName());
    }

    /**
     * Через вызов метода {@link CategoryRepository#findAllCategories()}
     * получаем список объектов categories из базы данных.
     * Выполняем проверку размера списка на эквивалентность пустому списку.
     */
    @Test
    void whenFindAllThenEmptyList() {
        List<Category> categories = categoryRepository.findAllCategories();

        assertThat(categories.size()).isEqualTo(0);
    }

    /**
     * Создается объект category и сохраняется в базе данных.
     * Через вызов метода {@link CategoryRepository#findCategoriesByIds(List)}
     * получаем список объектов categories из базы данных.
     * Выполняем проверку размера списка и содержание элементов
     * на эквивалентность объекту category по полю name.
     */
    @Test
    void whenFindCategoryByIdsThenGetCategoriesFromDB() {
        categoryRepository.add(category).get();
        List<Category> categories = categoryRepository
                .findCategoriesByIds(List.of(category.getId()));

        assertThat(categories.size()).isEqualTo(1);
        assertThat(categories.get(0).getName()).isEqualTo(category.getName());
    }

    /**
     * Создается объект category и сохраняется в базе данных.
     * Через вызов метода {@link CategoryRepository#findCategoriesByIds(List)}
     * получаем список объектов categories из базы данных по списку идентификаторов
     * категорий, которых нет в базе данных.
     * Выполняем проверку размера списка и содержание элементов
     * на эквивалентность объекту category по полю name.
     */
    @Test
    void whenFindCategoryByIdsThenGetEmptyList() {
        categoryRepository.add(category).get();
        List<Category> categories = categoryRepository
                .findCategoriesByIds(List.of(category.getId() + 1));

        assertThat(categories.size()).isEqualTo(0);
    }

    /**
     * Создается объект category и сохраняется в базе данных.
     * Через вызов метода {@link CategoryRepository#findCategoryById(int)}
     * получаем category из базы данных.
     * Выполняем проверку на эквивалентность начальному объекту category
     * по полю name.
     */
    @Test
    void whenFindCategoryByIdThenGetCategoryFromDB() {
        categoryRepository.add(category).get();
        Category categoryFromDB = categoryRepository.findCategoryById(category.getId()).get();

        assertThat(categoryFromDB.getName()).isEqualTo(category.getName());
    }

    /**
     * Создается объект category и сохраняется в базе данных.
     * Через вызов метода {@link CategoryRepository#findCategoryById(int)}
     * по идентификатору, которого нет в базе данных получаем Optional.empty
     */
    @Test
    void whenFindCategoryByIdThenGetOptionalEmpty() {
        categoryRepository.add(category).get();
        Optional<Category> categoryFromDB = categoryRepository.findCategoryById(category.getId() + 1);

        assertThat(categoryFromDB).isEqualTo(Optional.empty());
    }

    /**
     * Создается объект category и сохраняется в базе данных.
     * Выполняется изменение данных с обновлением объекта category в
     * базе данных при помощи метода {@link CategoryRepository#update(Category)}.
     * Результат обновления записывается в переменную updatedCategory,
     * далее проверяется его эквивалентность объекту category
     * по полю id.
     */
    @Test
    public void whenUpdateCategoryThenGetTheSameFromDatabase() {
        categoryRepository.add(category);
        category.setName("category2");
        Category updatedCategory = categoryRepository.update(category).get();

        assertThat(category.getId()).isEqualTo(updatedCategory.getId());
    }


    /**
     * Создается объект category и сохраняется в базе данных.
     * По полю id объект category находится в базе данных, сохраняется в объект categoryFromDB
     * при помощи метода {@link CategoryRepository#findCategoryById(int)}
     * и проверяется его эквивалентность объекту category по полю name.
     */
    @Test
    void whenAddCategoryThenGetTheSameFromDatabase() {
        categoryRepository.add(category);
        Category categoryFromDB = categoryRepository.add(category).get();

        assertThat(category.getName()).isEqualTo(categoryFromDB.getName());
    }

     /**
     * Создается объект category и сохраняется в базе данных.
     * По полю id объект category удаляется из базы данных при помощи метода
     * {@link CategoryRepository#deleteCategoryById(int)}
     * Вызов метода {@link CategoryRepository#deleteCategoryById(int)}
     * проверяется на эквивалентность Optional.empty().
     */
    @Test
    public void whenDeleteCategoryByIdThenDoNotGetShowFromDatabase() {
        int id = category.getId();
        categoryRepository.deleteCategoryById(id);

        assertThat(categoryRepository.findCategoryById(id)).isEqualTo(Optional.empty());
    }
}