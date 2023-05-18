CREATE DATABASE IF NOT EXISTS sharetravel CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

use sharetravel;

drop table if exists `user_travel_keyword`;
drop table if exists `travel_keyword`;
drop table if exists `user`;

create table `travel_keyword`
(
    travel_keyword_id bigint not null auto_increment,
    name              varchar(255),
    created_date      TIMESTAMP,
    modified_date     TIMESTAMP,
    primary key (travel_keyword_id)
);

create table `user`
(
    user_id       bigint       not null auto_increment,
    email         varchar(255) not null,
    name          varchar(255) not null,
    nickname      varchar(15),
    picture       varchar(500),
    provider      varchar(255) not null,
    role          varchar(255) not null,
    created_date  TIMESTAMP,
    modified_date TIMESTAMP,
    primary key (user_id),
    UNIQUE INDEX index_email_provider (email, provider),
    UNIQUE INDEX index_nickname (nickname)
);

create table `user_travel_keyword`
(
    user_travel_keyword_id bigint not null auto_increment,
    user_id                bigint,
    travel_keyword_id      bigint,
    created_date           TIMESTAMP,
    modified_date          TIMESTAMP,
    primary key (user_travel_keyword_id),
    foreign key (user_id) references `user` (user_id),
    foreign key (travel_keyword_id) references `travel_keyword` (travel_keyword_id)
);
