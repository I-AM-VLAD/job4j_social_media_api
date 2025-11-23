ALTER TABLE friend
ADD CONSTRAINT friend_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE friend
ADD CONSTRAINT friend_friend_id_fkey FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE;
