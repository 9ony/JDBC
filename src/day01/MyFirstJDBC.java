package day01;

import java.sql.*;

public class MyFirstJDBC {
	/*
	 * java 언어 <====JDBC Driver(통역관)====> DBMS(ORACLE)===> SQL
	 * 
	 * 이클립스에서 ojdbc6.jar(드라이버) 파일을 build path에서 add external library로 추가한다 db서버,
	 * TNSListener startup java를 이용해서 db에 connection 해보자!
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// 1.드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver Loading Success!!");

			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			// 프로토콜:dbms유형:driver타입:host:port:전역데이터베이스이름

			String user = "scott", pwd = "tiger";

			// 2.DB와 연결 => java.sql패키지에 DriverManager클래스의 getConnection()를 이용
			Connection con = DriverManager.getConnection(url, user, pwd);
			System.out.println("DB Connected...");

			// 3.query문 작성 -sql문
			String sql = "CREATE TABLE memo(";
			sql += "idx number(4) primary key,";
			sql += "name varchar2(30) not null,";
			sql += "msg varchar2(100),";
			sql += "wdate date default sysdate)";
			System.out.println(sql);
			// 4.Statement 객체 얻기 => Connection의 createStatement()를 이용
			Statement stmt = con.createStatement();
			// 5.Statement 의 excute()/executeXXX() 메서드를 이용해서 쿼리문을 실행시킨다.
			// boolean execute(): 모든 sql문을 실행시킨다.
			// int executeUpdate(): insert/delete/update문을 실행시킨다
			// ResultSet executeQuery(): select문을 실행시킨다.

			boolean b = stmt.execute(sql);
			System.out.println("b: " + b);

			// 6.DB관련 자원 반납
			stmt.close();
			con.close();
			System.out.println("자원반납성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
