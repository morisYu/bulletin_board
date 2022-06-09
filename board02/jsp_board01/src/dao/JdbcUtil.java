package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JdbcUtil {
	private static JdbcUtil instance = new JdbcUtil();
	
	private static DataSource ds;
	static {
		try{
			System.out.println("드라이버 로딩 성공!");
			Class.forName("com.mysql.cj.jdbc.Driver");
			// context.xml 정보를 사용해서 데이터베이스 연결
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/jsp_board01_DB");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private JdbcUtil(){}
	
	public static JdbcUtil getInstance(){
		return instance;
	}
	
	public Connection getConnection() throws SQLException{
		// tomcat-dbcp 풀에서 커넥션 반환
		return ds.getConnection();
	}
}
