package day04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Scanner;

import common.DBUtil;
//create or replace procedure memo_edit(
//pno in memo.idx%type,
//pname in memo.name%type,
//pmsg in memo.msg%type
//)
//is
//begin
//update memo set name=pname,msg=pmsg where idx=pno;
//end;
///
public class CallableStatementTest2 {

	public static void main(String[] args) throws Exception{
		Scanner sc=new Scanner(System.in);
		System.out.println("글번호를 입력해주세요:");
		int idx = sc.nextInt();
		sc.skip("\r\n");
		System.out.println("사용자명 :");
		String name = sc.nextLine();
		System.out.println("메세지를 입력해주세요 :");
		String msg = sc.nextLine();
		System.out.println(idx+"/"+name+"/"+msg);
		
		
		Connection con = DBUtil.getCon();
		String sql="{call memo_edit(?,?,?)}";
		
		CallableStatement cs=con.prepareCall(sql);
		try {
		cs.setInt(1, idx);
		cs.setString(2, name);
		cs.setString(3, msg);
		}catch(Exception e) {
			e.printStackTrace();
		}

		int n=cs.executeUpdate();
		System.out.println("n:"+n);
		if(n>0) {
		System.out.println("메모글 수정 성공!");
		}else {
			System.out.println("일치하는 idx가 없어요");
		}
		cs.close();
		con.close();
	}
}