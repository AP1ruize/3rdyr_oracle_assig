package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import elements.DBConnection;

public class UserInfoAdd
{
	Connection conn = null;
	PreparedStatement stmt = null;
	public void userAdd() 
	{
		String sql = "INSERT INTO USERINFO  VALUES(?,?,?,?,?,?)";
		try 
		{
			conn = DBConnection.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 123);
			stmt.setString(2, "wp");
			stmt.setString(3, "wp");
			stmt.setInt(4, 0);
			stmt.setInt(5, 0);
			stmt.setInt(6, 0);
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
		UserInfoAdd addD=new UserInfoAdd();
		addD.userAdd();
	}
}
