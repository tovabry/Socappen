CREATE TABLE answer
(
    id          bigint IDENTITY (1, 1) NOT NULL,
    question_id bigint         NOT NULL,
    app_user_id bigint         NOT NULL,
    text        varchar(255)   NOT NULL,
    sent_at     datetimeoffset NOT NULL,
    CONSTRAINT pk_answer PRIMARY KEY (id)
)
    GO

CREATE TABLE app_user
(
    id               bigint IDENTITY (1, 1) NOT NULL,
    email            varchar(255)   NOT NULL,
    password_hash    varchar(100)   NOT NULL,
    role             varchar(10)    NOT NULL,
    status           varchar(20)    NOT NULL,
    created_at       datetimeoffset NOT NULL,
    last_activity_at datetimeoffset NOT NULL,
    is_online        bit            NOT NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
)
    GO

CREATE TABLE conversation
(
    id               bigint IDENTITY (1, 1) NOT NULL,
    created_at       datetimeoffset NOT NULL,
    last_activity_at datetimeoffset NOT NULL,
    status           varchar(10)    NOT NULL,
    CONSTRAINT pk_conversation PRIMARY KEY (id)
)
    GO

CREATE TABLE frequently_asked_question
(
    id       bigint IDENTITY (1, 1) NOT NULL,
    question varchar(255) NOT NULL,
    answer   varchar(255) NOT NULL,
    CONSTRAINT pk_frequently_asked_question PRIMARY KEY (id)
)
    GO

CREATE TABLE message
(
    id              bigint IDENTITY (1, 1) NOT NULL,
    conversation_id bigint         NOT NULL,
    sender_id       bigint         NOT NULL,
    content         varchar(255)   NOT NULL,
    sent_at         datetimeoffset NOT NULL,
    CONSTRAINT pk_message PRIMARY KEY (id)
)
    GO

CREATE TABLE question
(
    id       bigint IDENTITY (1, 1) NOT NULL,
    nickname varchar(255)   NOT NULL,
    text     varchar(255)   NOT NULL,
    sent_at  datetimeoffset NOT NULL,
    CONSTRAINT pk_question PRIMARY KEY (id)
)
    GO

CREATE TABLE conversation_participant
(
    joined_at       datetimeoffset NOT NULL,
    conversation_id bigint         NOT NULL,
    app_user_id     bigint         NOT NULL,
    CONSTRAINT pk_conversation_participant PRIMARY KEY (conversation_id, app_user_id)
)
    GO

ALTER TABLE answer
    ADD CONSTRAINT uc_answer_question UNIQUE (question_id)
    GO

ALTER TABLE answer
    ADD CONSTRAINT FK_ANSWER_ON_APP_USER FOREIGN KEY (app_user_id) REFERENCES app_user (id)
    GO

ALTER TABLE answer
    ADD CONSTRAINT FK_ANSWER_ON_QUESTION FOREIGN KEY (question_id) REFERENCES question (id)
    GO

ALTER TABLE message
    ADD CONSTRAINT FK_MESSAGE_ON_CONVERSATION FOREIGN KEY (conversation_id) REFERENCES conversation (id)
    GO

ALTER TABLE message
    ADD CONSTRAINT FK_MESSAGE_ON_SENDER FOREIGN KEY (sender_id) REFERENCES app_user (id)
    GO

DROP TABLE dbo.MSreplication_options
    GO

DROP TABLE dbo.spt_fallback_db
    GO

DROP TABLE dbo.spt_fallback_dev
    GO

DROP TABLE dbo.spt_fallback_usg
    GO

DROP TABLE dbo.spt_monitor
    GO