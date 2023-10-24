package elements;

import java.sql.*;

public class Reader extends User {
    private int exp;//等级
    private int credit;//余额
    private int level;//废弃

    @Override
    public int getExp() { return exp; }
    @Override
    public int getCredit() { return credit; }

    @Override
    public void setExp(int _exp) {
        this.exp = exp;
    }

    @Override
    public void setCredit(int cre) {
        credit=cre;
    }



    public Reader(int _id, String pwd, String nick, int _exp, int cre){
        super();
        id=_id; password=pwd; nickname=nick; exp=_exp; credit=cre; level=Rule.getLevel(exp);
    }

    public static int sqlCode=0;//为0则没有错误
    public static Reader login(String nick_or_id,String pw)
    //返回Reader对象正确登录 返回null账户或密码错误或数据库错误 具体错误原因见sqlCode
    {
        sqlCode=0;
        Connection conn=null;
        String stmt;
        ResultSet rs=null;
        Reader reader=null;
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
            stmt="select userid,password,nickname,exp,credit from userinfo where admin = 0 and userid=? and password=?";
        } catch (NumberFormatException e) {
            input_id = -1;
            stmt="select userid,password,nickname,exp,credit from userinfo where admin = 0 and nickname=? and password=?";
        }

        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            if(input_id==-1){
                pstmt.setString(1,nick_or_id);
            }else{
                pstmt.setInt(1,input_id.intValue());
            }
            pstmt.setString(2,pw);
            rs=pstmt.executeQuery();
            if(rs.next()){
                reader=new Reader(rs.getInt("userid"),rs.getString("password"),
                        rs.getString("nickname"),rs.getInt("exp"),
                        rs.getInt("credit"));
            }
            else    return null;

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        return reader;
    }
    public static int nextUserID()//返回-1出错
    {
        sqlCode=0;
        Connection conn=null;
        String sql;
        ResultSet rs=null;
        int result=-1;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return -1;
        }
        sql="select count(userid) from userinfo";
        try {
            Statement stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            if(rs.next())
                result=rs.getInt(1);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return -1;
        }
        return result+1;
    }
    public static Reader register(String nickname,String password,int GivenGrade,int balance)//GivenGrade 注册时给定的等级 balance给定的账户余额
    //返回Reader对象正确登录 返回null账户或密码错误或数据库错误 具体错误原因见sqlCode
    {
        sqlCode=0;
        int userID=0;
        Connection conn=null;
        String stmt;
        ResultSet rs=null;
        Reader reader=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        stmt="insert into userinfo (password,nickname,exp,credit,admin) " +
                "values ( ?, ?, ?, ?, 0)";
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            pstmt.setString(1,password);
            pstmt.setString(2,nickname);
            pstmt.setInt(3,GivenGrade);
            pstmt.setInt(4,balance);
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
        return new Reader(userID,password,nickname,GivenGrade,balance);
    }

    public static Reader verify(String NickorID){
        sqlCode=0;
        Connection conn=null;
        String stmt;
        ResultSet rs=null;
        Reader reader=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        System.out.println("enter:"+NickorID);
        Integer input_id=0;
        try{
            input_id=Integer.parseInt(NickorID);
            stmt="select * from userinfo where admin = 0 and userid=?";
            System.out.println("using userid:"+input_id);
        } catch (NumberFormatException e) {
            input_id = -1;
            stmt="select * from userinfo where admin = 0 and nickname=?";
            System.out.println("using nickname");
        }
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            if(input_id==-1){
                pstmt.setString(1,NickorID);
            }else{
                pstmt.setInt(1,input_id.intValue());
            }
            System.out.println(1);
            rs=pstmt.executeQuery();
            if(rs.next())
            {
                reader=new Reader(rs.getInt("userid"),rs.getString("password"),
                        rs.getString("nickname"),rs.getInt("exp"),
                        rs.getInt("credit"));
                System.out.println(2);
            }
            else reader= null;
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        return reader;
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
            pstmt.setInt(3,this.exp);
            pstmt.setInt(4,this.credit);
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
