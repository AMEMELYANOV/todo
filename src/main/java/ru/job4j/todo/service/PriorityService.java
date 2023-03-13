package ru.job4j.todo.service;

import ru.job4j.todo.model.Priority;

/**
 * Сервис по работе с приоритетами
 * @see ru.job4j.todo.model.Priority
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface PriorityService {

    /**
     * Выполняет поиск приоритета по имени. При успешном нахождении возвращает
     * приоритет.
     *
     * @param name имя приоритета
     * @return приоритет при успешном нахождении
     */
    Priority findPriorityByName(String name);
}
