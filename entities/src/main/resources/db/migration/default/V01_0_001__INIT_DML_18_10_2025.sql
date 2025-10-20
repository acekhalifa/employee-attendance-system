
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE attendance (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    marked_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_attendance_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT unique_user_date
        UNIQUE (user_id, attendance_date)
);

INSERT INTO users (email, password, token, role, first_name, last_name)
VALUES (
    'admin@encentral.com',
    'admin',
    'default-admin-token-' || gen_random_uuid()::text,
    'ADMIN',
    'System',
    'Admin'
)
ON CONFLICT (email) DO NOTHING;
