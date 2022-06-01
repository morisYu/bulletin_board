package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Read {
	
	// SQL 연결

	// 연결 url => jdbc:mysql://localhost:포트/데이터베이스이름?퍼블릭키사용옵션=true&보안설정옵션=false&서버시간옵션=Asia/Seoul
	// 포트번호 확인방법 => cmd에서 MySQL 접속(mysql -u 아이디 -p비밀번호) 후 SHOW GLOBAL VARIABLES LIKE
	// 'port';
	private static String sql_url = "jdbc:mysql://localhost:3306/board01?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul";

	// MySQL 아이디
	private static String sql_id = "board";

	// MySQL 비밀번호
	private static String sql_pw = "boardProject1234";
	
	// DB 작업은 예외처리 필수로 해야 함
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// MySQl 드라이버 로딩(mysql-connector-java-버전.jar 파일을 라이브러리에 추가해야 함)
		// 프로젝트 properties 에서 Add External JARs 로 .jar 파일을 먼저 추가
		// jar 파일 안에 있는 jdbc 클래스를 불러오는 작업
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// DB 에 접속(MySQL 에 DB 가 생성되어 있어야 함)
		Connection con = DriverManager.getConnection(sql_url, sql_id, sql_pw);
		
		// 테이블의 데이터를 불러오는 sql 문
		String selectQuery = "SELECT * FROM notice";
		
		// sql 문을 실행하는 객체
		Statement stmt = con.createStatement();
		
		// sql 문을 실행, 결과셋의 시작주소를 반환
		ResultSet rs = stmt.executeQuery(selectQuery);
		
		// 결과셋의 레코드 존재 여부를 확인
		while(rs.next()) {
			// ResultSet.get자료형("필드명")
			// 필드명은 DB 테이블에 생성된 컬럼명과 일치해야함
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String writer = rs.getString("writer_ID");
			String content = rs.getString("content");
			String regdate = rs.getString("regdate");
			regdate = regdate.substring(0, 10);
			System.out.printf("%02d. %-8s | %-8s | %11s | %-30s \n", id, writer, title, regdate, content);
		}
		
		// rs, stmt, con 은 메모리를 계속 차지하기 때문에 실행 후 닫아줘야 함(생성 역순으로)
		rs.close();
		stmt.close();
		con.close();
	}
}