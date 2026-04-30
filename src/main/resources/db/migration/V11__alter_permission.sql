ALTER TABLE admin_permission ADD updated_by BIGINT,
    CONSTRAINT FK_admin_permission_updated_by FOREIGN KEY (updated_by) REFERENCES app_user(id);
GO

ALTER TRIGGER trg_app_user_delete
    ON app_user
    AFTER DELETE
    AS
    BEGIN
        SET NOCOUNT ON;

        --remove permission if user with permission is deleted
        DELETE FROM admin_permission
        WHERE app_user_id IN (SELECT id FROM deleted);

        --save permission but set granted_by to null if granted user is deleted
        UPDATE admin_permission
        SET granted_by = NULL
        WHERE granted_by IN (SELECT id FROM deleted);

        -- save permission but set updated_by to null if updated user is deleted
        UPDATE admin_permission
        SET updated_by = NULL
        WHERE updated_by IN (SELECT id FROM deleted);

    END;
GO