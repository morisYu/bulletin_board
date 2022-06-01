package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.Notice;

public class NoticeService {
	
	private static String sql_url = "jdbc:mysql://localhost:3306/board01?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul";
	private static String sql_id = "board";
	private static String sql_pw = "boardProject1234";
	private static String driver = "com.mysql.cj.jdbc.Driver";

	// 공지사항 목록 전체 가져오기(페이지 없이 모든 항목)
	public List<Notice> getListAll() throws ClassNotFoundException, SQLException{
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(sql_url, sql_id, sql_pw);
		
		// 모든 게시물을 가지고 오는 쿼리문
		String view = "SELECT * FROM notice_view";
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(view);
		
		// DBMS 에서 가지고 온 데이터를 저장할 List 생성
		List<Notice> list = new ArrayList<Notice>();
		
		// 가지고 온 데이터를 List에 저장
		while(rs.next()) {
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String writerID = rs.getString("writer_ID");
			String content = rs.getString("content");
			String regdate = rs.getString("regdate");
			int hit = rs.getInt("hit");
			String files = rs.getString("files");
			
			Notice notice = new Notice(id, title, writerID, content, regdate, hit, files);
			
			list.add(notice);
		}
		return list;
	}
	
	// 공지사항 목록 전체 가져오기(페이지별)
	public List<Notice> getListPage(int page) throws ClassNotFoundException, SQLException{
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(sql_url, sql_id, sql_pw);
		
		// 한 페이지에 10 개의 게시물을 볼 수 있도록 게시물 시작과 끝 변수 설정
		int start = 1 + 10 * (page - 1);
		int end = 10 * page;
		
		// 페이지 별 10 개의 게시물을 RNUM 기준으로 1 ~ 10 까지 조회하는 쿼리문
		String view = "SELECT * FROM notice_view WHERE RNUM BETWEEN ? AND ?";
		
		PreparedStatement st = con.prepareStatement(view);
		st.setInt(1, start);
		st.setInt(2, end);
		ResultSet rs = st.executeQuery();
		
		// DBMS 에서 가지고 온 데이터를 저장할 List 생성
		List<Notice> list = new ArrayList<Notice>();
		
		// 가지고 온 데이터를 List에 저장
		while(rs.next()) {
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String writerID = rs.getString("writer_ID");
			String content = rs.getString("content");
			String regdate = rs.getString("regdate");
			int hit = rs.getInt("hit");
			String files = rs.getString("files");
			
			Notice notice = new Notice(id, title, writerID, content, regdate, hit, files);
			
			list.add(notice);
		}
		return list;
	}
	
	// 검색어를 통해서 데이터 조회
	public List<Notice> getListSearch(int page, String searchField, String searchWord) throws ClassNotFoundException, SQLException{
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(sql_url, sql_id, sql_pw);
		
		// 한 페이지에 10 개의 게시물을 볼 수 있도록 게시물 시작과 끝 변수 설정
		int start = 1 + 10 * (page - 1);
		int end = 10 * page;
		
		// 검색 필드에서 해당 검색어가 포함된 행을 조회해서 날짜 역순으로 정렬해서 RNUM 을 순서대로 부여
		// 페이지에 맞게 한 페이지에 10 개의 게시물을 조회
		String view = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY regdate DESC)"
						+ " AS RNUM, id, title, writer_ID, content, regdate,"
						+ " hit, files FROM notice WHERE "
						+ searchField + " LIKE '%"+searchWord+"%') AS N"
						+ " WHERE RNUM BETWEEN ? AND ?";
		
		PreparedStatement st = con.prepareStatement(view);
		st.setInt(1, start);
		st.setInt(2, end);
		ResultSet rs = st.executeQuery();
		
		// DBMS 에서 가지고 온 데이터를 저장할 List 생성
		List<Notice> list = new ArrayList<Notice>();
		
		// 가지고 온 데이터를 List에 저장
		while(rs.next()) {
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String writerID = rs.getString("writer_ID");
			String content = rs.getString("content");
			String regdate = rs.getString("regdate");
			int hit = rs.getInt("hit");
			String files = rs.getString("files");
			
			Notice notice = new Notice(id, title, writerID, content, regdate, hit, files);
			
			list.add(notice);
		}
		return list;
	}
	
	// 공지사항 추가하기
	public int insert(String title, String writer_ID, String content, String files) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection con = DriverManager.getConnection(sql_url, sql_id, sql_pw);			
		
		// DB 에 추가하기 위한 쿼리문
		String insertQuery = "INSERT INTO notice(title, writer_ID, content, files)"
							+ " VALUES (?, ?, ?, ?)";
		
		PreparedStatement st = con.prepareStatement(insertQuery);
		st.setString(1, title);
		st.setString(2, writer_ID);
		st.setString(3, content);
		st.setString(4, files);
		
		int result = st.executeUpdate();
		
		st.close();
		con.close();
		
		return result;
	}
	
	// 공지사항 수정하기
	public int update(String title, String content, String files, int update_id) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection con = DriverManager.getConnection(sql_url, sql_id, sql_pw);

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

		st.close();
		con.close();
		
		return result;
	}
	
	// 공지사항 삭제하기
		public int delete(int delete_id) throws SQLException, ClassNotFoundException {
			
			Class.forName(driver);
			Connection con = DriverManager.getConnection(sql_url, sql_id, sql_pw);

			System.out.println();

			String updateSql = "DELETE FROM notice WHERE id = ?";
			
			PreparedStatement st = con.prepareStatement(updateSql);
			st.setInt(1, delete_id);
			
			int result = st.executeUpdate();
			
			st.close();
			con.close();
			
			return result;
		}

		// 총 게시글 갯수 구하기
		public int getCount() throws SQLException, ClassNotFoundException {
			
			int count = 0;
			
			Class.forName(driver);
			Connection con = DriverManager.getConnection(sql_url, sql_id, sql_pw);
			
			// 전체 게시글의 갯수를 가져옴
			String countSql = "SELECT COUNT(id) AS count FROM notice";
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(countSql);
			
			if(rs.next()) {
				// 쿼리문 실행 시 컬럼명과 일치해야 함(별칭 사용하면 컬럼명 사용하기 편함)
				count = rs.getInt("count");
			}
			
			rs.close();
			stmt.close();
			con.close();
			
			return count;
		}
}
