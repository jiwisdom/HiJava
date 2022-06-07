package DBVO;

public class BoardVO {

	int boardNum;
	String title;
	String contents;
	String Boardwriter;
	String date;
	
	int replyNum;
	int replyCount;
	String commentes;
	String RepltWriter;
	
	public int getBoardNum() {
		return boardNum;
	}
	
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
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

	public String getBoardwriter() {
		return Boardwriter;
	}
	
	public void setBoardwriter(String boardwriter) {
		Boardwriter = boardwriter;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getReplyNum() {
		return replyNum;
	}
	
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	
	public String getCommentes() {
		return commentes;
	}
	
	public void setCommentes(String commentes) {
		this.commentes = commentes;
	}
	
	public String getRepltWriter() {
		return RepltWriter;
	}
	
	public void setRepltWriter(String repltWriter) {
		RepltWriter = repltWriter;
	}

	@Override
	public String toString() {
		return "게시글 번호 " + boardNum + " | 제목 " + title +"(" +replyCount+") | 작성자 " + Boardwriter + " | 작성시간" + date
				 ;
	}
	
	
}
