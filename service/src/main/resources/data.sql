DROP TABLE MAGAZINE_ENTITY_EDITORS;
DROP TABLE MAGAZINE_ENTITY_PAYMENT_TYPES;
DROP TABLE MAGAZINE_ENTITY_REVIEWERS;
DROP TABLE MAGAZINE_ENTITY_SCIENTIFIC_FIELDS;
DROP TABLE MAGAZINE;

CREATE TABLE PUBLIC.MAGAZINE_ENTITY_EDITORS
(MAGAZINE_ISSN VARCHAR NOT NULL, EDITORS VARCHAR NOT NULL);

CREATE TABLE PUBLIC.MAGAZINE_ENTITY_PAYMENT_TYPES
(MAGAZINE_ISSN VARCHAR NOT NULL, PAYMENT_TYPES VARCHAR NOT NULL);

CREATE TABLE PUBLIC.MAGAZINE_ENTITY_REVIEWERS
(MAGAZINE_ISSN VARCHAR NOT NULL, REVIEWERS VARCHAR NOT NULL);

CREATE TABLE PUBLIC.MAGAZINE_ENTITY_SCIENTIFIC_FIELDS
(MAGAZINE_ISSN VARCHAR NOT NULL, SCIENTIFIC_FIELDS VARCHAR NOT NULL);

CREATE TABLE PUBLIC.MAGAZINE
(ISSN VARCHAR NOT NULL, TITLE VARCHAR NOT NULL, IS_OPEN_ACCESS BOOLEAN, SUBSCRIPTION_FEE_AMOUNT FLOAT, STATUS VARCHAR, MAIN_EDITOR VARCHAR);

DROP TABLE USER_ENTITY_SCIENTIFIC_FIELDS;
DROP TABLE USER;

CREATE TABLE PUBLIC.USER_ENTITY_SCIENTIFIC_FIELDS
(USER_ID VARCHAR NOT NULL, SCIENTIFIC_FIELDS VARCHAR NOT NULL);

CREATE TABLE PUBLIC.USER
(ID VARCHAR NOT NULL, EMAIL VARCHAR, USERNAME VARCHAR, PASSWORD VARCHAR, IS_REVIEWER BOOLEAN, IS_EDITOR BOOLEAN,
FIRSTNAME VARCHAR, LASTNAME VARCHAR, TITLE VARCHAR, CITY VARCHAR, STATE VARCHAR);

insert into magazine(issn, title, is_open_access, main_editor)
values ('m123', 'magazine1', true, 'perica');
insert into magazine(issn, title, is_open_access, main_editor)
values ('m124', 'magazine2', false, 'zika');
insert into magazine(issn, title, is_open_access, main_editor)
values ('m125', 'magazine3', true, 'djordje');

insert into user(id, email, username, password, is_reviewer, is_editor)
values ('u123', 'dobrica21@gmail.com', 'perica', '123', true, true);
insert into user(id, email, username, password, is_reviewer, is_editor)
values ('u124', 'dobrica21@gmail.com', 'zika', '123', true, true);
insert into user(id, email, username, password, is_reviewer, is_editor)
values ('u125', 'dobrica21@gmail.com', 'djordje', '123', true, true);
insert into user(id, email, username, password, is_reviewer, is_editor)
values ('u126', 'dobrica21@gmail.com', 'dobrica', '123', true, true);