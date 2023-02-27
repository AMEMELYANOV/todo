CREATE TABLE IF NOT EXISTS  tasks_categories (
   id serial PRIMARY KEY,
   task_id int not null REFERENCES tasks(id),
   category_id int not null REFERENCES categories(id)
);

COMMENT ON TABLE tasks_categories IS 'Таблица связей заданий и категорий';
COMMENT ON COLUMN tasks_categories.id IS 'Идентификатор связи';
COMMENT ON COLUMN tasks_categories.task_id IS 'Ссылка на задание';
COMMENT ON COLUMN tasks_categories.category_id IS 'Ссылка на категорию';