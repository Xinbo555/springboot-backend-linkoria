ALTER TABLE channel_category
ADD CONSTRAINT fk_channel_category_server
FOREIGN KEY (server_id) REFERENCES server(id)
ON DELETE CASCADE;

ALTER TABLE channel
ADD CONSTRAINT fk_channel_server
FOREIGN KEY (server_id) REFERENCES server(id)
ON DELETE CASCADE;

ALTER TABLE channel
ADD CONSTRAINT fk_channel_category
FOREIGN KEY (category_id) REFERENCES channel_category(id)
ON DELETE SET NULL;