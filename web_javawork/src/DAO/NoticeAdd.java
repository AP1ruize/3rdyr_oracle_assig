package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import elements.DBConnection;
import elements.DBConnection;

public class NoticeAdd 
{
	Connection conn = null;
	PreparedStatement stmt = null;
	public void add() 
	{
		String sql = "INSERT INTO NOTICE VALUES(?,?,?,?,?,?)";
		try 
		{
			conn =  DBConnection.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 5518);
			String dateStr="2021-06-22";
			SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd"); 
			java.util.Date date=sdf_date.parse(dateStr);
			stmt.setDate(2, new java.sql.Date(date.getTime()));
			String timeStr="2021-06-22 05:34";
			SimpleDateFormat sdf_time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date time=sdf_time.parse(timeStr);
			stmt.setTimestamp(3, new Timestamp(time.getTime()));
			stmt.setString(4, "标题标题标题5555");
			stmt.setString(5, "内容内容568876757865");
			stmt.setInt(6, 1);
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
		NoticeAdd addD=new NoticeAdd();
		addD.add();
	}
}
