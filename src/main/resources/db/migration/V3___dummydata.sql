INSERT INTO app_user (email, role, password_hash, status)
VALUES
    ('admin1@example.com', 'admin', 'hashed_admin1_pw', 'active'),
    ('user1@example.com', 'user', 'hashed_user1_pw', 'active'),
    ('user2@example.com', 'user', 'hashed_user2_pw', 'active'),
    ('admin2@example.com', 'admin', 'hashed_admin2_pw', 'active'),
    ('user3@example.com', 'user', 'hashed_user3_pw', 'active'),
    ('user4@example.com', 'user', 'hashed_user4_pw', 'active')
;
GO

INSERT INTO conversation (status)
VALUES
    ('active'),
    ('active'),
    ('active')
;
GO

INSERT INTO conversation_participant (conversation_id, app_user_id)
VALUES
    (1,1),
    (1, 2),
    (2, 1),
    (2, 3),
    (3, 4),
    (3, 5)
;
GO

INSERT INTO message (conversation_id, sender_id, content)
VALUES
    -- Conversation 1 (admin1 + user1)
    (1, 1, N'Hej, hur kan jag hjälpa dig?'),
    (1, 2, N'Jag har ett problem'),
    (1, 1, N'Vad har du för problem?'),
    (1, 2, N'Det är hemligt'),

    -- Conversation 2 (admin1 + user2)
    (2, 1, 'Hej user2'),
    (2, 3, 'Hej admin, jag undrar saker och ting'),
    (2, 1, N'Spännande, berätta mer'),
    (2, 3, N'Här är mitt problem...'),

    -- Conversation 3 (admin2 + user3 + user4)
    (3, 4, N'Hej, hur är det med dig idag?'),
    (3, 5, N'Det är bra med mig'),
    (3, 6, N'Okej, vill du prata om något?'),
    (3, 4, 'Nej tack.');
GO

INSERT INTO question (nickname, text)
VALUES
    ('Semmel-lover', N'Kan jag få hjälp om jag mår dåligt hemma?'),
    ('Peanut', N'Måste mina föräldrar veta om jag kontaktar resursenheten?'),
    ('Hemlig', N'Hur gör man en orosanmälan för en kompis?'),
    ('Prassel', N'Kan jag få hjälp med praktik eller sysselsättning?'),
    ('Osten', N'Vad händer efter att jag har pratat med en handläggare?');
GO

INSERT INTO answer (question_id, app_user_id, text)
VALUES
    (1, 1, N'Ja. Du kan kontakta resursenheten direkt. Vi lyssnar och hjälper dig att hitta rätt stöd, till exempel samtalskontakt eller annan insats.'),
    (2, 4, N'Det beror på din situation och ålder. I vissa fall behöver vårdnadshavare informeras, men du kan alltid börja med att prata med oss för att få rådgivning.'),
    (3, 1, N'Du kan göra en orosanmälan via kommunens webbplats eller genom att ringa socialtjänsten. Du har möjlighet att vara anonym.'),
    (4, 4, N'Ja. Vi kan hjälpa dig med stöd mot praktik, arbete eller annan sysselsättning genom individuella insatser och samarbete med andra aktörer.'),
    (5, 1, N'Efter första samtalet gör vi en bedömning av vilket stöd som kan vara aktuellt. Ibland startas en utredning enligt socialtjänstlagen.');
GO

INSERT INTO frequently_asked_question (question, answer)
VALUES
    (N'Vad är resursenheten?',
     N'Resursenheten är en del av socialtjänsten som arbetar med stöd och insatser för barn och ungdomar.'),

    (N'Är det gratis att få hjälp?',
     N'Ja, stöd från socialtjänsten och resursenheten är kostnadsfritt.'),

    (N'Kan jag kontakta er själv även om jag är under 18?',
     N'Ja, du kan alltid kontakta oss för rådgivning och stöd.'),

    (N'Vad är en utredning?',
     N'En utredning innebär att socialtjänsten tillsammans med dig och din familj kartlägger situationen för att bedöma vilket stöd som behövs.'),

    (N'Hur lång tid tar det att få hjälp?',
     N'Det beror på situationens allvar och vilken typ av stöd som behövs. Akuta ärenden prioriteras.');
GO