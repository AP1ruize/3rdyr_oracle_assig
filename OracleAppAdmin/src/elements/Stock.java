package elements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Stock {
    private int bookid;
    private int amount;
    private int remain;

    public Stock(){readInfo(0,0,0);}
    public Stock(int id,int amt,int rm){readInfo(id,amt,rm);}
    public void readInfo(int id,int amt,int rm){bookid=id; amount=amt; remain=rm;}

    static public int sqlCode=0;
    static public Stock getStock(int bid){
        sqlCode=0;
        Connection conn=null;
        ResultSet rs=null;
        Stock retnValue=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        String stmt="SELECT * FROM STOCK WHERE BOOKID="+bid;
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            rs=pstmt.executeQuery();
            while(rs.next()){
                retnValue=new Stock(rs.getInt("BOOKID"),rs.getInt("AMOUNT"),rs.getInt("REMAIN"));
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

    public int getBookid() {
        return bookid;
    }
    public int getAmount() {
        return amount;
    }
    public int getRemain() {
        return remain;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void setRemain(int remain) {
        this.remain = remain;
    }

    public boolean save(){
        sqlCode=0;
        Connection conn=null;
        String sqlStmt;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        sqlStmt="update stock set amount=?, remain=? where bookid=?";
        int retnValue=0;
        try {
            PreparedStatement pstmt=conn.prepareStatement(sqlStmt);
            pstmt.setInt(1,this.amount);
            pstmt.setInt(2,this.remain);
            pstmt.setInt(3,this.bookid);
            retnValue=pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        if(retnValue>=1)
            return true;
        return false;
    }
}
