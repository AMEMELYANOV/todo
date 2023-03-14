CREATE TABLE IF NOT EXISTS  categories (
   id SERIAL PRIMARY KEY,
   name varchar UNIQUE NOT NULL
);

COMMENT ON TABLE categories IS 'Категории';
COMMENT ON COLUMN categories.id IS 'Идентификатор категории';
COMMENT ON COLUMN categories.name IS 'Наименование категории';