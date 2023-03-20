package ru.job4j.todo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class ImplTaskServiceTest {

    /**
     Моск объекта TaskRepository
     */
    private TaskRepository taskRepository;

    /**
     Моск объекта PriorityService
     */
    private PriorityService priorityService;

    /**
     * Объект для внедрения моков
     */
    private TaskService taskService;

    /**
     * Задание
     */
    Task task;

    /**
     * Приоритет
     */
    Priority priority;


    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    public void setup() {
        taskRepository = Mockito.mock(TaskRepository.class);
        priorityService = Mockito.mock(PriorityService.class);
        taskService = new ImplTaskService(taskRepository, priorityService);

        priority = Priority.builder()
                .id(1)
                .name("priority")
                .position(1)
                .build();
        task = Task.builder()
                .id(1)
                .name("task")
                .priority(priority)
                .build();
    }

    /**
     * Выполняется проверка возвращения списка всех заданий,
     * если в списке есть элементы.
     */
    @Test
    void whenFindAllTasksThenReturnList() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        doReturn(tasks).when(taskRepository).findAllTasks();
        List<Task> taskList = taskService.findAllTasks();

        assertThat(taskList).isNotNull();
        assertThat(taskList.size()).isEqualTo(1);
    }

    /**
     * Выполняется проверка возвращения списка всех заданий,
     * если список пустой.
     */
    @Test
    void whenFindAllTasksThenReturnEmptyList() {
        doReturn(Collections.emptyList()).when(taskRepository).findAllTasks();
        List<Task> taskList = taskService.findAllTasks();

        assertThat(taskList).isEmpty();
    }

    /**
     * Выполняется проверка возвращения списка новых заданий,
     * если в списке есть элементы.
     */
    @Test
    void whenFindNewTasksThenReturnList() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        doReturn(tasks).when(taskRepository).findNewTasks();
        List<Task> taskList = taskService.findNewTasks();

        assertThat(taskList).isNotNull();
        assertThat(taskList.size()).isEqualTo(1);
    }

    /**
     * Выполняется проверка возвращения списка новых заданий,
     * если список пустой.
     */
    @Test
    void whenFindNewTasksThenReturnEmptyList() {
        doReturn(Collections.emptyList()).when(taskRepository).findNewTasks();
        List<Task> taskList = taskService.findNewTasks();

        assertThat(taskList).isEmpty();
    }

    /**
     * Выполняется проверка возвращения списка выполненных заданий,
     * если в списке есть элементы.
     */
    @Test
    void whenFindDoneTasksThenReturnList() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        doReturn(tasks).when(taskRepository).findDoneTasks();
        List<Task> taskList = taskService.findDoneTasks();

        assertThat(taskList).isNotNull();
        assertThat(taskList.size()).isEqualTo(1);
    }

    /**
     * Выполняется проверка возвращения списка выполненных заданий,
     * если список пустой.
     */
    @Test
    void whenFindDoneTasksThenReturnEmptyList() {
        doReturn(Collections.emptyList()).when(taskRepository).findDoneTasks();
        List<Task> taskList = taskService.findDoneTasks();

        assertThat(taskList).isEmpty();
    }

    /**
     * Выполняется проверка возвращения задачи, при возврате
     * от taskRepository Optional.of(task), т.е. если задача найдена по id.
     */
    @Test
    void whenFindTaskByIdThenReturnTask() {
        doReturn(Optional.of(task)).when(taskRepository).findTaskById(task.getId());
        Task taskFromDB = taskService.findTaskById(task.getId());

        assertThat(taskFromDB).isEqualTo(task);
    }

    /**
     * Выполняется проверка выброса исключения, при возврате от
     * taskRepository Optional.empty(), если задача не найдена по id.
     */
    @Test
    void whenFindTaskByIdThenThrowsException() {
        doReturn(Optional.empty()).when(taskRepository).findTaskById(task.getId());

        assertThrows(NoSuchElementException.class,
                () -> taskService.findTaskById(task.getId()));
    }

    /**
     * Выполняется проверка обновления задачи, при возврате от
     * taskRepository, т.е. если задача была обновлена.
     */
    @Test
    void whenUpdateTaskThenReturnTask() {
        doReturn(Optional.of(task)).when(taskRepository).update(task);
        Task updatedTask = taskService.update(task);

        assertThat(updatedTask).isEqualTo(task);
    }

    /**
     * Выполняется проверка выброса исключения, при возврате от
     * taskRepository Optional.empty(), если задача не была обновлена.
     */
    @Test
    void whenUpdateTaskThenThrowsException() {
        doReturn(Optional.empty()).when(taskRepository).update(task);

        assertThrows(IllegalArgumentException.class, () -> taskService.update(task));
    }

    /**
     * Выполняется проверка возврата задачи, при возврате от
     * taskRepository Optional.of(task), т.е. если задача была сохранена.
     */
    @Test
    void whenAddTaskThenReturnTask() {
        doReturn(Optional.of(task)).when(taskRepository).add(task);
        Task taskFromDB = taskService.add(task);
        assertThat(taskFromDB).isEqualTo(task);
        assertThat(taskFromDB).isNotNull();
    }

    /**
     * Выполняется проверка выброса исключения, при возврате от
     * taskRepository Optional.empty(), если задача не была сохранена.
     */
    @Test
    void whenAddTaskThenThrowsException() {
        doReturn(Optional.empty()).when(taskRepository).add(task);

        assertThrows(IllegalArgumentException.class, () -> taskService.add(task));
    }

    /**
     * Выполняется проверка изменения статуса задания.
     */
    @Test
    void whenTaskDoneThenReturnTask() {
        doReturn(Optional.of(task)).when(taskRepository).findTaskById(anyInt());
        doReturn(Optional.of(task)).when(taskRepository).update(task);
        Task updatedTask = taskService.taskDone(anyInt());

        assertThat(updatedTask.isDone()).isEqualTo(true);
    }

    /**
     * Выполняется проверка выбора обновления или сохранения задачи
     * в зависимости от значения идентификатора при нулевом идентификаторе.
     */
    @Test
    void whenAddOrUpdateThenAdd() {
        task.setId(0);
        doReturn(Optional.of(task)).when(taskRepository).add(task);
        taskService.addOrUpdateTask(task);

        verify(taskRepository).add(task);
    }

    /**
     * Выполняется проверка выбора обновления или сохранения задачи
     * в зависимости от значения идентификатора при не нулевом идентификаторе.
     */
    @Test
    void whenAddOrUpdateThenUpdate() {
        doReturn(Optional.of(task)).when(taskRepository).update(task);
        taskService.addOrUpdateTask(task);

        verify(taskRepository).update(task);
    }
}