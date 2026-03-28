CREATE TABLE users (
    id CHAR(36) NOT NULL,
    username VARCHAR(32) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(255),
    is_active BIT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT idx_users_email UNIQUE (email),
    CONSTRAINT idx_users_username UNIQUE (username)
);

CREATE TABLE server (
    id BIGINT NOT NULL AUTO_INCREMENT,
    owner_id CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    icon_url VARCHAR(255),
    invite_code VARCHAR(255),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UKavwcu5tdas101fhtpu4lgt7ie UNIQUE (invite_code)
);

CREATE TABLE server_member (
    id BIGINT NOT NULL AUTO_INCREMENT,
    server_id BIGINT NOT NULL,
    user_id CHAR(36) NOT NULL,
    role ENUM('ADMIN','MEMBER','OWNER') NOT NULL,
    joined_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UK59rpy143oyi30ojshbgbg3556 UNIQUE (server_id, user_id),
    CONSTRAINT fk_server_member_server FOREIGN KEY (server_id) REFERENCES server(id)
);

CREATE TABLE channel_category (
    id BIGINT NOT NULL AUTO_INCREMENT,
    server_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_channel_category_server FOREIGN KEY (server_id) REFERENCES server(id) ON DELETE CASCADE
);

CREATE TABLE channel (
    id BIGINT NOT NULL AUTO_INCREMENT,
    server_id BIGINT NOT NULL,
    category_id BIGINT,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_channel_server FOREIGN KEY (server_id) REFERENCES server(id) ON DELETE CASCADE,
    CONSTRAINT fk_channel_category FOREIGN KEY (category_id) REFERENCES channel_category(id) ON DELETE SET NULL
);

CREATE TABLE conversation (
    id BIGINT NOT NULL AUTO_INCREMENT,
    type ENUM('CHANNEL','DM','GROUP') NOT NULL,
    channel_id BIGINT,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_conversation_channel FOREIGN KEY (channel_id) REFERENCES channel(id) ON DELETE CASCADE
);

CREATE TABLE conversation_participant (
    id BIGINT NOT NULL AUTO_INCREMENT,
    conversation_id BIGINT NOT NULL,
    user_id CHAR(36) NOT NULL,
    joined_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_participant_conversation FOREIGN KEY (conversation_id) REFERENCES conversation(id) ON DELETE CASCADE
);

CREATE TABLE friendships (
    id BIGINT NOT NULL AUTO_INCREMENT,
    sender_id CHAR(36) NOT NULL,
    receiver_id CHAR(36) NOT NULL,
    status ENUM('ACCEPTED','DECLINED','PENDING','REMOVED') NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE refresh_tokens (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id CHAR(36) NOT NULL,
    token VARCHAR(36) NOT NULL,
    revoked BIT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    expires_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UKghpmfn23vmxfu3spu3lfg4r2d UNIQUE (token)
);

CREATE INDEX idx_conversation_channel_id ON conversation (channel_id);
CREATE INDEX idx_participant_conversation_id ON conversation_participant (conversation_id);
CREATE INDEX idx_participant_user_id ON conversation_participant (user_id);
CREATE INDEX idx_friendship_sender_receiver ON friendships (sender_id, receiver_id);
CREATE INDEX idx_friendship_sender_status ON friendships (sender_id, status);
CREATE INDEX idx_friendship_receiver_status ON friendships (receiver_id, status);
CREATE INDEX idx_refresh_token_token ON refresh_tokens (token);
CREATE INDEX idx_refresh_token_user_id ON refresh_tokens (user_id);