package day05;
/*
create or replace procedure memo_all(
mycr out sys_refcursor)
is
begin
    OPEN mycr for
    select idx,name,msg,wdate from memo
    order by idx desc;
end;
/
 */
import java.sql.*;

import common.DBUtil;

public class CallableStatementTest3 {

	public static void main(String[] args) 
	throws SQLException
	{
		Connection con = DBUtil.getCon();
		String sql="{call memo_all(?)}"; //sys_refcursor
		
		CallableStatement cs=con.prepareCall(sql);
		cs.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
		
//		ResultSet rs=cs.executeQuery(); [x] 에러 발생함
		cs.execute();
		
//		cs.getObject(1);
		ResultSet rs= (ResultSet)cs.getObject(1); // cs.getObject(1)가 resultset이기때문에
		while(rs.next()) {
			int idx=rs.getInt("idx");
			String name=rs.getString("name");
			String msg=rs.getString("msg");
			Date wdate=rs.getDate("wdate");
			System.out.println(idx+"\t"+name+"\t"+msg+"\t"+wdate);
		}
		rs.close();
		cs.close();
		con.close();
	}
}
