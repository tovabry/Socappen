ALTER TABLE message
    ALTER COLUMN sender_id BIGINT NULL;
GO

ALTER TRIGGER trg_app_user_delete
    ON app_user
    AFTER DELETE
    AS
    BEGIN
        SET NOCOUNT ON;

        DELETE FROM admin_permission
        WHERE app_user_id IN (SELECT id FROM deleted);

        UPDATE admin_permission
        SET granted_by = NULL
        WHERE granted_by IN (SELECT id FROM deleted);

        UPDATE admin_permission
        SET updated_by = NULL
        WHERE updated_by IN (SELECT id FROM deleted);

        -- add this
        UPDATE message
        SET sender_id = NULL
        WHERE sender_id IN (SELECT id FROM deleted);
    END;