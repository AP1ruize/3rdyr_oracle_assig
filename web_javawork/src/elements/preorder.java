package elements;

import java.sql.Timestamp;

public class preorder {      
	protected int userid;
	protected int bookid;
	protected Timestamp time;
	public preorder(int u,int b,Timestamp time) {
		this.userid=u;
		this.bookid=b;
		this.time=time;
	}
	public int getUserid() {
		return userid;
	}
	public int getBookid() {
		return bookid;
	}
	public Timestamp getTime() {
		return time;
	}
	
}
