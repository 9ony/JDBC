package day03;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import common.DBUtil;
public class PreparedStatementTest3 {

	public static void main(String[] args) throws Exception{
		Connection con = DBUtil.getCon();
		// 검색할 사원의 이름을 입력받아서 해당 사원정보를 출력하세요
		// 사번, 사원명, 부서명, 담당업무, 입사일, 근무지 가져와 출력하기
		Scanner sc = new Scanner(System.in);
		System.out.println("검색할 사원의 이름 입력: ");
		String name = sc.next();
		String sql = "select empno, ename, dname, job, hiredate, loc";
		sql += " from emp e join dept d on e.deptno = d.deptno where ename=upper(?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		boolean flag;
		while(flag=rs.next()) {
			System.out.println(rs.getInt("empno")+"\t"+rs.getString("ename")+"\t"+
								rs.getString("dname")+"\t"+rs.getString("job")+"\t\t"+
								rs.getString("hiredate")+"\t"+rs.getString("loc"));
		}
		if(flag==false) {
			System.out.println("검색한 사원이 없습니다.");
		}
		rs.close();
		ps.close();
		con.close();
	}

}
