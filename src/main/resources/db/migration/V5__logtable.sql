CREATE TABLE auth_log (
                          id           BIGINT PRIMARY KEY IDENTITY(1,1),
                          email        NVARCHAR(255) NOT NULL,
                          ip_address   VARCHAR(45) NOT NULL,
                          success      BIT NOT NULL,
                          fail_reason  VARCHAR(50),
                          created_at   DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET()
);
GO

CREATE TABLE message_log (
    id              BIGINT PRIMARY KEY IDENTITY(1,1),
    app_user_id     BIGINT NOT NULL,
    conversation_id BIGINT NOT NULL,
    ip_address      VARCHAR(45),
    created_at      DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),

                    CONSTRAINT FK_msglog_user FOREIGN KEY (app_user_id) REFERENCES app_user(id),
                    CONSTRAINT FK_msglog_conv FOREIGN KEY (conversation_id) REFERENCES conversation(id)
);
GO