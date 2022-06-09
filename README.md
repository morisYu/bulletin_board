# bulletin_board(게시판 실습)  

## board01  

> JAVA 에서 간단한 CRUD 구현  

- 작업 환경  

1. Eclipse version: 2202-03 (4.23)  
2. Server: Apache Tomcat v8.5  
3. JAVA: JDK17  
4. DBMS: MySQL  

- 실행내용  

    - Read, Create, Update, Delete 4 개의 클래스를 생성해서 CRUD 기능 구현(각 클래스별 하나의 기능만 수행함)   
    - NoticeService 클래스에 필요한 기능들을 메소드로 생성  
    - NoticeConsole 클래스에 콘솔창 화면을 구성하는 메소드 생성(NoticeService 클래스를 통해 데이터를 가지고 옴)  
    - UserApp 클래스를 통해 게시판 사용   

- 기타  

    - 서버는 사용하지 않음(차후 업데이트 시 사용 예정)  
    - MySQL 은 root 계정 사용하지 않고 계정 추가해서 사용함  

## board02  

> JSP 게시판  

- 작업 환경  

1. Eclipse version: Neon (4.6)  
2. Server: Apache Tomcat v9.0  
3. JAVA: JDK1.8  
4. DBMS: MySQL  

- 실행내용  

    - 메인페이지  
    - 게시글 목록  
    - 게시글 등록  
    - 게시글 보기  
    - 게시글 수정  
    - 게시글 삭제  

- 기타  

    - 인코딩 설정 시 파라미터 사용 전에 설정 코드 작성해야 함  
    - CSS 적용 안함