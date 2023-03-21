package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * Сервис по работе с задачами
 * @see ru.job4j.todo.model.Task
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface TaskService {

    /**
     * Возвращает список новых задач.
     *
     * @return список новых задач
     */
    List<Task> findAllTasks();

    /**
     * Возвращает список новых задач.
     *
     * @return список новых задач
     */
    List<Task> findNewTasks();

    /**
     * Возвращает список выполненных задач.
     *
     * @return список выполненных задач
     */
    List<Task> findDoneTasks();

    /**
     * Выполняет поиск задачи по идентификатору. При успешном нахождении возвращает
     * задачу.
     *
     * @param id идентификатор задачи
     * @return задачу при успешном нахождении
     */
    Task findTaskById(int id);

    /**
     * Выполняет обновление задачи.
     *
     * @param task обновляемая задача
     * @return задача при успешном обновлении
     */
    Task update(Task task);

    /**
     * Выполняет сохранение задачи. При успешном сохранении возвращает
     * сохраненную задачу.
     *
     * @param task сохраняемая задача
     * @return задача при успешном сохранении
     */
    Task add(Task task);

    /**
     * Выполняет выбор методов класса для сохранения или обновления задачи.
     *
     * @param task сохраняемая задача
     * @return задача при успешном сохранении или обновлении
     */
    Task addOrUpdateTask(Task task);

    /**
     * Выполняет установку статуса задачи в выполнено.
     *
     * @param id идентификатор задачи
     * @return задача при успешном обновлении статуса
     */
    Task taskDone(int id);

    /**
     * Выполняет удаление задачи по идентификатору задачи.
     *
     * @param id идентификатор задачи
     */
    void deleteTaskById(int id);
}
