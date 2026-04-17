CREATE TABLE post (
                      id          BIGINT PRIMARY KEY IDENTITY(1,1),
                      app_user_id BIGINT NOT NULL,
                      title       NVARCHAR(200) NOT NULL,
                      content     NVARCHAR(MAX) NOT NULL,
                      created_at  DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
                      updated_at  DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET()
);
GO

CREATE INDEX IX_post_app_user_id ON post(app_user_id);
GO

CREATE TABLE post_media (
                            id         BIGINT PRIMARY KEY IDENTITY(1,1),
                            post_id    BIGINT NOT NULL,
                            media_type VARCHAR(10) NOT NULL CHECK (media_type IN ('image', 'video')),
                            url        NVARCHAR(500) NOT NULL,
                            sort_order INT NOT NULL DEFAULT 0,

                            CONSTRAINT FK_postmedia_post FOREIGN KEY (post_id)
                                REFERENCES post(id) ON DELETE CASCADE
);
GO

CREATE INDEX IX_postmedia_post_id ON post_media(post_id);
GO

CREATE TRIGGER trg_UpdatePost
    ON post
    AFTER UPDATE
    AS
BEGIN
    SET NOCOUNT ON;
    UPDATE post
    SET updated_at = SYSDATETIMEOFFSET()
    FROM post p
             INNER JOIN inserted i ON p.id = i.id
END
GO

INSERT INTO post (app_user_id, title, content)
VALUES
    (1, 'Post 1', N'Det här är ny spännande information från resursenheten.'),
    (1, 'Post 2', N'Mer info från resursenheten');
GO