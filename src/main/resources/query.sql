CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(300),
    password VARCHAR(300),
    role VARCHAR(300)

);

CREATE TABLE categories(
    id SERIAL PRIMARY KEY,
    name VARCHAR(99),
    date TIMESTAMP,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    name VARCHAR(99),
    description VARCHAR(99),
    date TIMESTAMP,
    status VARCHAR(99),
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    category_id INTEGER REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE
);

