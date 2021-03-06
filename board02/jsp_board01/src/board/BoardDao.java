package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.JdbcUtil;
import dto.BoardVo;

public class BoardDao {
	private JdbcUtil ju;
	
	public BoardDao(){
		ju = JdbcUtil.getInstance();
	}
	
	// 데이터 삽입
	public int insert(BoardVo vo){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO board(title, writer, content) VALUES (?, ?, ?)";
		int result = -1;
		try{
			con = ju.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getContent());
			// 업데이트가 정상적으로 되면 1 이 반환됨
			result = pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	// 데이터 조회(전체 데이터)
	public List<BoardVo> selectAll(){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM board ORDER BY regdate DESC";
		
		ArrayList<BoardVo> ls = new ArrayList<BoardVo>();
		
		try{
			con = ju.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				BoardVo vo = new BoardVo(rs.getInt(1), rs.getString(2),	rs.getString(3),
								rs.getString(4), rs.getTimestamp(5),
								rs.getInt(6));
				ls.add(vo);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ls;
	}
	
	// 데이터 조회(하나의 데이터)
	public BoardVo selectOne(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM board WHERE num = ?";
		
		BoardVo vo = null;
		
		try{
			con = ju.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			// 데이터 조회 시 조회수 증가
			updateCnt(num);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				vo = new BoardVo(rs.getInt(1), rs.getString(2),	rs.getString(3),
								rs.getString(4), rs.getTimestamp(5),
								rs.getInt(6));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return vo;
	}
	
	// 데이터 수정
	public int update(BoardVo vo){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE board SET title=?, content=? WHERE num = ?";
		int result = -1;
		try{
			con = ju.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNum());
			// 업데이트가 정상적으로 되면 1 이 반환됨
			result = pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	
	// 조회수 업데이트
	public int updateCnt(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE board SET cnt = cnt + 1 WHERE num = ?";
		int result = -1;
		try{
			con = ju.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			// 업데이트가 정상적으로 되면 1 이 반환됨
			result = pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	// 데이터 삭제
	public int delete(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM board WHERE num = ?";
		int result = -1;
		try{
			con = ju.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			// 업데이트가 정상적으로 되면 1 이 반환됨
			result = pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
}
