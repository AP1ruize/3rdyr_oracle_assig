package elements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import Runtime.ReaderLoginState;

public class LoanTable {
    private Vector<LoanTuple> tuples;
    public LoanTable(){tuples=new Vector<LoanTuple>();}
//    public int readInfo(int beginIndex){
//        Connection conn=null;
//        ResultSet rs=null;
//        LoanTuple tpl=null;
//        int rows;
//        if(beginIndex<=0)
//            beginIndex=1;
//        try {
//            conn=DBConnection.getConn();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return e.getErrorCode();
//        }
//        String stmt="SELECT * FROM LOAN";
//        try {
//            PreparedStatement pstmt=conn.prepareStatement(stmt,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
//            rs=pstmt.executeQuery();
//            rows=rs.getRow();
//            for(int k = beginIndex; k<=(beginIndex+Rule.maxBuffTuples()) && rs.absolute(beginIndex); k++){
//                tpl=new LoanTuple(rs.getInt("userid"),
//                        rs.getInt("bookid"),
//                        rs.getInt("amount"),
//                        rs.getTimestamp("loantime"),
//                        rs.getInt("duration"));
//                tuples.add(tpl);
//            }
//            rs.close();
//            pstmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return e.getErrorCode();
//        }
//        return rows;
//    }

    public int readInfo(int uid){
        Connection conn=null;
        ResultSet rs=null;
        LoanTuple tpl=null;
        int rows;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        String stmt="SELECT * FROM LOAN WHERE USERID=?";
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            pstmt.setInt(1,uid);
            rs=pstmt.executeQuery();
            rows=rs.getRow();
            while(rs.next()){
                tpl=new LoanTuple(rs.getInt("userid"),
                        rs.getInt("bookid"),
                        rs.getInt("amount"),
                        rs.getTimestamp("loantime"),
                        rs.getInt("duration"));
                tuples.add(tpl);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        return rows;
    }

    public Vector<LoanTuple> getTuples() {
        return tuples;
    }
}
