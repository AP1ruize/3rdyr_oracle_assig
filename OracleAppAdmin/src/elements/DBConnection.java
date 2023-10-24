package elements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConn() throws SQLException {
        String url="jdbc:oracle:thin:@127.0.0.1:1521:XE";
        Connection conn= DriverManager.getConnection(url,"ruizesheng","password");
        return conn;
    }
}
