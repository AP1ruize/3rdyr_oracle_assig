package DAO;

import java.sql.Connection;
import java.sql.Statement;

import elements.DBConnection;

public class InfoAdd {
	Connection conn = null;
	Statement stmt = null;
	public void add() 
	{
		String sql = "insert into info(user_name,u_password) values('D','246')";
		try 
		{
			conn =  DBConnection.getConn();
			stmt=conn.createStatement();
			int rs = stmt.executeUpdate(sql);
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
		InfoAdd addD=new InfoAdd();
		addD.add();
	}
}