CREATE DATABASE IF NOT EXISTS oasisCMS;

USE oasiscms;
# # 로그인 데이트 기본값 NOW()로 해도 되 

CREATE TABLE MEMBER (
    MEMBER_ID 		VARCHAR(100) PRIMARY KEY,
	PASSWD			VARCHAR(100) NOT NULL,
	MEMBER_NM 		VARCHAR(50)	NOT NULL,
	MEMBER_NICK		VARCHAR(50)	NOT NULL,
	PROFILE_IMG 	VARCHAR(200),
	PROFILE_UUID	VARCHAR(200),
	SEX 			CHAR(1)		DEFAULT 'M',
	HP 				VARCHAR(20),
	EMAIL			VARCHAR(50),
	ZIPCODE 		VARCHAR(10),
	ROAD_ADDRESS 	VARCHAR(20),
	JIBUN_ADDRESS 	VARCHAR(50),
	NAMUJI_ADDRESS 	VARCHAR(50),
	ETC				VARCHAR(200),
	ACTIVE_YN		CHAR(1) DEFAULT 'Y',
    JOIN_DT			TIMESTAMP	DEFAULT NOW(),
	INACTIVE_DT		TIMESTAMP,
	LOGIN_DT		TIMESTAMP
    );
 
 
CREATE TABLE ADMIN (
# MEMBER_ID에 연결가능? ->그냥 빼는 걸로
	ADMIN_ID 		VARCHAR(20) PRIMARY KEY,
    ADMIN_PW 		VARCHAR(50),
    LOGIN_YN 		CHAR(1) DEFAULT 'N'
	);
    

CREATE TABLE BOARD (
	BD_ID 			BIGINT 			PRIMARY KEY AUTO_increment,
	MEMBER_ID 		VARCHAR(100),
    TITLE			VARCHAR(100) 	NOT NULL,
    CONTENT			VARCHAR(1000) 	NOT NULL,
    READ_CNT 		BIGINT			default 0,
    CREATE_DT 		TIMESTAMP 		DEFAULT NOW(),
    FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(MEMBER_ID) 
    ON UPDATE CASCADE 
    ON DELETE CASCADE 
);    
    
create TABLE MEMORIAL(
	BD_ID 		BIGINT 		PRIMARY KEY AUTO_INCREMENT,
	TITLE 		VARCHAR(100) NOT NULL,
	CONTENT 	VARCHAR(10000) NOT NULL,
	READ_CNT 		BIGINT default 0,
	bsCREATE_DT 		TIMESTAMP DEFAULT NOW()
);

CREATE TABLE BS (
	BS_ID 			BIGINT PRIMARY KEY,
    BS_TITLE 		VARCHAR(100) NOT NULL,
    BS_IMG 			VARCHAR(100) DEFAULT "BS_IMG.JPG",
    BS_UUID 		VARCHAR(100),
    CONTENT 		VARCHAR(10000) NOT NULL,
	READ_CNT 		BIGINT 			DEFAULT 0,
    BS_CATEGORY_1 	VARCHAR(100) 	NOT NULL,
    BS_CATEGORY_2 	VARCHAR(100),
    BS_CATEGORY_3 	VARCHAR(100),
    CREATE_DT		TIMESTAMP DEFAULT NOW()
);

CREATE TABLE BOOK (
	BOOK_ID 			BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	AUTHOR_ID BIGINT	DEFAULT 1,
    BOOK_NM 			VARCHAR(100) 	NOT NULL,
    BOOK_IMG 			VARCHAR(100) 	UNIQUE,
    BOOK_UUID 			VARCHAR(100),
    
    
    PUBLISHER_ID		BIGINT			DEFAULT 1,
    PUBLISHER_NM 		VARCHAR(100) 	DEFAULT '암마북스',
	PUB_DT 				TIMESTAMP 		NOT NULL,
	INTRO_1LINE 		VARCHAR(100),
	INTRO_DETAIL		VARCHAR(1000)
);

CREATE TABLE AUTHOR(
	AUTHOR_ID 			BIGINT 			PRIMARY KEY AUTO_INCREMENT,
    BOOK_ID 			BIGINT,
	AUTHOR_NM 			VARCHAR(10) 	DEFAULT "오혜령",
    AUTHOR_BIRTH_DT 	TIMESTAMP, 
    AUTHOR_DETAIL 		VARCHAR(1000),
	FOREIGN KEY (BOOK_ID) REFERENCES BOOK (BOOK_ID) 
);

CREATE TABLE REPL(
	# REPL_ID의 필요유무?
    REPL_ID				BIGINT				PRIMARY KEY,
    BOARD_ID 			BIGINT, 
    MEMBER_ID 			VARCHAR(100),
    CONTENT 		VARCHAR(500),
    CREATE_DT			TIMESTAMP 			DEFAULT NOW(),
    FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(MEMBER_ID) 
    ON UPDATE CASCADE 
    ON DELETE CASCADE 
);

CREATE TABLE DM (
	DM_ID			BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	FROM_MEMBER_ID 	VARCHAR(100),
    TO_MEMBER_ID 	VARCHAR(100),
    CONTENT 		VARCHAR(2000),
    CREATE_DT 		TIMESTAMP 		DEFAULT NOW(),
    FOREIGN KEY (TO_MEMBER_ID) REFERENCES MEMBER(MEMBER_ID) 
    ON UPDATE CASCADE 
    ON DELETE CASCADE 
);

CREATE TABLE NOTICE (
	# 알림은 받는 사람 입장에서 봐야 하나? PK지정 어떻게? => NOTI_ID 만들어 따로 저장
    NOTI_ID 		BIGINT 				PRIMARY KEY AUTO_INCREMENT,
    NOTI_TYPE 		VARCHAR(20) 		NOT NULL,
	FROM_MEMBER_ID 	VARCHAR(100),
    TO_MEMBER_ID 	VARCHAR(100),
    CONTENT 		VARCHAR(200) 		NOT NULL,
	CREATE_DT 		TIMESTAMP 			DEFAULT NOW(),
    FOREIGN KEY (TO_MEMBER_ID) REFERENCES MEMBER(MEMBER_ID) 
    ON UPDATE CASCADE 
    ON DELETE CASCADE 
);

