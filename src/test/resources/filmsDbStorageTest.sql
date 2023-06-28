INSERT INTO mpa (id, name)
VALUES (1,'G'),
       (2, 'PG'),
       (3, 'PG-13'),
       (4, 'R'),
       (5, 'NC-17');

INSERT INTO genres (id, name)
VALUES (1,'Комедия'),
       (2,'Драма'),
       (3,'Мультфильм'),
       (4,'Триллер'),
       (5,'Документальный'),
       (6,'Боевик');

INSERT INTO films (name, description, releaseDate, duration, mpa)
VALUES ('Большой куш', 'Отличный фильм, нравится детям', '2000-09-01', '104', '1'),
('Карты, деньги, два ствола', 'Дети тоже очень сильно любям', '1998-01-01', '106', '1');

INSERT INTO users (email, login, name, birthday)
VALUES ('ivanivanov@mail.ru', 'iivan', 'Ivan', '2000-01-01');
