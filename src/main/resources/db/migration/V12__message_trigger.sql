-- Remove cascades that block INSTEAD OF trigger
ALTER TABLE app_user
    DROP CONSTRAINT FK_app_user_role;
GO

ALTER TABLE app_user
    ADD CONSTRAINT FK_app_user_role FOREIGN KEY (role_id)
        REFERENCES role(id) ON DELETE NO ACTION;
GO

ALTER TABLE conversation_participant
    DROP CONSTRAINT FK_cp_user;
GO

ALTER TABLE conversation_participant
    ADD CONSTRAINT FK_cp_user FOREIGN KEY (app_user_id)
        REFERENCES app_user(id) ON DELETE NO ACTION;
GO

ALTER TABLE answer
    DROP CONSTRAINT FK_answer_app_user;
GO

ALTER TABLE answer
    ADD CONSTRAINT FK_answer_app_user FOREIGN KEY (app_user_id)
        REFERENCES app_user(id) ON DELETE NO ACTION;
GO

ALTER TABLE admin_permission
    DROP CONSTRAINT FK_permission_user;
GO

ALTER TABLE admin_permission
    ADD CONSTRAINT FK_permission_user FOREIGN KEY (app_user_id)
        REFERENCES app_user(id) ON DELETE NO ACTION;
GO

-- Allow sender_id to be null when user is deleted
ALTER TABLE message
    ALTER COLUMN sender_id BIGINT NULL;
GO

DROP TRIGGER IF EXISTS trg_app_user_delete;
GO

CREATE TRIGGER trg_app_user_delete
    ON app_user
    INSTEAD OF DELETE
    AS
BEGIN
    SET NOCOUNT ON;

    DELETE FROM admin_permission
    WHERE app_user_id IN (SELECT id FROM deleted);

    DELETE FROM conversation_participant
    WHERE app_user_id IN (SELECT id FROM deleted);

    DELETE FROM answer
    WHERE app_user_id IN (SELECT id FROM deleted);

    UPDATE admin_permission
    SET granted_by = NULL
    WHERE granted_by IN (SELECT id FROM deleted);

    UPDATE admin_permission
    SET updated_by = NULL
    WHERE updated_by IN (SELECT id FROM deleted);

    UPDATE message
    SET sender_id = NULL
    WHERE sender_id IN (SELECT id FROM deleted);

    DELETE FROM app_user
    WHERE id IN (SELECT id FROM deleted);
END;
GO