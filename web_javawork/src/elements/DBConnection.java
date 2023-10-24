package elements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection conn=null;// ����һ�����ݿ�����
	private static final String url = "jdbc:oracle:" + "thin:@127.0.0.1:1521:XE";
	private static final String user = "ruizesheng";// �û���
	private static final String password = "password";// �㰲װʱѡ���õ�����
	static 
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");// ����Oracle��������
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
