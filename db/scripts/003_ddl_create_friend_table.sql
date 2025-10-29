CREATE TABLE friend (
    user_id   INT REFERENCES users(id),
    friend_id INT REFERENCES users(id),
    status VARCHAR(20) NOT NULL DEFAULT 'pending', -- pending/accepted/rejected
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, friend_id),
    CHECK (user_id <> friend_id)
);