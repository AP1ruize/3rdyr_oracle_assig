package entity;

import java.sql.Blob;

public class BookInfo 
{
	private int  bookID;
	private String bookName;
	private String press;//³ö°æÉç
	private String author;
	private String type;
	private String intro;
	private Blob pic;
	
	
	
	public int getBookID() {
		return bookID;
	}
	public void setBookID(int  bookID) {
		this.bookID = bookID;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public Blob getPic() {
		return pic;
	}
	public void setPic(Blob pic) {
		this.pic = pic;
	}
}
