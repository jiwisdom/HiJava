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
			System.out.println("�α��εǼ̽��ϴ�");
			flag=1;
		}else {
			System.out.println("������ id�� �ƴմϴ�");
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
		return "��ȣ :" + num + " ����:" + title + " ����:" + contents + " �ۼ���:" + writer + " �ۼ�����:"
				+ date;
	}
	

}

