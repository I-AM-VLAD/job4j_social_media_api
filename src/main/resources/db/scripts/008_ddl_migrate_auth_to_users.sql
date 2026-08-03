CREATE TABLE users_roles
(
    user_id INT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id INT NOT NULL REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

ALTER TABLE users ALTER COLUMN email SET NOT NULL;
ALTER TABLE users ALTER COLUMN password SET NOT NULL;

DROP TABLE persons_roles;
DROP TABLE persons;
