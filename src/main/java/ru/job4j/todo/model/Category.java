package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;

/**
 * Модель данных категория
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
@Entity
@Table(name = "categories")
public class Category {

    /**
     * Идентификатор категории
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование категории
     */
    private String name;
}