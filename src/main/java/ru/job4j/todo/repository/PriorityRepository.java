package ru.job4j.todo.repository;

import ru.job4j.todo.model.Priority;

import java.util.Optional;

/**
 * Хранилище приоритетов
 * @see ru.job4j.todo.model.Priority
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface PriorityRepository {

    /**
     * Выполняет поиск приоритета по имени. Возвращает Optional
     * с объектом приоритета. Возвращаемый Optional может содержать null,
     * если приоритет не найден.
     *
     * @param name имя приоритета
     * @return Optional.ofNullable() с объектом priority
     */
    Optional<Priority> findPriorityByName(String name);
}
