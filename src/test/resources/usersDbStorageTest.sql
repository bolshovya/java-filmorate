INSERT INTO users (email, login, name, birthday)
VALUES ('ivanivanov@mail.ru', 'iivan', 'Ivan', '2000-01-01'),
('sergey@ya.ru', 'serg', 'Sergey', '1990-02-02');

INSERT INTO friendships (user_id, friend_id)
VALUES (1, 2);