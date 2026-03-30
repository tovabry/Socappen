CREATE TABLE permission (
                            id   INT PRIMARY KEY IDENTITY(1,1),
                            name VARCHAR(50) NOT NULL UNIQUE
);
GO

-- Seed initial permissions
INSERT INTO permission (name) VALUES
                                  ('manage_users'),
                                  ('view_logs'),
                                  ('manage_content'),
                                  ('manage_faq');
GO

CREATE TABLE admin_permission (
                                  id          INT PRIMARY KEY IDENTITY(1,1),
                                  app_user_id BIGINT NOT NULL,
                                  permission_id INT NOT NULL,
                                  granted_at  DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
                                  granted_by  BIGINT NOT NULL,

                                  CONSTRAINT UQ_user_permission UNIQUE (app_user_id, permission_id),
                                  CONSTRAINT FK_permission_user FOREIGN KEY (app_user_id)
                                      REFERENCES app_user(id) ON DELETE CASCADE,
                                  CONSTRAINT FK_permission_granter FOREIGN KEY (granted_by)
                                      REFERENCES app_user(id),
                                  CONSTRAINT FK_admin_permission FOREIGN KEY (permission_id)
                                      REFERENCES permission(id)
);