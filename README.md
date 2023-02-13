# job4j_todo

# **Проект - Список задач**

<pd id="start"></p>
<ul>
<li><a href="#Описание проекта">Описание проекта</a></li>
<li><a href="#Стек технологий">Стек технологий</a></li>
<li><a href="#Требования к окружению">Требования к окружению</a></li>
<li><a href="#Сборка и запуск проекта">Сборка и запуск проекта</a></li>
<li><a href="#Взаимодействие с приложением">Взаимодействие с приложением</a></li>
<li><a href="#Контакты">Контакты</a></li>
</ul>

## <p id="Описание проекта">Описание проекта</p>

* Добавление дел, проставление отметок о выполнении, редактирование.
* Возможность отображения всех дел, новых или только невыполненных.

<p><a href="#Описание проекта">К оглавлению</a></p>

## <p id="Стек технологий">Стек технологий</p>

- Java 17
- Spring Boot 2.7
- Thymeleaf
- Bootstrap
- Hibernate 5.6
- PostgreSql 14

<p><a href="#Описание проекта">К оглавлению</a></p>

## <p id="Требования к окружению">Требования к окружению</p>

<p><a href="#Описание проекта">К оглавлению</a></p>

## <p id="Сборка и запуск проекта">Сборка и запуск проекта</p>

### <p id="1. Сборка проекта">1. Сборка проекта</p>

Команда для сборки в jar:
`mvn clean package -DskipTests`

### <p id="2. Запуск проекта">2. Запуск проекта</p>

Перед запуском проекта необходимо создать базу данных todo
в PostgreSQL, команда для создания базы данных:
`create database todo;`
Средство миграции Liquibase автоматически создаст структуру
базы данных и наполнит ее предустановленными данными.
Команда для запуска приложения:
`mvn spring-boot:run`

<p><a href="#Описание проекта">К оглавлению</a></p>

## <p id="Взаимодействие с приложением">Взаимодействие с приложением</p>

Локальный доступ к приложению осуществляется через любой современный браузер
по адресу `http://localhost:8080/tasks`

### 1. Страница списка задач

На странице списка задач отображаются все задачи. При клике по названию задачи
происходит переход на страницу с подробной информацией о задаче.

![alt text](img/todo_1_1.png)

При переходе по ссылке 'Новые', в списке задач отображаются невыполненные задачи.

![alt text](img/todo_1_2.png)

При переходе по ссылке 'Выполнено', в списке задач отображаются выполненные задачи.

![alt text](img/todo_1_3.png)

### 2. Страница создания задачи

На странице необходимо задать наименование задачи, подробное описание и статус
(по умолчанию задача активна).

![alt text](img/todo_2.png)

### 3. Страница с подробной информацией о задаче

На странице отображается подробное описание задачи, есть возможность перевести
задачу в выполненные (кнопка 'Выполнено'), перейти к редактированию задачи
(кнопка 'Отредактировать') и удалить задачу (кнопка 'Удалить').

![alt text](img/todo_3.png)

### 4. Страница редактирования задачи

Страница аналогична странице по созданию задачи. Редактируются поля задачи:
наименование, подробное описание и статус.

![alt text](img/todo_4.png)

<p><a href="#Описание проекта">К оглавлению</a></p>

## <p id="Контакты">Контакты</p>

[![alt-text](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/T_AlexME)
&nbsp;&nbsp;
[![alt-text](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:amemelyanov@yandex.ru)
&nbsp;&nbsp;
<p><a href="#Описание проекта">К оглавлению</a></p>