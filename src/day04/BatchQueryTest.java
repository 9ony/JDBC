package day04;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.DBUtil;

/*
 * 여러개의 sql문을 한꺼번에 전송하는 일괄처리 방식
 * 여러개의 sql문을 작성해서 statement의 addBatch(sql),
 * executeBatch()메서드로 일괄 처리한다.
 * 모두 성공하던지, 아니면 모두 실패하던지 ==> Transaction
 * */

public class BatchQueryTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Connection con = DBUtil.getCon();
		con.setAutoCommit(false); // 자동커밋 설정해제 기본값:true

		Statement st = con.createStatement();
//		st.addBatch("insert into memo values(memo_seq.nextval,'김길동','Batch테스트11', sysdate)");
//		st.addBatch("insert into memo values(memo_seq.nextval,'임길동','Batch테스트21', sysdate)");
//		st.addBatch("insert into memo values(memo_seq.nextval,'송길동','Batch테스트31', sysdate)");
//		st.addBatch("insert into memo values(memo_seq.nextval,'최길동','Batch테스트41', sysdate)");
//		//일부러 sql문 하나를 에러를 발생시켜 봅시다.
//		st.addBatch("insert into memo values(memo_seq.nextval,'유길동','Batch테스트51', sysdate)");

		st.addBatch("INSERT INTO MEMO VALUES(MEMO_SEQ.NEXTVAL,'김길동','BATCH_TEST1',SYSDATE)");
		st.addBatch("INSERT INTO MEMO VALUES(MEMO_SEQ.NEXTVAL,'임길동','BATCH_TEST2',SYSDATE)");
		st.addBatch("INSERT INTO MEMO VALUES(MEMO_SEQ.NEXTVAL,'최길동','BATCH_TEST3',SYSDATE)");
		st.addBatch("INSERT INTO MEMO VALUES(MEMO_SEQ.NEXTVAL,'강길동','BATCH_TEST4',SYSDATE)");
		st.addBatch("INSERT INTO MEMO VALUES(MEMO_SEQ.NEXTVAL,'유길동','BATCH_TEST5',SYSDATE)");
		st.addBatch("INSERT INTO MEMO VALUES(MEMO_SEQ.NEXTVAL,'송길동','BATCH_TEST6',SYSDATE)");

		boolean isCommit = false;
		try {
			int[] updateCount = st.executeBatch();// 일괄처리하여 실행함
			isCommit = true;
		} catch (SQLException e) {
			isCommit = false;
			e.printStackTrace();
		}
		if (isCommit) {
			con.commit();
		} else {
			con.rollback();
		}
		con.setAutoCommit(true);// 자동 커밋 원상태로 복귀

		boolean b = st.execute("SELECT * FROM MEMO ORDER BY IDX DESC");
		if (b) {
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				System.out
						.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getDate(4));
			}
			rs.close();
		} // if------------------
		st.close();
		con.close();

	}

}
