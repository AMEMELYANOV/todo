package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import ru.job4j.todo.model.Priority;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест класс реализации хранилища приоритетов
 * @see ru.job4j.todo.repository.PriorityRepository
 * @author Alexander Emelyanov
 * @version 1.0
 */
class HibernatePriorityRepositoryTest {

    /**
     * Объект репозитория PriorityRepository
     */
    PriorityRepository priorityRepository;

    /**
     * Приоритет
     */
    Priority priority;

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
        priorityRepository = new HibernatePriorityRepository(sf());
        priority = Priority.builder()
                .name("priority")
                .position(1)
                .build();
        priorityRepository.add(priority);
    }

    /**
     * Создается объект priority и сохраняется в базе данных.
     * Через вызов метода {@link PriorityRepository#findPriorityById(int)}
     * получаем priority из базы данных.
     * Выполняем проверку на эквивалентность начальному объекту priority
     * по полю name.
     */
    @Test
    void whenFindPriorityByIdThenGetPriorityFromDB() {
        Priority priorityFromDB = priorityRepository.findPriorityById(priority.getId()).get();

        assertThat(priorityFromDB.getName()).isEqualTo(priority.getName());
    }

    /**
     * Создается объект priority и сохраняется в базе данных.
     * Через вызов метода {@link PriorityRepository#findPriorityById(int)}
     * по идентификатору, которого нет в базе данных, получаем Optional.empty
     */
    @Test
    void whenFindPriorityByIdThenGetOptionalEmpty() {
        Optional<Priority> priorityFromDB = priorityRepository.findPriorityById(priority.getId() + 1);

        assertThat(priorityFromDB).isEqualTo(Optional.empty());
    }

    /**
     * Создается объект priority и сохраняется в базе данных.
     * Выполняется изменение данных с обновлением объекта priority в
     * базе данных при помощи метода {@link PriorityRepository#update(Priority)}.
     * Результат обновления записывается в переменную updatedPriority,
     * далее проверяется его эквивалентность объекту priority
     * по полю id.
     */
    @Test
    public void whenUpdatePriorityThenGetTheSameFromDatabase() {
        priority.setName("priority2");
        Priority updatedPriority = priorityRepository.update(priority).get();

        assertThat(priority.getId()).isEqualTo(updatedPriority.getId());
    }


    /**
     * Создается объект priority и сохраняется в базе данных.
     * По полю id объект priority находится в базе данных, сохраняется в объект priorityFromDB
     * при помощи метода {@link PriorityRepository#findPriorityById(int)}
     * и проверяется его эквивалентность объекту priority по полю name.
     */
    @Test
    void whenAddPriorityThenGetTheSameFromDatabase() {
        Priority priorityFromDB = priorityRepository.findPriorityById(priority.getId()).get();

        assertThat(priority.getName()).isEqualTo(priorityFromDB.getName());
    }

     /**
     * Создается объект priority и сохраняется в базе данных.
     * По полю id объект priority удаляется из базы данных при помощи метода
     * {@link PriorityRepository#deletePriorityById(int)}
     * Вызов метода {@link PriorityRepository#findPriorityById(int)}
     * проверяется на эквивалентность Optional.empty().
     */
    @Test
    public void whenDeletePriorityByIdThenDoNotGetShowFromDatabase() {
        int id = priority.getId();
        priorityRepository.deletePriorityById(id);

        assertThat(priorityRepository.findPriorityById(id)).isEqualTo(Optional.empty());
    }

    /**
     * Создается объект priority и сохраняется в базе данных.
     * Через вызов метода {@link PriorityRepository#findPriorityByName(String)}
     * получаем priority из базы данных.
     * Выполняем проверку на эквивалентность начальному объекту priority
     * по полю id.
     */
    @Test
    void whenFindPriorityByNameThenGetPriorityFromDB() {
        Priority priorityFromDB = priorityRepository.findPriorityByName(priority.getName()).get();

        assertThat(priorityFromDB.getName()).isEqualTo(priority.getName());
    }

    /**
     * Создается объект priority и сохраняется в базе данных.
     * Через вызов метода {@link PriorityRepository#findPriorityByName(String)}
     * по имени, которого нет в базе данных получаем Optional.empty
     */
    @Test
    void whenFindPriorityByNameThenGetOptionalEmpty() {
        Optional<Priority> priorityFromDB = priorityRepository.findPriorityByName(priority.getName() + 1);

        assertThat(priorityFromDB).isEqualTo(Optional.empty());
    }
}