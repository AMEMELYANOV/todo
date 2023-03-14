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
     * Выполняет поиск приоритета по идентификатору. Возвращает Optional
     * с объектом приоритета. Возвращаемый Optional может содержать null,
     * если приоритет не найден.
     *
     * @param id идентификатор приоритета
     * @return Optional.ofNullable() с объектом priority
     */
    Optional<Priority> findPriorityById(int id);

    /**
     * Выполняет обновление приоритета.
     *
     * @param priority приоритета
     * @return Optional.ofNullable() с обновленным объектом priority
     */
    Optional<Priority> update(Priority priority);

    /**
     * Выполняет добавление приоритета. Возвращает
     * приоритет с проинициализированным идентификатором.
     *
     * @param priority приоритет
     * @return Optional.ofNullable() с сохраненным объектом priority
     */
    Optional<Priority> add(Priority priority);

    /**
     * Выполняет удаление приоритета по идентификатору.
     *
     * @param id идентификатор приоритета
     */
    void deletePriorityById(int id);

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
