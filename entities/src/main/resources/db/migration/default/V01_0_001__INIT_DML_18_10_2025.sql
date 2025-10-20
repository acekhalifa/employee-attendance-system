
-- Define the custom type for user roles (ADMIN, EMPLOYEE)
CREATE TYPE user_role AS ENUM ('ADMIN', 'EMPLOYEE');

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,

    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,

    role user_role NOT NULL,

    first_name VARCHAR(255),
    last_name VARCHAR(255)
);

CREATE TABLE attendance (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    marked_at TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT fk_attendance_user
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE RESTRICT,

    CONSTRAINT uq_user_date
        UNIQUE (user_id, attendance_date)
);

