package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.NoSuchElementException;

/**
 * Реализация сервиса по работе с задачами
 * @see ru.job4j.todo.service.PriorityService
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@Service
public class ImplPriorityService implements PriorityService {

    /**
     * Объект для доступа к методам PriorityRepository
     */
    private final PriorityRepository priorityRepository;

    /**
     * Выполняет поиск приоритета по имени. При успешном нахождении возвращает
     * приоритет, иначе выбрасывает исключение.
     *
     * @param name имя приоритета
     * @return приоритет при успешном нахождении
     * @exception NoSuchElementException если задача не найдена
     */
    @Override
    public Priority findPriorityByName(String name) {
        return priorityRepository.findPriorityByName(name).orElseThrow(
                () -> new NoSuchElementException(
                        String.format("Приоритет c name = %s не найден", name)));
    }
}
