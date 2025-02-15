CREATE DATABASE IF NOT EXISTS oasisCMS;

USE oasiscms;

create TABLE MEMORIAL(
	M_BD_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
	M_BD_TITLE VARCHAR(100) NOT NULL,
	M_BD_CONTENT VARCHAR(10000) NOT NULL,
	M_BD_READ_CNT BIGINT default 0,
	M_BD_CREATE_DT TIMESTAMP DEFAULT NOW()
);

CREATE TABLE AUTHOR(
	AUTHOR_NO BIGINT PRIMARY KEY AUTO_INCREMENT,
	AUTHOR_NM VARCHAR(100) DEFAULT '오혜령',
    AUTHOR_BIRTH_DT TIMESTAMP, 
    AUTHOR_DETAIL VARCHAR(500)
# BOOK ID 여기에 넣는지 여부
);

CREATE TABLE BOOKS (
	BOOK_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
	BOOK_NM VARCHAR(100) NOT NULL,
    BOOK_PUB_DT TIMESTAMP NOT NULL,
    BOOK_IMG VARCHAR(100) UNIQUE,
    BOOK_UUID VARCHAR(100),
    BOOK_PUBLISHER VARCHAR(100) DEFAULT '암마북스'
    
);

CREATE TABLE DM(
	DM_ID	BIGINT PRIMARY KEY AUTO_INCREMENT,
	FROM_MEMBER_NO BIGINT,
    TO_MEMBER_NO BIGINT,
    MESSAGE VARCHAR(2000),
    CREATE_DT TIMESTAMP DEFAULT NOW()
);

CREATE TABLE NOTICE (
	MEMBER_NO
);