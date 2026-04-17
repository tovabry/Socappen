/*Drop and recreate database*/

USE master;
GO
ALTER DATABASE socapp SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
GO
DROP DATABASE socapp;
GO
CREATE DATABASE socapp;

/*Drop table, change to your table name*/
DROP TABLE message_log;

/*Change a specific column value in table to a new value*/
UPDATE app_user
SET role_id = (SELECT id FROM role WHERE id = 3)
WHERE id = 7;
GO

/*Insert some test data, change to your table, columns and values*/
INSERT INTO message (conversation_id, sender_id, content)
VALUES
    (4, 2, N'Hej.'),
    (4, 1, N'Hej!'),
    (4, 2, N'Blabla'),
    (4, 1, N'Blablablabla');
GO

