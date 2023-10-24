package Logic;

import elements.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import Runtime.BookInfo;
import Runtime.ReaderLoginState;

import javax.swing.*;

public class SearchInfo {
    static public int sqlCode=0;

    static public Vector<Vector> searchBooks(String keyword, SearchType type){
        sqlCode=0;
        Connection conn=null;
        String stmt;
        ResultSet rs=null;
        Vector<Vector> results=null;
        try {
            conn= DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        stmt="select * from bookinfo where "+type.attrName()+" like '%"+keyword+"%'";
        System.out.println("SearchInfo.searchBooks:"+stmt);
        try{
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            rs=pstmt.executeQuery();
            results=new Vector<Vector>();
            while(rs.next()){
                if(rs.getBlob("PIC")!=null) {
                    results.add(new BookInfo(rs.getInt("BOOKID"), rs.getString("BOOKNAME"),
                            rs.getString("AUTHOR"), rs.getString("TYPE"),
                            new ImageIcon(rs.getBlob("PIC").getBinaryStream().readAllBytes())).toVec());
                }
                else{
                    results.add(new BookInfo(rs.getInt("BOOKID"), rs.getString("BOOKNAME"),
                            rs.getString("AUTHOR"), rs.getString("TYPE"),
                            new ImageIcon()).toVec());
                }
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return results;
    }

    static public Vector<Vector> searchUsersAdv(Integer idu,Integer idl,String nick,Integer cl,Integer blc){
        if(idu!=null && idl!=null)
        {
            if(idu<idl){
                Integer ext=idu;
                idu=idl;
                idl=ext;
            }
        }
        sqlCode=0;
        Connection conn=null;
        String stmt;
        ResultSet rs=null;
        Vector<Vector> results=null;
        try {
            conn= DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        stmt="select * from userinfo where ";
        String suffix="";
        boolean hasSuf=false;
        if(idu!=null){
            suffix+= (SearchType4UM.IDU.attrName()+idu.toString());
            hasSuf=true;
        }
        if(idl!=null){
            if(hasSuf)
                suffix+=" and ";
            suffix+= (SearchType4UM.IDL.attrName()+idl.toString());
            hasSuf=true;
        }
        if(nick!=null){
            if(hasSuf)
                suffix+=" and ";
            suffix+= (SearchType4UM.NICK.attrName()+" like '%"+nick+"%'");
            hasSuf=true;
        }
        if(cl!=null){
            if(hasSuf)
                suffix+=" and ";
            suffix+= (SearchType4UM.CL.attrName()+"="+cl.toString());
            hasSuf=true;
        }
        if(blc!=null){
            if(hasSuf)
                suffix+=" and ";
            suffix+= (SearchType4UM.BLC.attrName()+"="+blc.toString());
            hasSuf=true;
        }
        if(!hasSuf)
            return null;
        suffix+=" order by userid";
        stmt+=suffix;
        System.out.println("SearchInfo.searchUsersAdv:"+stmt);

        try{
            Statement pstmt =conn.createStatement();
            rs=pstmt.executeQuery(stmt);
            results=new Vector<Vector>();
            while(rs.next()){
                results.add(new ReaderLoginState(rs.getInt("userid"),rs.getString("nickname"),
                        rs.getInt("exp"),rs.getInt("credit")).toVec());
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        return results;
    }

    static public Vector<Vector> searchLoanedBooks(int uid){
        sqlCode=0;
        Connection conn=null;
        ResultSet rs=null;
        Vector<Vector> reV=new Vector<>();
        Vector<String> tpl=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        String stmt="select * from bookinfo bi right join (select * from loan where userid=?) a on bi.bookid=a.bookid";
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            pstmt.setInt(1,uid);
            rs=pstmt.executeQuery();
            while(rs.next()){
                tpl=new Vector<>();
                tpl.add(((Integer)rs.getInt("userid")).toString());
                tpl.add(((Integer)rs.getInt("bookid")).toString());
                tpl.add(rs.getString("bookname"));
                tpl.add(((Integer)rs.getInt("amount")).toString());
                tpl.add(rs.getTimestamp("loantime").toString());
                tpl.add(((Integer)rs.getInt("duration")).toString());
                reV.add(tpl);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode= e.getErrorCode();
            return null;
        }
        return reV;
    }
    static public Vector<String> loanedBookColName(){
        Vector<String> colName=new Vector<String>();
        colName.add("用户id");
        colName.add("图书id");
        colName.add("书名");
        colName.add("数量");
        colName.add("借阅时间");
        colName.add("借阅期");
        return colName;
    }
    static public int Renewable(Vector<String> vec){
        return Integer.parseInt(vec.get(5));
    }

}
