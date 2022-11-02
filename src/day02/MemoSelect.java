package day02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class MemoSelect {
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "scott", pwd = "tiger";

	public MemoSelect() {
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MemoSelect app = new MemoSelect();
		Scanner sc = new Scanner(System.in);
		System.out.println("기능을 선택하세요.");
		System.out.println("1.memo테이블 출력");
		System.out.println("2.memo테이블 번호로검색");
		System.out.println("3.memo테이블 메세지로검색");
		System.out.println("4.memo테이블 작성자로검색");
		int menuSelect = sc.nextInt();
		if (menuSelect == 1) {
			ArrayList<MemoVO> memoList = app.selectMemoAll();
			app.printMemo(memoList);
		} else if (menuSelect == 2) {
			System.out.println("검색할 글번호를 입력하세요");
			int idx = sc.nextInt();
			MemoVO memo = app.selectMemo(idx);
			System.out.println(
					"\t" + memo.getIdx() + "\t" + memo.getName() + "\t" + memo.getMsg() + "\t\t" + memo.getWdate());
		} else if (menuSelect == 3) {
			System.out.println("메세지를 입력하세요");
			String str = sc.next();
			ArrayList<MemoVO> memoList = app.findMemoByMsg(str);
			app.printMemo(memoList);
		} else if (menuSelect == 4) {
			System.out.println("작성자를 입력하세요");
			String str = sc.next();
			ArrayList<MemoVO> memoList = app.findMemoByName(str);
			app.printMemo(memoList);
		} else {
			System.out.println("일치하는 메뉴가 없습니다.");
		}
	}

	// 글 번호로 특정글을 가져오는 메서드
	public MemoVO selectMemo(int idx) {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			String sql = "select * from memo where idx=" + idx;
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(rs.next()); // 넥스트호출
			MemoVO memoVO = new MemoVO();
			memoVO.setIdx(rs.getInt("IDX"));
			memoVO.setName(rs.getString("NAME"));
			memoVO.setMsg(rs.getString("MSG"));
			memoVO.setWdate(rs.getDate("WDATE"));
			return memoVO;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
	}// -------------------------------------

	public ArrayList<MemoVO> findMemoByMsg(String keyword) {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			String sql = "select * from memo where msg like '%" + keyword + "%'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			ArrayList<MemoVO> list = makeList(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
	}

	// -------------------------------------
	public ArrayList<MemoVO> findMemoByName(String keyword) {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			String sql = "select * from memo where name like '%" + keyword + "%'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			ArrayList<MemoVO> list = makeList(rs);
			return list;
		} catch (Exception e) {
			return null;
		} finally {
			close();
		}
	}// -------------------------------------

	public ArrayList<MemoVO> selectMemoAll() {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			String sql = "select * from memo";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			ArrayList<MemoVO> list = makeList(rs);
			return list;
		} catch (Exception e) {
			return null;
		} finally {
			close();
		}
	}

	public ArrayList<MemoVO> makeList(ResultSet rs) throws SQLException {
		ArrayList<MemoVO> list = new ArrayList<>();
		while (rs.next()) {
			MemoVO memoVO = new MemoVO();
			memoVO.setIdx(rs.getInt("IDX"));
			memoVO.setName(rs.getString("NAME"));
			memoVO.setMsg(rs.getString("MSG"));
			memoVO.setWdate(rs.getDate("WDATE"));
			list.add(memoVO);
		}
		return list;
	}

	public void printMemo(ArrayList<MemoVO> memoList) {
		if (memoList.size() != 0) {
			System.out.println("----------------------------------");
			System.out.println("\t글번호\t작성자명\t메모내용\t\t작성일");
			System.out.println("----------------------------------");
			for (MemoVO memo : memoList) {
				System.out.println(
						"\t" + memo.getIdx() + "\t" + memo.getName() + "\t" + memo.getMsg() + "\t\t" + memo.getWdate());
			}
		} else
			System.out.println("일치하는 정보가 없습니다");
	}
}
