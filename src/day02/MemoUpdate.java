package day02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MemoUpdate {
	private Connection con;
	private Statement stmt;

	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "scott", pwd = "tiger";

	public MemoUpdate() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int updateMemo(MemoVO memo) {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			con.setAutoCommit(true);
			// sql문 -> update문 작성하기
			String sql = "update memo set name='" + memo.getName() + "',msg='" + memo.getMsg()
					+ "', wdate=sysdate where idx=" + memo.getIdx();
			System.out.println(sql);
			// stmt 얻어괴
			stmt = con.createStatement();
			// executeUpdate()로 실행하고 그결과를 return한다
			int n = stmt.executeUpdate(sql);
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			close();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("수정할 글번호 입력: ");
		int idx = sc.nextInt();

		System.out.println("수정할 작성자명: ");
		String name = sc.next();

		sc.skip("\r\n");

		System.out.println("수정할 메모내용: ");
		String msg = sc.nextLine();

		System.out.println(idx + "/" + name + "/" + msg);

		// vo객체에 입력받은 값 담아주기
		MemoVO vo = new MemoVO(idx, name, msg, null);

		MemoUpdate app = new MemoUpdate();
		int n = app.updateMemo(vo);

		String res = (n > 0) ? "글 수정 성공" : "수정 실패";
		System.out.println(res);
	}

}
