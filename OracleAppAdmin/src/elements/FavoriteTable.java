package elements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class FavoriteTable {
    protected Vector<FavoriteTuple> tuples;
    protected String tableName;
    public FavoriteTable(){
        tableName="FAVORITE";
    }
    public int readInfo(int beginIndex){
        Connection conn=null;
        ResultSet rs=null;
        FavoriteTuple tpl=null;
        int rows;
        if(beginIndex<=0)
            beginIndex=1;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        String stmt="SELECT * FROM "+tableName;
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            rs=pstmt.executeQuery();
            rows=rs.getRow();
            for(int k = beginIndex; k<(beginIndex+Rule.maxBuffTuples()) && rs.absolute(beginIndex); k++){
                tpl=new FavoriteTuple(rs.getInt("userid"),
                        rs.getInt("bookid"),
                        rs.getTimestamp("time"));
                tuples.add(tpl);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        return rows;
    }
}
