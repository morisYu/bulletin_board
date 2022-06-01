package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Update {
	
	private static String sql_url = "jdbc:mysql://localhost:3306/board01?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul";
	private static String sql_id = "board";
	private static String sql_pw = "boardProject1234";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(sql_url, sql_id, sql_pw);
		Statement stmt = con.createStatement();
		
		// 수정 전/후만 확인하기 위해 데이터 10 개만 추출
		String selectSql = "SELECT * FROM notice LIMIT 10";
		ResultSet rs = stmt.executeQuery(selectSql);
		
		System.out.println("----------수정 전 공지사항----------");
		while (rs.next()) {
			int print_id = rs.getInt("id");
			String print_title = rs.getString("title");
			String print_writerID = rs.getString("writer_ID");
			String print_content = rs.getString("content");
			String print_regdate = rs.getString("regdate");
			System.out.printf("%02d. %-8s | %-8s | %11s | %-30s \n", print_id, print_writerID, print_title, print_regdate, print_content);
		}

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.print("수정할 id 를 입력하세요> ");
		int update_id = scanner.nextInt();
		System.out.print("title 을 입력하세요> ");
		String title = scanner.next();
		System.out.print("content 를 입력하세요> ");
		String content = scanner.next();
		System.out.print("files 를 입력하세요> ");
		String files = scanner.next();
		System.out.println();

		// 데이터 수정을 위한 쿼리문(쿼리문이 길어서 아래처럼 나뉘어서 문자열을 합치는 경우 명령어 사이 띄어쓰기 확인할 것)
		String updateSql = "UPDATE notice "
				+ "	SET "
				+ "title = ?, "
				+ "content = ?, "
				+ "files = ?"
				+ " WHERE id = ?";
		
		PreparedStatement st = con.prepareStatement(updateSql);
		st.setString(1, title);
		st.setString(2, content);
		st.setString(3, files);
		st.setInt(4, update_id);

		int result = st.executeUpdate();
		System.out.println("수정된 행의 수: " + result);

		ResultSet rs2 = stmt.executeQuery(selectSql);
		System.out.println("\n----------수정 후 공지사항----------");
		while (rs2.next()) {
			int print_id = rs2.getInt("id");
			String print_title = rs2.getString("title");
			String print_writerID = rs2.getString("writer_ID");
			String print_content = rs2.getString("content");
			String print_regdate = rs2.getString("regdate");
			System.out.printf("%02d. %-8s | %-8s | %11s | %-30s \n", print_id, print_writerID, print_title, print_regdate, print_content);
			
		}

		rs2.close();
		rs.close();
		stmt.close();
		st.close();
		con.close();
	}
}
