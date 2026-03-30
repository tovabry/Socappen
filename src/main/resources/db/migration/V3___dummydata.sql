INSERT INTO app_user (email, role, password_hash, status)
VALUES
    (N'admin@resursenheten.se', 'admin', N'hashed_admin_pw', 'active'),
    (N'elin@ungdom.se', 'user', N'hashed_user_pw1', 'active'),
    (N'oscar@ungdom.se', 'user', N'hashed_user_pw2', 'active'),
    (N'maria@ungdom.se', 'user', N'hashed_user_pw3', 'active'),
    (N'jonas@ungdom.se', 'user', N'hashed_user_pw4', 'active'),
    (N'systemadmin@resursenheten.se', 'sysadmin', N'hashed_password', 'active');
GO

INSERT INTO conversation (status)
VALUES
    ('active'),
    ('active'),
    ('active');
GO

INSERT INTO conversation_participant (conversation_id, app_user_id)
VALUES
    (1, 1), -- admin
    (1, 2), -- Elin
    (1, 3), -- Oscar
    (2, 1), -- admin
    (2, 4), -- Maria
    (3, 1), -- admin
    (3, 5); -- Jonas
GO

INSERT INTO message (conversation_id, sender_id, content)
VALUES
    (1, 2, N'Hej, jag behöver hjälp med skolan och min familjesituation.'),
    (1, 1, N'Hej Elin! Vi kan boka ett samtal med en socialsekreterare.'),
    (1, 3, N'Jag har också frågor om min fritid.'),
    (1, 1, N'Vi kan hjälpa dig med aktiviteter och stöd.'),
    (2, 4, N'Jag känner mig stressad och vet inte vart jag ska vända mig.'),
    (2, 1, N'Vi lyssnar gärna och kan ge stöd eller rådgivning.'),
    (3, 5, N'Tack för hjälpen, allt känns bättre nu.'),
    (3, 1, N'Varsågod! Kontakta oss igen om du behöver mer stöd.');
GO

INSERT INTO question (nickname, text, status)
VALUES
    (N'Elin', N'Hur kan jag få stöd med läxorna?', 'pending'),
    (N'Oscar', N'Kan jag träffa en kurator utan att mina föräldrar vet?', 'pending'),
    (N'Maria', N'Hur gör jag en orosanmälan anonymt?', 'pending');
GO

INSERT INTO answer (question_id, app_user_id, text)
VALUES
    (1, 1, N'Vi kan sätta upp stöd med läxhjälp via vår resursenhet.'),
    (2, 1, N'Du kan boka tid med kurator själv, men viss information kan behöva delas med vårdnadshavare beroende på ålder.'),
    (3, 1, N'Du kan göra en anonym orosanmälan via kommunens webbplats eller ringa socialtjänsten.');
GO

INSERT INTO frequently_asked_question (question, answer)
VALUES
    (N'Hur kan jag få stöd med läxorna?', N'Vi erbjuder läxhjälp och stödgrupper för ungdomar.'),
    (N'Kan jag träffa en kurator anonymt?', N'Du kan kontakta kuratorn direkt, vissa uppgifter kan behöva delas med föräldrar beroende på ålder.'),
    (N'Hur gör jag en orosanmälan anonymt?', N'Du kan fylla i formuläret på kommunens webbplats eller ringa socialtjänsten och vara anonym.');
GO