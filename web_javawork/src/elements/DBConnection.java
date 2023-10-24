package elements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection conn=null;// 创建一个数据库连接
	private static final String url = "jdbc:oracle:" + "thin:@127.0.0.1:1521:XE";
	private static final String user = "ruizesheng";// 用户名
	private static final String password = "password";// 你安装时选设置的密码
	static 
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
    public static Connection getConn() throws SQLException {
    	if(conn==null)
		{
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		}
		return conn;

    }

}
