DROP DATABASE IF EXISTS jsp_board01_DB;
CREATE DATABASE jsp_board01_DB;
USE jsp_board01_DB;

DROP TABLE IF EXISTS board;
CREATE TABLE board (
	num INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    writer VARCHAR(50) NOT NULL,
    content VARCHAR(1000),
    regdate DATETIME DEFAULT now(),
    cnt INT DEFAULT 0
);

-- 삽입
INSERT INTO board(title, writer, content)
	VALUES ("제목1", "작성자1", "내용1");

-- 조회(전체, 날짜시간 역순 정렬)
SELECT * FROM board ORDER BY regdate DESC;

-- 조회(하나)
SELECT * FROM board WHERE num = 1;

-- safe update mode 설정 해제
SET SQL_SAFE_UPDATES = 0;

-- 수정
UPDATE board SET title="제목수정" WHERE num = 1;

-- 조회수 추가
UPDATE board SET cnt = cnt + 1 WHERE num = 21;

-- 삭제
DELETE FROM board WHERE num = 1;