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
		
		
		String query="select * from notice order by 번호";
		
		Connection cnn = dbConnect();
		Statement stmt = cnn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()) {
			
			InformVO vo = new InformVO();
			
			vo.setNum(rs.getString("번호"));
			vo.setTitle(rs.getString("제목"));
			vo.setContents(rs.getString("내용"));
			vo.setWriter(rs.getString("작성자"));
			vo.setDate(rs.getString("작성일자"));
			
			
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
		
		System.out.println("변경할 공지의 번호를 입력하세요");
		String num =sc.nextLine();
		
		System.out.println("제목,내용 중 변경하실 부분을 다 입력하세요");
		String c = sc.nextLine();
		if(c.equals("제목,내용")||c.equals("내용,제목")) {
			
			System.out.println("새로운 제목을 입력하세요");
			String title = sc.nextLine();		
			
			System.out.println("새로운 내용을 입력하세요");
			String contents = sc.nextLine();			
			
			String query="update notice set 제목='"+title+"',내용='"+contents+"',작성일자=sysdate where 번호 ='"+num+"'";
			int rs = stmt.executeUpdate(query);
			if(rs==1) {
				
				System.out.println(num+"번 공지가 수정되었습니다");
			}	
		
		}else if(c.equals("제목")) {
			System.out.println("새로운 제목을 입력하세요");
			String title = sc.nextLine();	
			String query="update notice set 제목='"+title+"',작성일자=sysdate where 번호 ='"+num+"'";
			int rs = stmt.executeUpdate(query);
			if(rs==1) {
				
				System.out.println(num+"번 공지가 수정되었습니다");
			}	
		
		}else if(c.equals("내용")) {
			System.out.println("새로운 제목을 입력하세요");
			String contents = sc.nextLine();	
			String query="update notice set 내용='"+contents+"',작성일자=sysdate where 번호 ='"+num+"'";
			int rs = stmt.executeUpdate(query);
			if(rs==1) {
				
				System.out.println(num+"번 공지가 수정되었습니다");
			}	
		}
		
		
					
	}
	
	public void insert() throws Exception {
		
		
		Connection cnn = dbConnect();
		
		String query="insert into notice values(?,?,?,'admin',sysdate) ";
		PreparedStatement pstmt =cnn.prepareStatement(query);
			
		System.out.println("제목을 입력하세요");
		String title =sc.nextLine();
		
		System.out.println("내용을 입력하세요");
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
	
		System.out.println("삭제할 공지의 번호를 입력하세요");
		int num =sc.nextInt();
		String query="delete from notice where 번호=?";
		
		PreparedStatement pstmt = cnn.prepareStatement(query);
		pstmt.setInt(1, num);
		
		int rs = pstmt.executeUpdate();				
		if(rs==1) {
			System.out.println(num+"번 공지가 삭제되었습니다");
		}
	}
	
	public void find() throws Exception {
		Connection cnn = dbConnect();

        check();
		
		System.out.println("조회할 공지의 번호를 입력하세요");
		int num = sc.nextInt();
		String query = "select * from notice where 번호=?";
		
		PreparedStatement pstmt = cnn.prepareStatement(query);
		pstmt.setInt(1,num);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			
			InformVO vo = new InformVO();
			
			
			vo.setTitle(rs.getString("제목"));
			vo.setContents(rs.getString("내용"));
			
			vo.setDate(rs.getString("작성일자"));
			
			System.out.println("제목: "+vo.getTitle()+"\n내용: "+vo.getContents()+"\n작성일자: "+vo.getDate());
		}
		
	}
	
	public void check() throws Exception {
		
		Connection cnn = dbConnect();
		Statement stmt = cnn.createStatement();
		String query = "select * from notice order by 번호";
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()) {
			InformVO vo = new InformVO();
			
			vo.setNum(rs.getString("번호"));
			vo.setTitle(rs.getString("제목"));
			
			System.out.println(vo.getNum()+" "+vo.getTitle());
		}
	}
	
	public int getNextNumber() throws Exception {
		
		String query = "(select max(번호)+1 from notice )";
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
