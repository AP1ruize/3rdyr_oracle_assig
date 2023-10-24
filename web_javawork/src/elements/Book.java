package elements;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import oracle.sql.BLOB;
import java.io.*;


public class Book {
    private int id;
    private String name;
    private String press;//出版社
    private String author;
    private String type;
    private String intro;
    private BLOB pic;
    
    public String getIntro() {
		return intro;
	}
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPress() {
		return press;
	}

	public String getAuthor() {
		return author;
	}

	public String getType() {
		return type;
	}
	public BLOB getPic() {
		return pic;
	}
	
	
	public void getBookInfo(int id) {                    //favorite.jsp调用  获取图书信息
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConn();
			String sql = "select * from BOOKINFO where BOOKID=?"; // SQL语句
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				this.id=id;
				this.name=rs.getString("BOOKNAME");
				this.press=rs.getString("PRESS");
				this.author=rs.getString("AUTHOR");
				this.type=rs.getString("TYPE");
				this.intro=rs.getString("INTRO");
				this.pic=(BLOB)rs.getBlob("pic");
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		} 
    }
}
