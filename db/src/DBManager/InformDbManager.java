package DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import DBVO.InformVO;

public class InformDbManager {
Scanner sc = new Scanner(System.in);
	
	private final static String url = "jdbc:oracle:thin:@localhost:1521/xe";
	private static final String uid = "green01";
	private static final String upw = "1234";
	
	public Connection dbConnect() throws Exception {
		
		Class.forName("oracle.jdbc.OracleDriver");
		Connection cnn=DriverManager.getConnection(url,uid,upw);		
		
		return cnn;
	}
	
	public ArrayList<InformVO> getList() throws Exception{
		
		ArrayList<InformVO> list = new ArrayList<InformVO>();	
		
		
		String query="select * from notice order by ��ȣ";
		
		Connection cnn = dbConnect();
		Statement stmt = cnn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()) {
			
			InformVO vo = new InformVO();
			
			vo.setNum(rs.getString("��ȣ"));
			vo.setTitle(rs.getString("����"));
			vo.setContents(rs.getString("����"));
			vo.setWriter(rs.getString("�ۼ���"));
			vo.setDate(rs.getString("�ۼ�����"));
			
			
			list.add(vo);
			
		}
		
		for(InformVO v : list) {					
			System.out.println(v.toString());
		}
		
		return list;
		
	}
	
	public void update() throws Exception{
		
		Connection cnn = dbConnect();
		Statement stmt = cnn.createStatement();
		
		check();
		
		System.out.println("������ ������ ��ȣ�� �Է��ϼ���");
		String num =sc.nextLine();
		
		System.out.println("����,���� �� �����Ͻ� �κ��� �� �Է��ϼ���");
		String c = sc.nextLine();
		if(c.equals("����,����")||c.equals("����,����")) {
			
			System.out.println("���ο� ������ �Է��ϼ���");
			String title = sc.nextLine();		
			
			System.out.println("���ο� ������ �Է��ϼ���");
			String contents = sc.nextLine();			
			
			String query="update notice set ����='"+title+"',����='"+contents+"',�ۼ�����=sysdate where ��ȣ ='"+num+"'";
			int rs = stmt.executeUpdate(query);
			if(rs==1) {
				
				System.out.println(num+"�� ������ �����Ǿ����ϴ�");
			}	
		
		}else if(c.equals("����")) {
			System.out.println("���ο� ������ �Է��ϼ���");
			String title = sc.nextLine();	
			String query="update notice set ����='"+title+"',�ۼ�����=sysdate where ��ȣ ='"+num+"'";
			int rs = stmt.executeUpdate(query);
			if(rs==1) {
				
				System.out.println(num+"�� ������ �����Ǿ����ϴ�");
			}	
		
		}else if(c.equals("����")) {
			System.out.println("���ο� ������ �Է��ϼ���");
			String contents = sc.nextLine();	
			String query="update notice set ����='"+contents+"',�ۼ�����=sysdate where ��ȣ ='"+num+"'";
			int rs = stmt.executeUpdate(query);
			if(rs==1) {
				
				System.out.println(num+"�� ������ �����Ǿ����ϴ�");
			}	
		}
		
		
					
	}
	
	public void insert() throws Exception {
		
		
		Connection cnn = dbConnect();
		
		String query="insert into notice values(?,?,?,'admin',sysdate) ";
		PreparedStatement pstmt =cnn.prepareStatement(query);
			
		System.out.println("������ �Է��ϼ���");
		String title =sc.nextLine();
		
		System.out.println("������ �Է��ϼ���");
		String contents =sc.nextLine();
		
		int n = getNextNumber();
		
		pstmt.setInt(1,n);	
		pstmt.setString(2, title);
		pstmt.setString(3, contents);
			
		int rs = pstmt.executeUpdate();
						
		check();
		
	}
	
	public void delete() throws Exception{
		Connection cnn = dbConnect();
		
		check();
	
		System.out.println("������ ������ ��ȣ�� �Է��ϼ���");
		int num =sc.nextInt();
		String query="delete from notice where ��ȣ=?";
		
		PreparedStatement pstmt = cnn.prepareStatement(query);
		pstmt.setInt(1, num);
		
		int rs = pstmt.executeUpdate();				
		if(rs==1) {
			System.out.println(num+"�� ������ �����Ǿ����ϴ�");
		}
	}
	
	public void find() throws Exception {
		Connection cnn = dbConnect();

        check();
		
		System.out.println("��ȸ�� ������ ��ȣ�� �Է��ϼ���");
		int num = sc.nextInt();
		String query = "select * from notice where ��ȣ=?";
		
		PreparedStatement pstmt = cnn.prepareStatement(query);
		pstmt.setInt(1,num);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			
			InformVO vo = new InformVO();
			
			
			vo.setTitle(rs.getString("����"));
			vo.setContents(rs.getString("����"));
			
			vo.setDate(rs.getString("�ۼ�����"));
			
			System.out.println("����: "+vo.getTitle()+"\n����: "+vo.getContents()+"\n�ۼ�����: "+vo.getDate());
		}
		
	}
	
	public void check() throws Exception {
		
		Connection cnn = dbConnect();
		Statement stmt = cnn.createStatement();
		String query = "select * from notice order by ��ȣ";
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()) {
			InformVO vo = new InformVO();
			
			vo.setNum(rs.getString("��ȣ"));
			vo.setTitle(rs.getString("����"));
			
			System.out.println(vo.getNum()+" "+vo.getTitle());
		}
	}
	
	public int getNextNumber() throws Exception {
		
		String query = "(select max(��ȣ)+1 from notice )";
		Connection cnn = dbConnect();
		Statement stmt =cnn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
        
		int n=0;
		if(rs.next()) {
			
			InformVO vo = new InformVO();
			vo.setNum(rs.getString(1));
			
			String a=vo.getNum();
			 n=Integer.parseInt(a);
		}
		
		return n;
		
	}
}
