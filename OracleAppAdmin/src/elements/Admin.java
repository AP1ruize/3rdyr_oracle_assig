package elements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User {

    public Admin(int _id,String nn,String pw){super(nn,pw);id=_id;}

    public static int sqlCode=0;//为0则没有错误
    public static Admin login(String nick_or_id,String pw)
    //返回Reader对象正确登录 返回null账户或密码错误或数据库错误 具体错误原因见sqlCode
    {
        sqlCode=0;
        Connection conn=null;
        String stmt;
        ResultSet rs=null;
        Admin admin=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        Integer input_id=0;
        try{
            input_id=Integer.parseInt(nick_or_id);
            //stmt="select userid,password,nickname,exp,credit from userinfo where admin = 1 and userid=? and password=?";
            stmt="select * from userinfo where admin = 1 and userid=? and password=?";
            //System.out.println("using userid");
        } catch (NumberFormatException e) {
            input_id = -1;
            //stmt="select userid,password,nickname,exp,credit from userinfo where admin = 1 and nickname=? and password=?";
            stmt="select * from userinfo where admin = 1 and nickname=? and password=?";
            //System.out.println("using nickname");
        }
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            if(input_id==-1){
                pstmt.setString(1,nick_or_id);
                //System.out.println(nick_or_id+" pw:"+pw);
            }else{
                pstmt.setInt(1,input_id.intValue());
                //System.out.println("id:"+input_id.intValue()+" pw:"+pw);
            }
            pstmt.setString(2,pw);
            rs=pstmt.executeQuery();

            if(rs.next()){
                admin=new Admin(rs.getInt("userid"),rs.getString("nickname"),
                        rs.getString("password"));
                //System.out.println("2");
            }
            else   admin= null;

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        return admin;
    }
    public static Admin register(String nickname,String password)
    //返回Reader对象正确登录 返回null账户或密码错误或数据库错误 具体错误原因见sqlCode
    {
        sqlCode=0;
        int userID=0;
        Connection conn=null;
        String stmt;
        ResultSet rs=null;
        Admin admin=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        stmt="insert into userinfo (password,nickname,exp,credit,admin) " +
                "values ( ?, ?, 0, 0, 1)";
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            pstmt.setString(1,password);
            pstmt.setString(2,nickname);
            pstmt.executeUpdate();
            stmt="select userid from userinfo where nickname = ? and password = ?";
            pstmt=conn.prepareStatement(stmt);
            pstmt.setString(2,password);
            pstmt.setString(1,nickname);
            rs=pstmt.executeQuery();
            if(rs.next())
                userID=rs.getInt("userid");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        return new Admin(userID,nickname,password);
    }

    @Override
    public int getExp() {
        return 0;
    }

    @Override
    public int getCredit() {
        return 0;
    }

    @Override
    public void setExp(int _exp) {

    }

    @Override
    public void setCredit(int cre) {

    }

    @Override
    public boolean modifyInfo() {
        sqlCode=0;
        Connection conn=null;
        String sqlStmt;
        Reader reader=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        sqlStmt="update userinfo set password=?, nickname=?, exp=?, credit=? where userid=?";
        int retnValue=0;
        try {
            PreparedStatement pstmt=conn.prepareStatement(sqlStmt);
            pstmt.setString(1,this.password);
            pstmt.setString(2,this.nickname);
            pstmt.setInt(3,this.getExp());
            pstmt.setInt(4,this.getCredit());
            pstmt.setInt(5,this.id);
            retnValue=pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        if(retnValue==1)
            return true;
        return false;
    }

}
