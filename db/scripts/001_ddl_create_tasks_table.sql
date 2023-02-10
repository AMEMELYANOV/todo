CREATE TABLE IF NOT EXISTS tasks (
   id SERIAL PRIMARY KEY,
   name varchar NOT NULL,
   description TEXT NOT NULL,
   created TIMESTAMP NOT NULL,
   done BOOLEAN NOT NULL
);

COMMENT ON TABLE tasks IS 'Задачи';
COMMENT ON COLUMN tasks.id IS 'Идентификатор задачи';
COMMENT ON COLUMN tasks.name IS 'Наименование задачи';
COMMENT ON COLUMN tasks.description IS 'Подробное описание задачи';
COMMENT ON COLUMN tasks.done IS 'Статус задачи';