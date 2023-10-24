package DAO;
import elements.DBConnection;
import elements.FavoriteTuple;
import elements.preorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
public class Preorder {
	protected Vector<preorder> orders;
	public Vector<preorder> getOrders(){
		return this.orders;
	}
	public Preorder() {
		orders=new Vector<preorder>();
	}
	public int getprenum(int bookid)    //��ȡĳ������ԤԼ����
	{
		int num=0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
        try{
        	conn = DBConnection.getConn();
        	String sql= "select * from preorder WHERE  BOOKID=?";
        	stmt=conn.prepareStatement(sql);
            stmt.setInt(1,bookid);
            rs = stmt.executeQuery();
            while(rs.next())
            {
            	num++;
            }    
        } catch (Exception ex) {
			ex.printStackTrace();
		}
        return num;
	}
	public int booknum(int bookid) //��ȡͼ�����п������
	{
		int num=-1;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
        try{
        	conn = DBConnection.getConn();
        	String sql="select * from stock where bookid=? ";
        	stmt=conn.prepareStatement(sql);
        	stmt.setInt(1,bookid);
            rs = stmt.executeQuery();
            if(rs.next())
            {
            	num=rs.getInt("remain");
            }   
        } catch (Exception ex) {
			ex.printStackTrace();
		}
        return num;
	}
	public int isorder(int usrid,int bookid) {         //�ж��ܷ�ԤԼ
		int numbook=this.booknum(bookid);
		int numpers=this.getprenum(bookid);
		if(numbook-numpers==0) {
			return 0;
		}
		try{
				 	Connection conn = null;
					PreparedStatement stmt = null;
					PreparedStatement stmt2 = null;
					ResultSet rs = null;
		        	conn = DBConnection.getConn();
					String sql2="select * from PREORDER where USERID=? AND BOOKID=?";
					stmt2 = conn.prepareStatement(sql2);
					stmt2.setInt(1,usrid);
					stmt2.setInt(2,bookid);
					rs=stmt2.executeQuery();
					if(rs.next())
					{
						return 2;   //��ԤԼ�Ͳ����ٴ�ԤԼ
					}
		} catch (Exception ex) {
					ex.printStackTrace();
		}
		return 1;
	}
	public void order(int usrid,int bookid) {         //ԤԼĳ��
		try {
		Connection conn = null;
		conn = DBConnection.getConn();
		String sql = "INSERT INTO PREORDER VALUES(?,?,?)"; // SQL���
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,usrid);
		stmt.setInt(2,bookid);
		Timestamp currentstamp = new Timestamp(System.currentTimeMillis());//��ǰʱ���
		stmt.setTimestamp(3,currentstamp);
		stmt.executeUpdate();
		conn.commit();
		} catch (Exception ex) {
		ex.printStackTrace();
		}
	}
	public void getOrderinfo(int id) {               //���ԤԼ��Ϣ
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConn();
			String sql = "select * from PREORDER where USERID=?"; // SQL���
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				preorder order=new preorder(rs.getInt("userid"),rs.getInt("bookid"),rs.getTimestamp("time"));
				orders.add(order);
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void cancelorder(int usrid,int bookid) {     //ȡ��ԤԼ
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConn();
			String sql = "delete from PREORDER where BOOKID=? AND USERID=?"; // SQL���
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
