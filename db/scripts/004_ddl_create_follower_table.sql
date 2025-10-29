CREATE TABLE follower (
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    follower_id INT REFERENCES users(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, follower_id)
);