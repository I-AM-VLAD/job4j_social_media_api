ALTER TABLE message
ADD CONSTRAINT message_sender_id_fkey FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE message
ADD CONSTRAINT message_receiver_id_fkey FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE;