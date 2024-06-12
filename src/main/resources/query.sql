CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(99),
    password VARCHAR(99),
    role VARCHAR(99)
);

CREATE TABLE categories(
    id SERIAL PRIMARY KEY,
    name VARCHAR(99),
    date TIMESTAMP,
    user_id INTEGER REFERENCES users(id)
);

CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    name VARCHAR(99),
    description VARCHAR(99),
    date TIMESTAMP,
    status VARCHAR(99),
    user_id INTEGER REFERENCES users(id),
    category_id INTEGER REFERENCES categories(id)
);
