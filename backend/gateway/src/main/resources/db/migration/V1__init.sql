CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login VARCHAR(30) NOT NULL,
    password VARCHAR(256) NOT NULL,
    role VARCHAR(16) NOT NULL
);

INSERT INTO users (login, password, role)
VALUES ('admin', '$2a$10$FUAne5ule7eDGK/SXTBLB.j4yOc0OBtoGs5e1dznIZbk83wuMahL.', 'ADMIN');