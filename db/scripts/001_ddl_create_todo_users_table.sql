CREATE TABLE IF NOT EXISTS todo_users (
   id SERIAL PRIMARY KEY,
   name varchar NOT NULL,
   login varchar NOT NULL UNIQUE,
   password varchar NOT NULL,
   user_zone varchar
);

COMMENT ON TABLE todo_users IS 'Пользователи';
COMMENT ON COLUMN todo_users.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN todo_users.name IS 'Имя пользователя';
COMMENT ON COLUMN todo_users.login IS 'Логин пользователя';
COMMENT ON COLUMN todo_users.password IS 'Пароль пользователя';
COMMENT ON COLUMN todo_users.user_zone IS 'Временная зона пользователя';