package elements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class LoanTable {
    private Vector<LoanTuple> tuples;
    public LoanTable() {
    	tuples=new Vector<LoanTuple>();
    }
    public Vector<LoanTuple> getTuples(){
    	return tuples;
    }
    public void getLoaninfo(int id) {
    	Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConn();
			String sql = "select * from LOAN where USERID=?"; // SQLÓï¾ä
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				LoanTuple tpl=new LoanTuple(rs.getInt("userid"),rs.getInt("bookid"),rs.getInt("amount"),rs.getTimestamp("loantime"),rs.getInt("duration"));
				tuples.add(tpl);
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    public void renew(int bookid,int usrid) {         //Ðø½è
    	Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConn();
			String sql = "update LOAN set DURATION=60 where BOOKID=? AND USERID=?"; // SQLÓï¾ä
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,bookid);
			stmt.setInt(2,usrid);
			stmt.executeUpdate();
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
}
