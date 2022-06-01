/* 게시판 데이터베이스 */
/* 항상 데이터베이스, 테이블이 먼저 생성 되어야 JAVA 에서 사용 가능 */

-- 데이터베이스 생성
DROP DATABASE IF EXISTS board01;
CREATE DATABASE board01;
USE board01;

-- 테이블 생성
DROP TABLE IF EXISTS notice;
CREATE TABLE notice (
	id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    writer_ID VARCHAR(50),
    content LONGTEXT,
    regdate TIMESTAMP DEFAULT NOW(),
    hit INT DEFAULT 0,
    files VARCHAR(1000)
);

-- 샘플 데이터 입력을 위한 프로시저
DROP PROCEDURE IF EXISTS dataInsert;

DELIMITER $$
CREATE PROCEDURE dataInsert()
BEGIN
	DECLARE i INT;
    SET i = 1;

    WHILE(i < 23) DO
		IF(i < 10) THEN
			SET @tmp = CONCAT('0',i);
		ELSE
			SET @tmp = i;
		END IF;
        
	    SET @title = CONCAT('title-', @tmp);
		SET @writer_ID = CONCAT('user- ', FLOOR(RAND()*100));
		SET @content = CONCAT('test content-- ', FLOOR(RAND()*10000));
		SET @regdate = CONCAT('2022-01-', @tmp);
        INSERT INTO notice VALUES (NULL, @title, @writer_ID, @content, @regdate, DEFAULT, NULL);
        SET i = i + 1;
	END WHILE;
END $$
DELIMITER ;
CALL dataInsert();

-- 테이블 전체 조회
SELECT * FROM notice;

-- Delete 실습을 위한 테이블 생성
-- notice 테이블에서 상위 10 개만 데이터를 복사함
DROP TABLE IF EXISTS copy_notice;
CREATE TABLE copy_notice (SELECT * FROM notice LIMIT 10);
SELECT * FROM copy_notice;

-- 등록일시 기준으로 내림차순 정렬 후, 행 번호(RNUM)를 순서대로 부여를 하고나서 RNUM 1 ~ 10 까지 데이터 조회
SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY regdate DESC) AS RNUM, id, title, 
				writer_ID, content, regdate, hit, files FROM notice) AS N
    WHERE RNUM BETWEEN 1 AND 10;

-- 위의 내용을 뷰로 생성
CREATE VIEW notice_view 
AS 
SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY regdate DESC) AS RNUM, id, title, 
				writer_ID, content, regdate, hit, files FROM notice) AS N;

-- 생성된 뷰를 통해 1~10 행의 내용만 출력
SELECT * FROM notice_view WHERE RNUM BETWEEN 1 AND 10;

-- 뷰 삭제(뷰를 삭제하면 JAVA 에서 뷰를 사용하지 못함)
DROP VIEW IF EXISTS notice_view;

-- 검색어로 데이터 조회
-- field 를 'title' 로 설정 후 title 컬럼에 'ti' 문자열이 들어가는 데이터를 조회해서 1~10 행의 내용을 뷰로 생성
DROP VIEW IF EXISTS notice_search_view; 
CREATE VIEW notice_search_view 
AS 
SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY regdate DESC) AS RNUM, id, title, writer_ID, 
				content, regdate, hit, files FROM notice WHERE title LIKE '%ti%') AS N;
                
SELECT * FROM notice_search_view WHERE RNUM BETWEEN 1 AND 10;

-- 게시글의 갯수 확인
-- JAVA 에서 사용하기 쉽게 별칭 사용
SELECT COUNT(id) AS count FROM notice;