-- DROP TABLE  IF EXISTS  books

CREATE TABLE books
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    author VARCHAR(256) NOT NULL,
    title  VARCHAR(250) NOT NULL,
    SIZE   INT DEFAULT NULL
);