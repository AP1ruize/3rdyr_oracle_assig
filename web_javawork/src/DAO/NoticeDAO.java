package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import entity.Notice;
import elements.DBConnection;

public class NoticeDAO 
{
	public ArrayList<Notice> getNotice()
	{
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
			
		ArrayList<Notice> list=new ArrayList<Notice>();
		try 
		{
			conn =DBConnection.getConn();
			String sql="select * from NOTICE where rownum<=10 and STATUS=1 "
					+ "order by EDITDATE";
			//这条sql语句表示按时间排序取出时间最晚的3条公告进行展示
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();
			while(rs.next())
			{
				Notice notice=new Notice();
				notice.setAdminID(rs.getInt("adminID"));
				notice.setEditDate(rs.getDate("editDate"));
				notice.setEditTime(rs.getTimestamp("editTime"));
				notice.setTitle(rs.getString("title"));
				notice.setText(rs.getString("text"));
				notice.setStatus(rs.getInt("status"));
				
				list.add(notice);
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
}
