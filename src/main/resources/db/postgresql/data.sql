-- Insert into roles
INSERT INTO roles (rol_id, rol_name) VALUES 
(1, 'ADMIN'), 
(2, 'USER');

-- Insert into users
INSERT INTO users (username, password, email, roles_rol_id) VALUES 
('admin', '$2a$10$ijkPeb2bVjTYbmcOnataRuqrm79Q64brGZGGYQxgeG49HdwLjouJO', 'admin@localhost.com', 1),
('Joaquin', '$2a$10$BBw8vlIoLbPSpPHXGVh4puwr/kcaDlDU5eH54bVFPomEs0jHeN9cK', 'joaquin.borrego@vedruna.es', 2);

-- Insert into films
INSERT INTO films (title, release_date) VALUES 
('Star Wars Episode I', '1999-05-19'),
('Star Wars Episode II', '2002-05-16'),
('Star Wars Episode III', '2005-05-19'),
('Star Wars Episode IV', '1977-05-25'),
('Star Wars Episode V', '1980-05-21'),
('Star Wars Episode VI', '1983-05-25');

-- Insert into dnis
INSERT INTO dnis (number, front_img, back_img, users_user_id) VALUES 
('12345678Z', './front.jpg', './back.jpg', 1);

-- Insert into users_haswatched_films
INSERT INTO users_haswatched_films (users_user_id, films_film_id) VALUES 
(2, 4);
