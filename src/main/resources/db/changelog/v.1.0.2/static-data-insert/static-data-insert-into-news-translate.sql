INSERT INTO news (date)
VALUES (NOW()),
       (NOW()),
       (NOW());

INSERT INTO news_translations (news_id, language, title, info)
VALUES (1, 'ru', 'Первая новость', 'Это информация о первой новости.'),
       (1, 'en', 'First News', 'This is information about the first news.'),
       (1, 'de', 'Erste Nachricht', 'Dies ist die Information über die erste Nachricht.'),
       (2, 'ru', 'Вторая новость', 'Это информация о второй новости.'),
       (2, 'en', 'Second News', 'This is information about the second news.'),
       (2, 'de', 'Zweite Nachricht', 'Dies ist die Information über die zweite Nachricht.'),
       (3, 'ru', 'Третья новость', 'Это информация о третьей новости.'),
       (3, 'en', 'Third News', 'This is information about the third news.'),
       (3, 'de', 'Dritte Nachricht', 'Dies ist die Information über die dritte Nachricht.');