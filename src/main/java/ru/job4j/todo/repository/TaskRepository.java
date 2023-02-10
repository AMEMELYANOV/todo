package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище задач
 * @see ru.job4j.todo.model.Task
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface TaskRepository {

    /**
     * Возвращает список всех задач
     *
     * @return список всех сеансов
     */
    List<Task> findAllTasks();

    /**
     * Возвращает список всех новых задач
     *
     * @return список всех сеансов
     */
    List<Task> findNewTasks();

    /**
     * Возвращает список всех выполненных задач
     *
     * @return список всех сеансов
     */
    List<Task> findDoneTasks();

    /**
     * Выполняет поиск задачи по идентификатору. Возвращает Optional
     * с объектом задачи. Возвращаемый Optional может содержать null,
     * если задача не найдена
     *
     * @param id идентификатор задачи
     * @return Optional.ofNullable() с объектом task
     */
    Optional<Task> findTaskById(int id);

    /**
     * Выполняет обновление задачи.
     *
     * @param task задача
     * @return Optional.ofNullable() с обновленным объектом task
     */
    Optional<Task> update(Task task);

    /**
     * Выполняет добавление задачи. Возвращает
     * задачу с проинициализированным id.
     *
     * @param task задача
     * @return Optional.ofNullable() с сохраненным объектом task
     */
    Optional<Task> add(Task task);

    /**
     * Выполняет удаление задачи.
     *
     * @param id идентификатор задачи
     */
    void deleteTaskById(int id);
}
