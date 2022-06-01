package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Create {

	private static String sql_url = "jdbc:mysql://localhost:3306/board01?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul";
	private static String sql_id = "board";
	private static String sql_pw = "boardProject1234";
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.print("title 을 입력하세요> ");
		String title = scanner.nextLine();
		System.out.print("writer_ID 를 입력하세요> ");
		String writer_ID = scanner.nextLine();
		System.out.print("content 를 입력하세요> ");
		String content = scanner.nextLine();
		System.out.print("files 를 입력하세요> ");
		String files = scanner.next();
		System.out.println();

		// 데이터 삽입을 위한 쿼리문
		String insertSql = "INSERT INTO notice(title, writer_ID, content, files)"
							+ " VALUES (?,?,?,?)";
		
		Connection con = DriverManager.getConnection(sql_url, sql_id, sql_pw);
		
		// 쿼리문에 변수를 사용할 경우 PreparedStatement 객체 생성
		PreparedStatement st = con.prepareStatement(insertSql);
		
		// 쿼리문에서 '?' 의 순서대로 변수를 삽입 가능(1 부터 시작함)
		st.setString(1, title);
		st.setString(2, writer_ID);
		st.setString(3, content);
		st.setString(4, files);

		// DML 쿼리문 실행에 영향을 받는 행 수를 반환
		// INSERT 문 실행으로 하나의 행이 생성되기 때문에 1 이 반환됨
		int result = st.executeUpdate();
		System.out.println(result);

		st.close();
		con.close();
	}
}
