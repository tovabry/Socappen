CREATE TABLE permission (
                            id   BIGINT PRIMARY KEY IDENTITY(1,1),
                            name VARCHAR(50) NOT NULL UNIQUE
);
GO

-- Seed initial permissions
INSERT INTO permission (name) VALUES
                                  ('manage_user'),
                                  ('view_logs'),
                                  ('manage_faq'),
                                  ('manage_post'),
                                  ('manage_contact'),
                                  ('manage_permission');
GO

CREATE TABLE admin_permission (
                                  id          BIGINT PRIMARY KEY IDENTITY(1,1),
                                  app_user_id BIGINT NOT NULL,
                                  permission_id BIGINT NOT NULL,
                                  granted_at  DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
                                  updated_at  DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
                                  granted_by  BIGINT,

                                  CONSTRAINT UQ_user_permission UNIQUE (app_user_id, permission_id),
                                  CONSTRAINT FK_permission_user FOREIGN KEY (app_user_id)
                                      REFERENCES app_user(id) ON DELETE CASCADE,
                                  CONSTRAINT FK_permission_granter FOREIGN KEY (granted_by)
                                      REFERENCES app_user(id) ON DELETE NO ACTION,
                                  CONSTRAINT FK_admin_permission FOREIGN KEY (permission_id)
                                      REFERENCES permission(id)
);
GO

CREATE TRIGGER trg_app_user_delete
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
END;
GO

--DROP TRIGGER IF EXISTS trg_app_user_delete;
--DROP TABLE IF EXISTS admin_permission;
--DROP TABLE IF EXISTS permission;

--insert into admin_permission (app_user_id, permission_id) values (20008, 1);
--update app_user set role_id = 2 where id = 20008;