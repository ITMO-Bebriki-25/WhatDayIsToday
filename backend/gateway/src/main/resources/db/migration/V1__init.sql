CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login VARCHAR(30) NOT NULL,
    password VARCHAR(256) NOT NULL,
    role VARCHAR(16) NOT NULL
);

INSERT INTO users (login, password, role)
VALUES ('admin', '$2a$10$XJ2df5AlNNuPP45ApNA2oOubEoxS.d3ElW4zhjfVSikk4U46zczOu', 'ADMIN');

CREATE INDEX idx_users_login ON users(login);