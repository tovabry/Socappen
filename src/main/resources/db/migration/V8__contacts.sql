CREATE TABLE contact (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    app_user_id BIGINT NOT NULL,
    title VARCHAR(255),
    img_url VARCHAR(255),
    mail VARCHAR(255),
    phone VARCHAR(255),
    created_at DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    updated_at DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET()
    );
GO

CREATE TRIGGER trg_UpdateContact
    ON contact
    AFTER UPDATE
    AS
BEGIN
    SET NOCOUNT ON;
    UPDATE c
    SET updated_at = SYSDATETIMEOFFSET()
    FROM contact c
             INNER JOIN inserted i ON c.id = i.id
END
GO

INSERT INTO contact (app_user_id, title, img_url, mail, phone)
VALUES (1, 'Ungdomsmottagningen', 'https://vasternorrland.rfsl.se/wp-content/uploads/sites/28/2016/06/UMO.png', 'example@mail.com', '+380971234567');

