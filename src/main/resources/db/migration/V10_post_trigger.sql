ALTER TABLE post_media DROP CONSTRAINT FK_postmedia_post;
ALTER TABLE post_media ADD CONSTRAINT FK_postmedia_post
    FOREIGN KEY (post_id) REFERENCES post(id);

ALTER TABLE post_log DROP CONSTRAINT FK_postlog_post;
ALTER TABLE post_log ADD CONSTRAINT FK_postlog_post
    FOREIGN KEY (post_id) REFERENCES post(id);
GO

CREATE TRIGGER trg_DeletePost
    ON post
    INSTEAD OF DELETE
    AS
BEGIN
    SET NOCOUNT ON;

    --Set post_id to NULL in post_log
    UPDATE post_log
    SET post_id = NULL
    WHERE post_id IN (SELECT id FROM deleted);

    --Delete related post_media
    DELETE FROM post_media
    WHERE post_id IN (SELECT id FROM deleted);

    --Deletes the post
    DELETE FROM post
    WHERE id IN (SELECT id FROM deleted);
END
GO