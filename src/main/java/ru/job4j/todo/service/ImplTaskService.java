package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.PriorityRepository;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Реализация сервиса по работе с задачами
 * @see ru.job4j.todo.service.TaskService
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@Service
public class ImplTaskService implements TaskService {

    /**
     * Объект для доступа к методам TaskRepository
     */
    private final TaskRepository taskRepository;

    /**
     * Объект для доступа к методам PriorityRepository
     */
    private final PriorityService priorityRepository;

    /**
     * Возвращает список всех задач.
     *
     * @return список всех задач
     */
    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAllTasks();
    }

    /**
     * Возвращает список новых задач.
     *
     * @return список новых задач
     */
    @Override
    public List<Task> findNewTasks() {
        return taskRepository.findNewTasks();
    }

    /**
     * Возвращает список выполненных задач.
     *
     * @return список выполненных задач
     */
    @Override
    public List<Task> findDoneTasks() {
        return taskRepository.findDoneTasks();
    }

    /**
     * Выполняет поиск задачи по идентификатору. При успешном нахождении возвращает
     * задачу, иначе выбрасывает исключение.
     *
     * @param id идентификатор задачи
     * @return задачу при успешном нахождении
     * @exception NoSuchElementException если задача не найдена
     */
    @Override
    public Task findTaskById(int id) {
        return taskRepository.findTaskById(id).orElseThrow(
                () -> new NoSuchElementException(
                        String.format("Задание c id = %d не найдено", id)));
    }

    /**
     * Выполняет обновление задачи.
     *
     * @param task обновляемая задача
     * @exception NoSuchElementException если задача не найдена
     */
    @Override
    public Task update(Task task) {
        Priority priorityFromDB = priorityRepository.findPriorityByName(task.getPriority().getName());
        task.setPriority(priorityFromDB);
        return taskRepository.update(task).orElseThrow(
                () -> new IllegalArgumentException(
                        String.format("Ошибка в обновлении задачи с id = %d", task.getId())));
    }

    /**
     * Выполняет сохранение задачи. При успешном сохранении возвращает
     * сохраненную задачу, иначе выбрасывается исключение.
     *
     * @param task сохраняемая задача
     * @return задача при успешном сохранении
     * @exception IllegalArgumentException если сохранение задачи не произошло
     */
    @Override
    public Task add(Task task) {
        Priority priorityFromDB = priorityRepository.findPriorityByName(task.getPriority().getName());
        task.setPriority(priorityFromDB);
        return taskRepository.add(task).orElseThrow(
                () -> new IllegalArgumentException(
                        String.format("Ошибка в сохранении задачи с наименованием - %s", task.getName())));
    }

    /**
     * Выполняет выбор методов класса для сохранения или обновления задачи.
     *
     * @param task сохраняемая задача
     * @return задача при успешном сохранении или обновлении
     */
    @Override
    public Task addOrUpdateTask(Task task) {
        Task taskFromDB;
        if (task.getId() == 0) {
            taskFromDB = add(task);
        } else {
            taskFromDB = update(task);
        }
        return taskFromDB;
    }

    /**
     * Выполняет установку статуса задачи в выполнено.
     *
     * @param id идентификатор задачи
     * @return задача при успешном обновлении статуса
     */
    @Override
    public Task taskDone(int id) {
        Task taskFromDB = findTaskById(id);
        taskFromDB.setDone(true);
        return update(taskFromDB);
    }

    /**
     * Выполняет удаление задачи по идентификатору задачи.
     *
     * @param id идентификатор задачи
     */
    @Override
    public void deleteTaskById(int id) {
        taskRepository.deleteTaskById(id);
    }
}
