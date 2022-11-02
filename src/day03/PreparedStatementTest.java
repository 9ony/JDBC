package day03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PreparedStatementTest {

	public static void main(String[] args) throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con;
		Statement stmt;
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String user="scott", pwd="tiger";
		
		con=DriverManager.getConnection(url,user,pwd);
		String sql="INSERT INTO emp(empno,ename,job,hiredate,sal,deptno)";
				sql+=" values(?,?,?,SYSDATE,?,?)";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, 8000);
		pstmt.setString(2, "TOM");
		pstmt.setString(3, "MANAGER");
		pstmt.setInt(4, 4000);
		pstmt.setInt(5, 10);
		
		int n = pstmt.executeUpdate();
		
		System.out.println(n+"개의 레코드삽입함");
		
		
		con.close();
	}
}
