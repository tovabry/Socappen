CREATE TABLE app_user
(
    -- 1,1 = seed,increment
    id               BIGINT PRIMARY KEY IDENTITY (1,1),

    -- Case-insensitive email
    email            NVARCHAR(255) COLLATE Latin1_General_CI_AS NOT NULL UNIQUE,
    role             VARCHAR(10) NOT NULL CHECK (role IN ('user', 'admin', 'sysadmin')),
    password_hash    NVARCHAR(100) NOT NULL,
    status           VARCHAR(20) NOT NULL CHECK (status IN ('active', 'inactive', 'suspended', 'deleted')),
    created_at       DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    last_activity_at DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    is_online        BIT NOT NULL            DEFAULT 0
);
GO

CREATE TABLE conversation
(
    id               BIGINT PRIMARY KEY IDENTITY (1,1),
    created_at       DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    last_activity_at DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    status           VARCHAR(10) NOT NULL CHECK (status IN ('active', 'closed'))
);
GO

CREATE TABLE conversation_participant
(
    conversation_id BIGINT NOT NULL,
    app_user_id     BIGINT NOT NULL,
    joined_at       DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    CONSTRAINT PK_conversation_participants PRIMARY KEY (conversation_id, app_user_id),
    CONSTRAINT FK_cp_conversation FOREIGN KEY (conversation_id) REFERENCES conversation (id) ON DELETE CASCADE,
    CONSTRAINT FK_cp_user FOREIGN KEY (app_user_id) REFERENCES app_user (id) ON DELETE CASCADE
);
GO

CREATE TABLE message
(
    id               BIGINT PRIMARY KEY IDENTITY (1,1),

    conversation_id  BIGINT NOT NULL,
    sender_id        BIGINT NOT NULL,
    content          NVARCHAR(1000) NOT NULL,
    sent_at          DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),

    CONSTRAINT FK_message_conversation FOREIGN KEY (conversation_id) REFERENCES conversation (id) ON DELETE CASCADE,

    CONSTRAINT FK_message_sender FOREIGN KEY (sender_id) REFERENCES app_user (id)
);
GO

CREATE TABLE question
(
    id       BIGINT PRIMARY KEY IDENTITY (1,1),
    nickname NVARCHAR(20) NOT NULL,
    text     NVARCHAR(1000) NOT NULL,
    sent_at  DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    status   NVARCHAR(10) NOT NULL CHECK (status IN ('pending', 'approved')) default 'pending'
);
GO

CREATE TABLE answer
(
    id          BIGINT PRIMARY KEY IDENTITY (1,1),
    question_id BIGINT NOT NULL UNIQUE,
    app_user_id BIGINT NOT NULL,
    text        NVARCHAR(MAX) NOT NULL,
    sent_at     DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),

    CONSTRAINT FK_answer_question FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE,
    CONSTRAINT FK_answer_app_user FOREIGN KEY (app_user_id) REFERENCES app_user (id) ON DELETE CASCADE
);
GO

CREATE TABLE frequently_asked_question
(
    id         BIGINT PRIMARY KEY IDENTITY (1,1),
    app_user_id BIGINT NOT NULL,
    question   NVARCHAR(MAX) NOT NULL,
    answer     NVARCHAR(MAX) NOT NULL
)
    GO

-- =========================
-- Trigger to update conversation.last_activity_at
-- =========================

CREATE TRIGGER trigger_update_last_activity
    ON message
    AFTER INSERT AS
BEGIN
SET NOCOUNT ON;

UPDATE c
SET last_activity_at = SYSDATETIMEOFFSET()
FROM conversation c
         INNER JOIN inserted i ON c.id = i.conversation_id;
END;
GO