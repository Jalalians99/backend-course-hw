-- Drop tables if they exist
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS users;

-- Create category table
CREATE TABLE category (
    categoryid BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create book table
CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INT,
    isbn VARCHAR(20),
    price DOUBLE PRECISION,
    categoryid BIGINT,
    FOREIGN KEY (categoryid) REFERENCES category(categoryid)
);

-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
); 