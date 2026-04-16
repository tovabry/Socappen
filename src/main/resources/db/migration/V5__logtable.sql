CREATE TABLE auth_log (
                          id           BIGINT PRIMARY KEY IDENTITY(1,1),
                          app_user_id      BIGINT,
                          ip_address   VARCHAR(45) NOT NULL,
                          success      BIT NOT NULL,
                          fail_reason  VARCHAR(50),
                          logged_in_at  DATETIMEOFFSET,
                          logged_out_at DATETIMEOFFSET,
                          created_at   DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET()

                    CONSTRAINT FK_authlog_user_id FOREIGN KEY (app_user_id) REFERENCES app_user(id)
);
GO

CREATE TABLE message_log (
    id              BIGINT PRIMARY KEY IDENTITY(1,1),
    app_user_id     BIGINT NULL,
    conversation_id BIGINT NULL,
    ip_address      VARCHAR(45),
    created_at      DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),

                    CONSTRAINT FK_msglog_user FOREIGN KEY (app_user_id) REFERENCES app_user(id),
                    CONSTRAINT FK_msglog_conv FOREIGN KEY (conversation_id) REFERENCES conversation(id)
);
GO

CREATE TABLE audit_log (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    app_user_id BIGINT NULL,
    ip_address VARCHAR(45),
    reason VARCHAR(MAX),
    created_at DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET()
)
GO

CREATE TABLE post_log (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    app_user_id BIGINT NULL,
    post_id BIGINT NULL,
    ip_address VARCHAR(45),
    created_at DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET()

        CONSTRAINT FK_postlog_user FOREIGN KEY (app_user_id) REFERENCES app_user(id),
        CONSTRAINT FK_postlog_post FOREIGN KEY (post_id) REFERENCES post(id)
)
GO

CREATE TABLE faq_log (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    app_user_id BIGINT NULL,
    faq_id BIGINT NULL,
    ip_address VARCHAR(45),
    created_at DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET()

        CONSTRAINT FK_faqlog_user FOREIGN KEY (app_user_id) REFERENCES app_user(id),
        CONSTRAINT FK_faglog_faq FOREIGN KEY (faq_id) REFERENCES frequently_asked_question(id)
)
GO