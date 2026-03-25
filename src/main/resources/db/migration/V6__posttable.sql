CREATE TABLE post (
                      id           BIGINT PRIMARY KEY IDENTITY(1,1),
                      app_user_id  BIGINT NOT NULL,
                      title        NVARCHAR(200) NOT NULL,
                      content      NVARCHAR(MAX) NOT NULL,
                      created_at   DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
                      updated_at   DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),

                      CONSTRAINT FK_post_user FOREIGN KEY (app_user_id) REFERENCES app_user(id)
);
GO

CREATE TABLE post_media (
                      id         BIGINT PRIMARY KEY IDENTITY(1,1),
                      post_id     BIGINT NOT NULL,
                      media_type VARCHAR(10) NOT NULL CHECK (media_type IN ('image', 'video')),
                      url        NVARCHAR(500) NOT NULL,
                      sort_order INT NOT NULL DEFAULT 0,

                      CONSTRAINT FK_postmedia_post FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
);
GO