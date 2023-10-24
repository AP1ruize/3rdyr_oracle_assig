package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import elements.DBConnection;
import entity.UserInfo;
import elements.DBConnection;

public class UserDAO {
	public ArrayList<UserInfo> getAllInfo()
	{
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		ArrayList<UserInfo> list=new ArrayList<UserInfo>();
		try 
		{
			conn =DBConnection.getConn();
			String sql="select * from USERINFO";
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();
			while(rs.next())
			{
				UserInfo userInfo=new UserInfo();
				userInfo.setUserID(rs.getInt("userID"));
				userInfo.setPassword(rs.getString("password"));
				list.add(userInfo);
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
	public UserInfo getLoginVerify(int userID,String password)
	{
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try 
		{
			conn =DBConnection.getConn();
			String sql="select * from USERINFO where USERID=? and PASSWORD=?";
			stmt=conn.prepareStatement(sql);
			stmt.setInt(1, userID);
			stmt.setString(2, password);
			rs=stmt.executeQuery();
			
			UserInfo userInfo=null;
			while(rs.next())
			{
				userInfo=new UserInfo();
				userInfo.setUserID(rs.getInt("userID"));
				userInfo.setPassword(rs.getString("password"));
			}
			return userInfo;
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
