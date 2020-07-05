DROP TABLE MAGAZINE_ENTITY_EDITORS;
DROP TABLE MAGAZINE_ENTITY_PAYMENT_TYPES;
DROP TABLE MAGAZINE_ENTITY_REVIEWERS;
DROP TABLE MAGAZINE_ENTITY_SCIENTIFIC_FIELDS;
DROP TABLE MAGAZINE;
DROP TABLE SCIENTIFIC_PAPER_ENTITY_COAUTHORS;
DROP TABLE SCIENTIFIC_PAPER;

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
(USER_ENTITY_ID VARCHAR NOT NULL, SCIENTIFIC_FIELDS VARCHAR NOT NULL);

CREATE TABLE PUBLIC.USER
(ID VARCHAR NOT NULL, EMAIL VARCHAR, USERNAME VARCHAR, PASSWORD VARCHAR, IS_REVIEWER BOOLEAN, IS_EDITOR BOOLEAN,
FIRSTNAME VARCHAR, LASTNAME VARCHAR, TITLE VARCHAR, CITY VARCHAR, STATE VARCHAR, SUBSCRIPTION_STATUS BOOLEAN);

CREATE TABLE PUBLIC.SCIENTIFIC_PAPER_ENTITY_COAUTHORS (SCIENTIFIC_PAPER_ENTITY_ID VARCHAR NOT NULL, USERNAME VARCHAR NOT NULL);

CREATE TABLE PUBLIC.SCIENTIFIC_PAPER
(ID VARCHAR NOT NULL, DOI VARCHAR, TITLE VARCHAR, ABSTRACT VARCHAR, KEYWORDS VARCHAR, FEE NUMERIC,
PDF_NAME VARCHAR, PDF BLOB, SCIENTIFIC_FIELD VARCHAR, MAGAZINE_ID VARCHAR);

insert into magazine(issn, title, is_open_access, main_editor)
values ('m123', 'Časopis 1', true, 'perica');
insert into magazine(issn, title, is_open_access, main_editor)
values ('m124', 'Časopis 2', false, 'zika');
insert into magazine(issn, title, is_open_access, main_editor)
values ('m125', 'Časopis 3', true, 'djordje');

insert into user(id, email, username, password, is_reviewer, is_editor, subscription_status)
values ('u123', 'bccservistim17@gmail.com', 'perica', '$2a$10$3ziaaq0vEUtDNdh4FtOsk.wZKThjO1.reKO.5ELAiSEQoyfL5Neqi', true, true, false);
insert into user(id, email, username, password, is_reviewer, is_editor, subscription_status)
values ('u124', 'bccservistim17@gmail.com', 'zika', '$2a$10$3ziaaq0vEUtDNdh4FtOsk.wZKThjO1.reKO.5ELAiSEQoyfL5Neqi', true, true, true);
insert into user(id, email, username, password, is_reviewer, is_editor, subscription_status)
values ('u125', 'bccservistim17@gmail.com', 'djordje', '$2a$10$3ziaaq0vEUtDNdh4FtOsk.wZKThjO1.reKO.5ELAiSEQoyfL5Neqi', true, true, false);
insert into user(id, email, username, password, is_reviewer, is_editor, subscription_status)
values ('u126', 'bccservistim17@gmail.com', 'dobrica', '$2a$10$3ziaaq0vEUtDNdh4FtOsk.wZKThjO1.reKO.5ELAiSEQoyfL5Neqi', false, false, false);
insert into user(id, email, username, password, is_reviewer, is_editor, subscription_status)
values ('u127', 'bccservistim17@gmail.com', 'marko', '$2a$10$3ziaaq0vEUtDNdh4FtOsk.wZKThjO1.reKO.5ELAiSEQoyfL5Neqi', true, false, false);
insert into user(id, email, username, password, is_reviewer, is_editor, subscription_status)
values ('u128', 'bccservistim17@gmail.com', 'pavle', '$2a$10$3ziaaq0vEUtDNdh4FtOsk.wZKThjO1.reKO.5ELAiSEQoyfL5Neqi', true, false, false);

insert into user_entity_scientific_fields(USER_ENTITY_ID, scientific_fields)
values ('u123', '1,2,3');

insert into scientific_paper(id, doi, title, abstract, keywords, fee, pdf_name, pdf, scientific_field, magazine_id)
values ('sp123', '123', 'rad1', 'sazetak123', 'kw1', 100.00, 'pdf1', null, 'Matematika', 'm123');