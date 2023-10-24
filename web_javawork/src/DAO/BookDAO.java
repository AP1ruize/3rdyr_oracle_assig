package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import entity.BookInfo;
import elements.DBConnection;

public class BookDAO
{
	public void collect(int usrid,int bookid) {   //添加收藏
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		boolean flag=true;
		try {
			conn = DBConnection.getConn();
			String sql = "INSERT INTO FAVORITE VALUES(?,?,?)"; // SQL语句
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,usrid);
			stmt.setInt(2,bookid);
			stmt.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
			stmt.executeUpdate();
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public int iscollect(int usrid,int bookid) {   //判断是否已收藏
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		boolean flag=true;
		try {
			conn = DBConnection.getConn();
			String sql2="select * from FAVORITE where USERID=? AND BOOKID=?";
			stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1,usrid);
			stmt2.setInt(2,bookid);
			rs=stmt2.executeQuery();
			if(rs.next())
			{
				return 1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	public ArrayList<BookInfo> getBookAll(String input)

	{
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
			
		ArrayList<BookInfo> list=new ArrayList<BookInfo>();
		try 
		{
			conn =DBConnection.getConn();
			String sql="select * from BOOKINFO where BOOKNAME like '%'||?||'%' "
					+ "OR AUTHOR like '%'||?||'%'";//这里写图书的信息表
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, input);
			stmt.setString(2, input);
			rs=stmt.executeQuery();
			while(rs.next())
			{
				BookInfo bookInfo=new BookInfo();
				bookInfo.setBookID(rs.getInt("bookID"));
				bookInfo.setBookName(rs.getString("bookName"));
				bookInfo.setAuthor(rs.getString("author"));
				bookInfo.setPress(rs.getString("press"));
				bookInfo.setType(rs.getString("type"));
				bookInfo.setIntro(rs.getString("intro"));
				bookInfo.setPic(rs.getBlob("pic"));
				
				list.add(bookInfo);
			}
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if(rs!=null)
			{
				try
				{
					rs.close();
					rs=null;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
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
	public ArrayList<BookInfo> getBookNew()
	{
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
			
		ArrayList<BookInfo> list=new ArrayList<BookInfo>();
		try 
		{
			conn =DBConnection.getConn();
			String sql="select BOOKID,BOOKNAME,AUTHOR,PIC from "
					+ "(select * from BOOKINFO order by rownum DESC) where rownum<=3";
			//这条sql语句表示取出相应表的最后9行数据
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();
			while(rs.next())
			{
				BookInfo bookInfo=new BookInfo();
				bookInfo.setBookID(rs.getInt("bookID"));
				bookInfo.setBookName(rs.getString("bookName"));
				bookInfo.setAuthor(rs.getString("author"));
				//bookInfo.setPress(rs.getString("press"));
				//bookInfo.setType(rs.getString("type"));
				//bookInfo.setIntro(rs.getString("intro"));
				bookInfo.setPic(rs.getBlob("pic"));
				
				list.add(bookInfo);
			}
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if(rs!=null)
			{
				try
				{
					rs.close();
					rs=null;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
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
	public ArrayList<BookInfo> getBookFuzzy(String bookname,String author,String type)

	{
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
			
		ArrayList<BookInfo> bookList=new ArrayList<BookInfo>();
		try 
		{
			conn =DBConnection.getConn();
			String sql="select * from BOOKINFO where 1=1 ";//1=1恒为真，为了之后拼接sql语句做准备
			StringBuffer stb=new StringBuffer(sql);
			ArrayList<Object> sqlList=new ArrayList<>();
			//拼接sql语句
			if(bookname!=null&&bookname!="")
			{
				stb.append(" and BOOKNAME like '%'||?||'%' ");
				sqlList.add(bookname);
			}
			if(author!=null&&author!="")
			{
				stb.append(" and AUTHOR like '%'||?||'%' ");
				sqlList.add(author);
			}
			if(type!=null&&type!="")
			{
				stb.append(" and TYPE = ? ");
				sqlList.add(type);
			}
			stmt=conn.prepareStatement(stb.toString());
			//给问号赋值
			for(int i=1;i<=sqlList.size();i++)
			{
				stmt.setObject(i, sqlList.get(i-1));
			}
			rs=stmt.executeQuery();
			while(rs.next())
			{
				BookInfo bookInfo=new BookInfo();
				bookInfo.setBookID(rs.getInt("bookID"));
				bookInfo.setBookName(rs.getString("bookName"));
				bookInfo.setAuthor(rs.getString("author"));
				bookInfo.setPress(rs.getString("press"));
				bookInfo.setType(rs.getString("type"));
				bookInfo.setIntro(rs.getString("intro"));
				bookInfo.setPic(rs.getBlob("pic"));
				
				bookList.add(bookInfo);
			}
			return bookList;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if(rs!=null)
			{
				try
				{
					rs.close();
					rs=null;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
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
}
