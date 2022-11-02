package day03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class PreparedStatementTest2 {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("수정할 사번 입력: ");
		int no=sc.nextInt();
		System.out.println("수정할 업무 입력: ");
		String job=sc.next();
		System.out.println("수정할 부서번호 입력: ");
		int dno=sc.nextInt();
		System.out.println("수정할 급여 입력: ");
		int sal=sc.nextInt();
		
	Class.forName("oracle.jdbc.driver.OracleDriver");
	Connection con;
	PreparedStatement pstmt;
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="scott", pwd="tiger";
	
	con=DriverManager.getConnection(url,user,pwd);
	String sql="UPDATE emp SET job=?,deptno=?,sal=? where empno=?";
	
	pstmt = con.prepareStatement(sql);
	pstmt.setString(1, job);
	pstmt.setInt(2, dno);
	pstmt.setInt(3, sal);
	pstmt.setInt(4, no);
	
	int n = pstmt.executeUpdate();
	
	System.out.println(n+"개의 레코드 수정");
	pstmt.close();
	con.close();
	}
}
