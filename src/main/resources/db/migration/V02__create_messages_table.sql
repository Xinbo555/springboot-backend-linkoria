CREATE TABLE messages (
    id BIGINT NOT NULL AUTO_INCREMENT,
    conversation_id BIGINT NOT NULL,
    user_id CHAR(36) NOT NULL,
    content TEXT NOT NULL,
    message_type ENUM('TEXT', 'IMAGE', 'FILE', 'VIDEO', 'AUDIO') NOT NULL DEFAULT 'TEXT',
    reply_to_message_id BIGINT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_messages_conversation FOREIGN KEY (conversation_id) REFERENCES conversation(id) ON DELETE CASCADE,
    CONSTRAINT fk_messages_reply_to FOREIGN KEY (reply_to_message_id) REFERENCES messages(id) ON DELETE SET NULL
);

CREATE INDEX idx_conversation_id ON messages (conversation_id);
CREATE INDEX idx_user_id ON messages (user_id);
CREATE INDEX idx_conversation_created ON messages (conversation_id, created_at);
CREATE INDEX idx_reply_to_message_id ON messages (reply_to_message_id);