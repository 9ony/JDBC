package day04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Scanner;

import common.DBUtil;

//CREATE OR REPLACE PROCEDURE MEMO_ADD(
//PNAME IN VARCHAR2 DEFAULT '아무개',
//PMSG IN MEMO.MSG%TYPE)
//IS
//BEGIN
//    INSERT INTO MEMO(IDX,NAME,MSG,WDATE)
//    VALUES(MEMO_SEQ.NEXTVAL,PNAME, PMSG, SYSDATE);
//    COMMIT;
//END;
///
public class CallableStatementTest {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("작성자 이름을 입력해주세요:");
		String name = sc.nextLine();
		System.out.println("메모내용 :");
		String msg = sc.nextLine();
		System.out.println(name + "/" + msg);

		Connection con = DBUtil.getCon();
		String sql = "{call Memo_ADD(?,?)}";

		// 프로시저를 호출하기 위해서는 CallableStatement객체를 얻어온다.
		CallableStatement cs = con.prepareCall(sql);
		// in parameter값 세팅
		cs.setString(1, name);
		cs.setString(2, msg);

		// 실행
		cs.execute();
		System.out.println("메모글 등록 성공!");
		cs.close();
		con.close();
	}

}
