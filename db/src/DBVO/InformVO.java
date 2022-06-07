package DBVO;


public class InformVO {


	private String num;
	private String title;
	private String contents;
	private String writer;
	private String date;
	
	
	private String id;	
	private String pw;
	private int flag;
	
	public int login(String id,String pw) {
		this.id=id;
		this.pw=pw;
		if(id.equals("admin")&&(pw.equals("1234"))){
			System.out.println("로그인되셨습니다");
			flag=1;
		}else {
			System.out.println("관리자 id가 아닙니다");
			flag=0;
		}
		return flag;
	}
		
	public String getNum() {
		return num;
	}
	public void setNum(String string) {
		this.num = string;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	
	


	@Override
	public String toString() {
		return "번호 :" + num + " 제목:" + title + " 내용:" + contents + " 작성자:" + writer + " 작성일자:"
				+ date;
	}
	

}

