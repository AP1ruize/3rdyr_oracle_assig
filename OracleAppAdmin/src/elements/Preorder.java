package elements;

import Logic.LendAndReturn;
import Runtime.*;
import com.sun.jdi.VMDisconnectedException;

import java.sql.*;
import java.util.Vector;

public class Preorder {
    private int uid;
    private int bid;
    private Timestamp time;

    public void readInfo(int _uid,int _bid,Timestamp ts){ uid=_uid; bid=_bid; time=ts;}
    public Preorder(){ readInfo(0,0,null);}
    public Preorder(int _uid,int _bid,Timestamp ts){ readInfo(_uid, _bid, ts);}



    static public int sqlCode=0;
    static public Preorder getPreorder(Integer userid,Integer bookid){
        sqlCode=0;
        Connection conn=null;
        ResultSet rs=null;
        Preorder retnValue=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        String stmt="SELECT * FROM STOCK WHERE BOOKID=? and userid=?";
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            pstmt.setInt(1,bookid);
            pstmt.setInt(2,userid);
            rs=pstmt.executeQuery();
            if(rs.next()){
                retnValue=new Preorder(rs.getInt("userid"),rs.getInt("bookid"),
                        rs.getTimestamp("time"));

            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        return retnValue;
    }
    static public boolean cash(Integer userid,Integer bookid,Integer duration){
//        int result=new LendAndReturn().getpreorderbook(new ReaderLoginState(userid),
//                new BookInfo(bookid),duration);
//        if(result==0)
//            return true;
//        else{
//            sqlCode=result;
//            return false;
//        }

        sqlCode=0;
        Connection conn=null;
        ResultSet rs=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        String sql="select * from loan where userid=? and bookid=?";
        try {
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,userid);
            pstmt.setInt(2,bookid);
            rs=pstmt.executeQuery();
            if(rs.next()){//检查是否已借阅
                rs.close();
                pstmt.close();
                conn.close();
                return false;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }

        sql="select remain from stock where bookid=?";
        try {
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,bookid);
            rs=pstmt.executeQuery();
            if(rs.next()){//检查是否有库存
                if(rs.getInt("remain")<=0){
                    rs.close();
                    pstmt.close();
                    conn.close();
                    return false;
                }
            }
            else{
                rs.close();
                pstmt.close();
                conn.close();
                return false;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }

        sql="delete from preorder where bookid=? and userid=?";
        try {
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,bookid);
            pstmt.setInt(2,userid);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }


        int rV=LendAndReturn.borrowbook(new ReaderLoginState(userid),
                new BookInfo(bookid),duration);
        if(rV==0)
            return true;
        sqlCode=rV;
        return false;
    }
    static public boolean makePreorder(Preorder po){
        sqlCode=0;
        Connection conn=null;
        ResultSet rs=null;
        int result=0;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        String stmt="SELECT * FROM preorder WHERE BOOKID=? and userid=?";
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            pstmt.setInt(1,po.getBid());
            pstmt.setInt(2,po.getUid());
            rs=pstmt.executeQuery();
            if(rs.next()){//检查是否已经预定过
                rs.close();
                pstmt.close();
                conn.close();
                return false;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }

        stmt="SELECT * FROM userinfo WHERE userid=?";
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            pstmt.setInt(1,po.getUid());
            rs=pstmt.executeQuery();
            if(!rs.next()){//检查是否有此用户
                rs.close();
                pstmt.close();
                conn.close();
                return false;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }

        stmt="UPDATE STOCK SET REMAIN = REMAIN-1 WHERE bookid = ? ";//更新库存
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            pstmt.setInt(1,po.getUid());
            result=pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }

        stmt="insert into preorder (userid,bookid,time) values (?, ?, ?)";
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            pstmt.setInt(1,po.getUid());
            pstmt.setInt(2,po.getBid());
            pstmt.setTimestamp((2+1),po.getTime());
            result=pstmt.executeUpdate();
            if(result!=1){
                pstmt.close();
                conn.close();
                return false;
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        return true;
    }
    static public boolean makePreorder(Integer userid,Integer bookid){
        return makePreorder(new Preorder(userid,bookid,new Timestamp(System.currentTimeMillis())));
    }
    static public boolean delete(Preorder po){
        return delete(po.uid,po.bid);
    }
    static public boolean delete(Integer userid,Integer bookid){
        int result=new LendAndReturn().delPreorder(new ReaderLoginState(userid),
                new BookInfo(bookid),null);
        if(result==1)
            return true;
        sqlCode=result;
        return false;
    }
    static public Vector<String> colName(){
        Vector<String> rV=new Vector<>();
        rV.add("用户id");
        rV.add("图书id");
        rV.add("书名");
        rV.add("借阅时间");
        return rV;
    }
    static public Vector<Vector> search(Integer userid,Integer bookid,Timestamp tsu,Timestamp tsl,boolean desc){
        sqlCode=0;
        Connection conn=null;
        ResultSet rs=null;
        Vector<Vector> rV=new Vector<>();
        Vector<String> tpl=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        String sql="select userid,po.bookid,bookname,time from preorder po left join bookinfo bi on po.bookid=bi.bookid ";
        String suffix="";
        boolean hasSuf=false;
        if(userid!=null){
            suffix+="where ";
            suffix+=("userid="+userid);
            hasSuf=true;
        }
        if(bookid!=null){
            if(hasSuf)
                suffix+=" and ";
            else suffix+="where ";
            suffix+=("po.bookid="+bookid);
            hasSuf=true;
        }
        if(tsu!=null){
            if(hasSuf)
                suffix+=" and ";
            else suffix+="where ";
            suffix+=("time<="+ SearchType4NM.toSqlFormat(tsu));
            hasSuf=true;
        }
        if(tsl!=null){
            if(hasSuf)
                suffix+=" and ";
            else suffix+="where ";
            suffix+=("time>="+ SearchType4NM.toSqlFormat(tsl));
            hasSuf=true;
        }
        suffix+=" order by time";
        if(desc)
            suffix+=" desc";
        sql+=suffix;
        System.out.println("Preorder.search:"+sql);
        try {
            Statement stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            while (rs.next()){
                tpl=new Vector<>();
                tpl.add(((Integer)rs.getInt("userid")).toString());
                tpl.add(((Integer)rs.getInt("bookid")).toString());
                tpl.add(rs.getString("bookname"));
                tpl.add(rs.getTimestamp("time").toString());
                rV.add(tpl);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        return rV;
    }

    public int getUid() {
        return uid;
    }

    public int getBid() {
        return bid;
    }

    public Timestamp getTime() {
        return time;
    }
}
