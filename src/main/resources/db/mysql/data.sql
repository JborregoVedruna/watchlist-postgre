-- SQLINES FOR EVALUATION USE ONLY (14 DAYS)
INSERT INTO watchlist.roles VALUES (1, "ADMIN"), (2, "USER");

INSERT INTO watchlist.users VALUES (0, "admin", "$2a$10$ijkPeb2bVjTYbmcOnataRuqrm79Q64brGZGGYQxgeG49HdwLjouJO", "admin@localhost.com", 1);
INSERT INTO watchlist.users VALUES (0, "Joaquin", "$2a$10$BBw8vlIoLbPSpPHXGVh4puwr/kcaDlDU5eH54bVFPomEs0jHeN9cK", "joaquin.borrego@vedruna.es", 2);

INSERT INTO watchlist.films VALUES (0, "Star Wars Episode I", "1999-05-19");
INSERT INTO watchlist.films VALUES (0, "Star Wars Episode II", "2002-05-16");
INSERT INTO watchlist.films VALUES (0, "Star Wars Episode III", "2005-05-19");
INSERT INTO watchlist.films VALUES (0, "Star Wars Episode IV", "1977-05-25");
INSERT INTO watchlist.films VALUES (0, "Star Wars Episode V", "1980-05-21");
INSERT INTO watchlist.films VALUES (0, "Star Wars Episode VI", "1983-05-25");

INSERT INTO watchlist.dnis VALUES (0, "12345678Z", "./front.jpg", "./back.jpg", 1);

INSERT INTO watchlist.users_haswatched_films VALUES(2, 4);