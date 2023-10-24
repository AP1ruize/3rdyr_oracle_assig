package elements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class User {
    protected int id;
    protected String password;
    protected String nickname;
    public User(String nn,String pw){nickname=nn;password=pw;}

    protected User() {
    }

    protected void update_self(int i,String pw,String nn){
        id=i;password=pw;nickname=nn;
    }

    public int modifyInfo(String nn,String pw){
        Connection conn=null;
        int rows=0;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try{
            String stmt="UPDATE USERINFO SET NICKNAME = ?, PASSWORD = ? WHERE USERID = ?";
            PreparedStatement updStmt=conn.prepareStatement(stmt);
            updStmt.setString(1,nn);
            updStmt.setString(2,pw);
            updStmt.setInt(3,this.id);
            rows=updStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        update_self(this.id,pw,nn);
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        return 0;
    }


}
