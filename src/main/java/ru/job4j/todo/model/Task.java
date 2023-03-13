package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Модель данных задачи
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tasks")
public class Task {
    /**
     * Идентификатор задачи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование задачи
     */
    private String name;

    /**
     * Подробное описание задачи
     */
    private String description;

    /**
     * Время создания задач
     */
    private LocalDateTime created = LocalDateTime.now();

    /**
     * Статус задачи
     */
    private boolean done;

    /**
     * Пользователь задачи
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Приоритет задачи
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    /**
     * Список категорий задачи
     */
    @ManyToMany
    @JoinTable(
            name = "tasks_categories",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private List<Category> categories;
}
