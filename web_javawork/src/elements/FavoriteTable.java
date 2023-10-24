package elements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class FavoriteTable {
    protected Vector<FavoriteTuple> tuples;
    protected String tableName;             //�ղؼ�����
    
    public FavoriteTable(){
        tableName="FAVORITE";
        tuples=new Vector<FavoriteTuple>();
    }
    public void cancelFavorite() {}
    
    public Vector<FavoriteTuple> getTuples(){      //favorite.jsp����
    	return tuples;
    }
    public String getTablename() {
    	return tableName;
    }
    public void getFavorite(int id)            //favorite.jsp����;��ȡ���ղؼе��ղؼ�¼
    {
    	Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConn();
			String sql = "select * from FAVORITE where USERID=?"; // SQL���
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				FavoriteTuple tpl=new FavoriteTuple(rs.getInt("userid"),rs.getInt("bookid"),rs.getTimestamp("time"));
				tuples.add(tpl);
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    public void cancel(int bookid,int usrid)         //favorite.jsp����;ȡ��bookid�����ղ�
    {
    	Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConn();
			String sql = "delete from FAVORITE where BOOKID=? AND USERID=?"; // SQL���
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,bookid);
			stmt.setInt(2,usrid);
			stmt.executeUpdate();
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    /*
    public void readInfo(){                      
        Connection conn=null;
        ResultSet rs=null;
        FavoriteTuple tpl=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        String stmt="SELECT * FROM FAVORITE";
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            rs=pstmt.executeQuery();
            while(rs.next()) {
                tpl=new FavoriteTuple(rs.getInt("userid"),
                        rs.getInt("bookid"),
                        rs.getTimestamp("time"));
                this.tuples.add(tpl);
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */
}
