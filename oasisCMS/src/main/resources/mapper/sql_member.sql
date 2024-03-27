CREATE DATABASE IF NOT EXISTS oasisCMS;
USE oasisCMS;

CREATE TABLE MEMBER (
    MEMBER_ID 		VARCHAR(100) PRIMARY KEY,
	PASSWD				VARCHAR(100) NOT NULL,
	CREATE_DT		TIMESTAMP	DEFAULT NOW(),
	MEMBER_NM 		VARCHAR(50)	NOT NULL,
	MEMBER_NICK		VARCHAR(50)	NOT NULL,
	MEMBER_PROFILE_IMG	VARCHAR(200),
	MEMBER_PROFILE_UUID	VARCHAR(200),
	SEX 			CHAR(1)		DEFAULT 'M',
	HP 				VARCHAR(20),
	EMAIL			VARCHAR(50),
	ZIPCODE 		VARCHAR(10),
	ROAD_ADDRESS 	VARCHAR(20),
	JIBUN_ADDRESS 	VARCHAR(50),
	NAMUJI_ADDRESS 	VARCHAR(50),
	ETC				VARCHAR(200),
	ACTIVE_YN		CHAR(1) DEFAULT 'Y',
	INACTIVE_DT		TIMESTAMP,
	LOGIN_YN		CHAR(1) DEFAULT 'N',
	LOGIN_DT		TIMESTAMP

);