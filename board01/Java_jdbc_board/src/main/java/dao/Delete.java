package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Delete {

	private static String sql_url = "jdbc:mysql://localhost:3306/board01?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul";
	private static String sql_id = "board";
	private static String sql_pw = "boardProject1234";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(sql_url, sql_id, sql_pw);
		Statement stmt = con.createStatement();
		
		// 기존 notice 테이블에서 데이터를 10 개만 복사한 copy_notice 테이블 조회
		String selectSql = "SELECT * FROM copy_notice";
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

		System.out.print("삭제할 id 를 입력하세요> ");
		int delete_id = scanner.nextInt();
		System.out.println();

		// 데이터 삭제를 위한 쿼리문
		String updateSql = "DELETE FROM copy_notice WHERE id = ?";
		
		PreparedStatement st = con.prepareStatement(updateSql);
		st.setInt(1, delete_id);

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
