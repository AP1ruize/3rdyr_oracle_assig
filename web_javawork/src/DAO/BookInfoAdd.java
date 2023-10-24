package DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

import elements.DBConnection;
import elements.DBConnection;

public class BookInfoAdd {
	Connection conn = null;
	PreparedStatement stmt = null;
	public void add() 
	{
		String sql = "INSERT INTO BOOKINFO  VALUES(?,?,?,?,?,?,?)";
		try 
		{
			conn = DBConnection.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 2);
			stmt.setString(2, "黑暗中的笑声");
			stmt.setString(3, "上海译文出版社");
			stmt.setString(4, "纳博科夫");
			stmt.setString(5, "小说");
			stmt.setString(6, "无");
			File file=new File("C:\\Users\\poi\\Desktop\\系统开发\\image\\黑暗中的笑声.jpg");
			java.io.BufferedInputStream imageInput = new java.io.BufferedInputStream(
           	     new java.io.FileInputStream(file));
			stmt.setBinaryStream(7, imageInput,(int) file.length());
			int rs = stmt.executeUpdate();
			if(rs != 0)
			{
				System.out.println("用户信息添加成功！");
			
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(stmt!=null)
			{
				try
				{
					stmt.close();
					stmt=null;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		BookInfoAdd addD=new BookInfoAdd();
		addD.add();
	}
}
