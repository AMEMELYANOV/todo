CREATE TABLE IF NOT EXISTS  priorities (
   id SERIAL PRIMARY KEY,
   name varchar UNIQUE NOT NULL,
   position int
);

COMMENT ON TABLE priorities IS 'Приоритеты';
COMMENT ON COLUMN priorities.id IS 'Идентификатор приоритета';
COMMENT ON COLUMN priorities.name IS 'Наименование приоритета';
COMMENT ON COLUMN priorities.position IS 'Позиция приоритета';