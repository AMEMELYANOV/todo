package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест класс реализации хранилища заданий
 * @see ru.job4j.todo.repository.TaskRepository
 * @author Alexander Emelyanov
 * @version 1.0
 */
class HibernateTaskRepositoryTest {

    /**
     * Объект репозитория TaskRepository
     */
    TaskRepository taskRepository;

    /**
     * Объект репозитория CategoryRepository
     */
    CategoryRepository categoryRepository;

    /**
     * Объект репозитория PriorityRepository
     */
    PriorityRepository priorityRepository;

    /**
     * Задание
     */
    Task task;

    /**
     * Категория
     */
    Category category;

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
        taskRepository = new HibernateTaskRepository(sf());
        categoryRepository = new HibernateCategoryRepository(sf());
        priorityRepository = new HibernatePriorityRepository(sf());

        category = Category.builder()
                .name("category")
                .build();
        categoryRepository.add(category);

        priority = Priority.builder()
                .name("priority")
                .build();
        priorityRepository.add(priority);

        task = Task.builder()
                .name("task")
                .description("description")
                .categories(List.of(category))
                .priority(priority)
                .done(false)
                .build();
    }

    /**
     * Создаются два объекта task со статусом выполнено и активно и сохраняются
     * в базе данных. Через вызов метода {@link TaskRepository#findAllTasks()}
     * получаем список объектов из базы данных.
     * Выполняем проверку размера списка и содержание элементов
     * на эквивалентность созданным объектам по полю name.
     */
    @Test
    void whenFindAllTasksThenGetTasksFromDB() {
        Task task2  = Task.builder()
                .name("task2")
                .description("description2")
                .categories(List.of(category))
                .priority(priority)
                .done(true)
                .build();
        taskRepository.add(task);
        taskRepository.add(task2);
        List<Task> tasks = taskRepository.findAllTasks();

        assertThat(tasks.size()).isEqualTo(2);
        assertThat(tasks.get(0).getName()).isEqualTo(task.getName());
        assertThat(tasks.get(1).getName()).isEqualTo(task2.getName());
    }

    /**
     * Через вызов метода {@link TaskRepository#findAllTasks()}
     * получаем список объектов tasks из базы данных.
     * Выполняем проверку размера списка на эквивалентность пустому списку.
     */
    @Test
    void whenFindAllTasksThenEmptyList() {
        List<Task> tasks = taskRepository.findAllTasks();

        assertThat(tasks.size()).isEqualTo(0);
    }

    /**
     * Создаются два объекта task со статусом выполнено и активно и сохраняются
     * в базе данных. Через вызов метода {@link TaskRepository#findNewTasks()}
     * получаем список объектов из базы данных.
     * Выполняем проверку размера списка и содержание элементов
     * на эквивалентность созданным объектам по полю name.
     */
    @Test
    void whenFindNewTasksThenGetTasksFromDB() {
        Task task2  = Task.builder()
                .name("task2")
                .description("description2")
                .categories(List.of(category))
                .priority(priority)
                .done(true)
                .build();
        taskRepository.add(task);
        taskRepository.add(task2);
        List<Task> tasks = taskRepository.findNewTasks();

        assertThat(tasks.size()).isEqualTo(1);
        assertThat(tasks.get(0).getName()).isEqualTo(task.getName());
    }

    /**
     * Через вызов метода {@link TaskRepository#findNewTasks()}
     * получаем список объектов tasks из базы данных.
     * Выполняем проверку размера списка на эквивалентность пустому списку.
     */
    @Test
    void whenFindNewTasksThenEmptyList() {
        Task task2  = Task.builder()
                .name("task2")
                .description("description2")
                .categories(List.of(category))
                .priority(priority)
                .done(true)
                .build();
        taskRepository.add(task2);
        List<Task> tasks = taskRepository.findNewTasks();

        assertThat(tasks.size()).isEqualTo(0);
    }

    /**
     * Создаются два объекта task со статусом выполнено и активно и сохраняются
     * в базе данных. Через вызов метода {@link TaskRepository#findDoneTasks()}
     * получаем список объектов из базы данных.
     * Выполняем проверку размера списка и содержание элементов
     * на эквивалентность созданным объектам по полю name.
     */
    @Test
    void whenFindDoneTasksThenGetTasksFromDB() {
        Task task2  = Task.builder()
                .name("task2")
                .description("description2")
                .categories(List.of(category))
                .priority(priority)
                .done(true)
                .build();
        taskRepository.add(task2);
        List<Task> tasks = taskRepository.findDoneTasks();

        assertThat(tasks.size()).isEqualTo(1);
        assertThat(tasks.get(0).getName()).isEqualTo(task2.getName());
    }

    /**
     * Через вызов метода {@link TaskRepository#findDoneTasks()}
     * получаем список объектов tasks из базы данных.
     * Выполняем проверку размера списка на эквивалентность пустому списку.
     */
    @Test
    void whenFindDoneTasksThenEmptyList() {
        taskRepository.add(task);
        List<Task> tasks = taskRepository.findDoneTasks();

        assertThat(tasks.size()).isEqualTo(0);
    }

    /**
     * Создается объект task и сохраняется в базе данных.
     * Через вызов метода {@link TaskRepository#findTaskById(int)}
     * получаем task из базы данных.
     * Выполняем проверку на эквивалентность начальному объекту task
     * по полю name.
     */
    @Test
    void whenFindTaskByIdThenGetTasksFromDB() {
        taskRepository.add(task).get();
        Task taskFromDB = taskRepository.findTaskById(task.getId()).get();

        assertThat(taskFromDB.getName()).isEqualTo(task.getName());
    }

    /**
     * Создается объект task и сохраняется в базе данных.
     * Через вызов метода {@link TaskRepository#findTaskById(int)}
     * по идентификатору, которого нет в базе данных получаем Optional.empty
     */
    @Test
    void whenFindTaskByIdThenGetOptionalEmpty() {
        taskRepository.add(task).get();
        Optional<Task> taskFromDB = taskRepository.findTaskById(category.getId() + 1);

        assertThat(taskFromDB).isEqualTo(Optional.empty());
    }

    /**
     * Создается объект task и сохраняется в базе данных.
     * Выполняется изменение данных с обновлением объекта task в
     * базе данных при помощи метода {@link TaskRepository#update(Task)}.
     * Результат обновления записывается в переменную updatedTask,
     * далее проверяется его эквивалентность объекту task
     * по полю id.
     */
    @Test
    public void whenUpdateTaskThenGetTheSameFromDatabase() {
        taskRepository.add(task);
        task.setName("task2");
        Task updatedTask = taskRepository.update(task).get();

        assertThat(task.getId()).isEqualTo(updatedTask.getId());
    }

    /**
     * Создается объект task и сохраняется в базе данных.
     * По полю id объект task находится в базе данных, сохраняется в объект taskFromDB
     * при помощи метода {@link TaskRepository#findTaskById(int)}
     * и проверяется его эквивалентность объекту task по полю name.
     */
    @Test
    void whenAddTaskThenGetTheSameFromDatabase() {
        taskRepository.add(task);
        Task taskFromDB = taskRepository.add(task).get();

        assertThat(task.getName()).isEqualTo(taskFromDB.getName());
    }

    /**
     * Создается объект task и сохраняется в базе данных.
     * По полю id объект task удаляется из базы данных при помощи метода
     * {@link TaskRepository#deleteTaskById(int)}
     * Вызов метода {@link TaskRepository#deleteTaskById(int)}
     * проверяется на эквивалентность Optional.empty().
     */
    @Test
    public void whenDeleteTaskByIdThenDoNotGetShowFromDatabase() {
        int id = task.getId();
        taskRepository.deleteTaskById(id);

        assertThat(taskRepository.findTaskById(id)).isEqualTo(Optional.empty());
    }
}