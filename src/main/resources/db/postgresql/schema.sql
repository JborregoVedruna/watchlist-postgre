-- -- Drop schema if it exists
-- DROP SCHEMA IF EXISTS watchlist CASCADE;

-- -- Create schema
-- CREATE SCHEMA watchlist;

-- -- Use the schema
-- SET search_path TO watchlist;

-- Table roles
DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
  rol_id SERIAL PRIMARY KEY,
  rol_name VARCHAR(45) NOT NULL UNIQUE
);

-- Table users
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  user_id SERIAL PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE,
  password CHAR(60) NOT NULL,
  email VARCHAR(90) NOT NULL UNIQUE,
  roles_rol_id INT NOT NULL,
  FOREIGN KEY (roles_rol_id) REFERENCES roles (rol_id)
);

-- Table dnis
DROP TABLE IF EXISTS dnis;

CREATE TABLE dnis (
  dni_id SERIAL,
  number VARCHAR(9) NOT NULL UNIQUE,
  front_img VARCHAR(45),
  back_img VARCHAR(45),
  users_user_id INT NOT NULL,
  PRIMARY KEY (dni_id, users_user_id),
  FOREIGN KEY (users_user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

-- Table films
DROP TABLE IF EXISTS films;

CREATE TABLE films (
  film_id SERIAL PRIMARY KEY,
  title VARCHAR(45) NOT NULL UNIQUE,
  release_date DATE NOT NULL
);

-- Table users_haswatched_films
DROP TABLE IF EXISTS users_haswatched_films;

CREATE TABLE users_haswatched_films (
  users_user_id INT NOT NULL,
  films_film_id INT NOT NULL,
  PRIMARY KEY (users_user_id, films_film_id),
  FOREIGN KEY (films_film_id) REFERENCES films (film_id),
  FOREIGN KEY (users_user_id) REFERENCES users (user_id)
);
