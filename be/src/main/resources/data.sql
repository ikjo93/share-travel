INSERT INTO `travel_keyword` (name, created_date, modified_date)
VALUES ('운동시설', NOW(), NOW()), ('야경', NOW(), NOW()), ('산책', NOW(), NOW()),
       ('감수성', NOW(), NOW()), ('일몰', NOW(), NOW()), ('일출', NOW(), NOW());

INSERT INTO `user` (email, name, nickname, picture, provider, role, created_date, modified_date)
VALUES ('ikjo1@hanmail.net', '익조1', '중복된닉네임1', null, 'GOOGLE', 'ROLE_USER', NOW(), NOW()),
       ('ikjo2@hanmail.net', '익조2', '중복된닉네임2', null, 'GOOGLE', 'ROLE_USER', NOW(), NOW()),
       ('ikjo3@hanmail.net', '익조3', '중복된닉네임3', null, 'GOOGLE', 'ROLE_USER', NOW(), NOW());
