package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "priorities")
public class Priority {

    /**
     * Идентификатор приоритета
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование приоритета
     */
    private String name;

    /**
     * Позиция приоритета
     */
    private int position;
}