package ru.job4j.todo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

class ImplPriorityServiceTest {

    /**
     Моск объекта репозитория
     */
    private PriorityRepository priorityRepository;

    /**
     * Объект для внедрения моков
     */
    private PriorityService priorityService;

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
        priorityRepository = Mockito.mock(PriorityRepository.class);
        priorityService = new ImplPriorityService(priorityRepository);
        priority = Priority.builder()
                .id(1)
                .name("priority")
                .build();
    }

    /**
     * Выполняется проверка возвращения приоритета, при возврате
     * от priorityRepository Optional.of(priority), т.е. если приоритет найден по имени.
     */
    @Test
    void whenFindPriorityByNameThenReturnPriority() {
        doReturn(Optional.of(priority)).when(priorityRepository).findPriorityByName(priority.getName());
        Priority priorityFromDB = priorityService.findPriorityByName(priority.getName());

        assertThat(priorityFromDB).isEqualTo(priority);
        assertThat(priorityFromDB).isNotNull();
    }

    /**
     * Выполняется проверка выброса исключения, при возврате от
     * priorityRepository Optional.empty(), если приоритет не найден по имени.
     */
    @Test
    void whenFindPriorityByNameThenThrowsException() {
        doReturn(Optional.empty()).when(priorityRepository).findPriorityByName(anyString());

        assertThrows(NoSuchElementException.class, () -> priorityService.findPriorityByName(anyString()));
    }
}