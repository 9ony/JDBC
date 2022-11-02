package day05;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import common.DBUtil;
/*
 create or replace procedure emp_forjava(
pdno in emp.deptno%type,
mycr out sys_refcursor)
is
begin
    OPEN mycr for
    select ename,job,hiredate,dname,loc from
    (select * from emp where emp.deptno=pdno) A join dept D
    on A.deptno = D.deptno;
end;
/
 */
public class CallableStatementTest4 {

	public static void main(String[] args) 
			throws SQLException
			{
				Connection con = DBUtil.getCon();
				String sql="{call emp_forjava(?,?)}"; //sys_refcursor
				Scanner sc = new Scanner(System.in);
				System.out.println("부서번호를 입력하세요:");
				int deptno = sc.nextInt();
				CallableStatement cs=con.prepareCall(sql);
				cs.setInt(1, deptno);
				cs.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
				cs.execute();
				ResultSet rs= (ResultSet)cs.getObject(2);
				while(rs.next()) {
					String ename=rs.getString("ename");
					String job=rs.getString("job");
					Date hiredate=rs.getDate("hiredate");
					String dname=rs.getString("dname");
					String loc=rs.getString("loc");
					System.out.println(ename+"\t"+job+"\t"+hiredate+"\t"+dname+"\t"+loc);
				}
				rs.close();
				cs.close();
				con.close();
			}
}
