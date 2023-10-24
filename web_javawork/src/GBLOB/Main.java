package GBLOB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import elements.Book;
import elements.DBConnection;
import elements.FavoriteTable;
import elements.FavoriteTuple;
import elements.Reader;
import oracle.sql.BLOB;

import DAO.*;

public class Main {
	public static void main(String[] args){
		
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConn();
			/*插入书籍信息
			String sql = "INSERT INTO BOOKINFO  VALUES(101,?,?,?,?,?,?)"; // SQL语句
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "活着");
			stmt.setString(2, "北京十月文艺出版社");
			stmt.setString(3, "余华");
			stmt.setString(4, "社会小说");
			stmt.setString(5, "");
			File file=new File("D:\\mess\\101.jpg");
			java.io.BufferedInputStream imageInput = new java.io.BufferedInputStream(
           	     new java.io.FileInputStream(file));
			stmt.setBinaryStream(6, imageInput,(int) file.length());
			stmt.executeUpdate();
			
			String sql2 = "INSERT INTO BOOKINFO  VALUES(102,?,?,?,?,?,?)"; // SQL语句
			PreparedStatement stmt2 = null;
			stmt2 = conn.prepareStatement(sql2);
			stmt2.setString(1, "消失的13级台阶");
			stmt2.setString(2, "上海文艺出版社");
			stmt2.setString(3, "高野和明");
			stmt2.setString(4, "推理小说");
			stmt2.setString(5, "");
			File file2=new File("D:\\mess\\102.jpg");
			java.io.BufferedInputStream imageInput2 = new java.io.BufferedInputStream(
           	     new java.io.FileInputStream(file2));
			stmt2.setBinaryStream(6, imageInput2,(int) file2.length());
			stmt2.executeUpdate();
			

			String sql3 = "INSERT INTO BOOKINFO  VALUES(103,?,?,?,?,?,?)"; // SQL语句
			PreparedStatement stmt3 = null;
			stmt3 = conn.prepareStatement(sql3);
			stmt3.setString(1, "白夜行");
			stmt3.setString(2, "南海出版公司");
			stmt3.setString(3, "东野圭吾");
			stmt3.setString(4, "社会小说");
			stmt3.setString(5, "");
			File file3=new File("D:\\mess\\103.jpg");
			java.io.BufferedInputStream imageInput3 = new java.io.BufferedInputStream(
           	     new java.io.FileInputStream(file3));
			stmt3.setBinaryStream(6, imageInput3,(int) file3.length());
			stmt3.executeUpdate();
			
			String sql4 = "INSERT INTO BOOKINFO  VALUES(104,?,?,?,?,?,?)"; // SQL语句
			PreparedStatement stmt4 = null;
			stmt4 = conn.prepareStatement(sql4);
			stmt4.setString(1, "人间失格");
			stmt4.setString(2, "作家出版社");
			stmt4.setString(3, "太宰治");
			stmt4.setString(4, "社会小说");
			stmt4.setString(5, "");
			File file4=new File("D:\\mess\\104.jpg");
			java.io.BufferedInputStream imageInput4 = new java.io.BufferedInputStream(
           	     new java.io.FileInputStream(file4));
			stmt4.setBinaryStream(6, imageInput4,(int) file4.length());
			stmt4.executeUpdate();
			
			String sql5 = "INSERT INTO BOOKINFO  VALUES(105,?,?,?,?,?,?)"; // SQL语句
			PreparedStatement stmt5 = null;
			stmt5 = conn.prepareStatement(sql5);
			stmt5.setString(1, "霍乱时期的爱情");
			stmt5.setString(2, "南海出版公司");
			stmt5.setString(3, "（哥伦比亚）加西亚 · 马尔克斯");
			stmt5.setString(4, "外国小说");
			stmt5.setString(5, "");
			File file5=new File("D:\\mess\\105.jpg");
			java.io.BufferedInputStream imageInput5 = new java.io.BufferedInputStream(
           	     new java.io.FileInputStream(file5));
			stmt5.setBinaryStream(6, imageInput5,(int) file5.length());
			stmt5.executeUpdate();
			
			String sql6 = "INSERT INTO BOOKINFO  VALUES(106,?,?,?,?,?,?)"; // SQL语句
			PreparedStatement stmt6 = null;
			stmt6 = conn.prepareStatement(sql6);
			stmt6.setString(1, " 面纱");
			stmt6.setString(2, "时代文艺出版社");
			stmt6.setString(3, "〔英〕毛姆 著，王晋华 译");
			stmt6.setString(4, "世界名著");
			stmt6.setString(5, "");
			File file6=new File("D:\\mess\\106.jpg");
			java.io.BufferedInputStream imageInput6 = new java.io.BufferedInputStream(
           	     new java.io.FileInputStream(file6));
			stmt6.setBinaryStream(6, imageInput6,(int) file6.length());
			stmt6.executeUpdate();
			*/
			
			/*插入收藏记录
			for(int i=103;i<=106;++i) {
			String sql = "INSERT INTO FAVORITE VALUES(20001,?,?)"; // SQL语句
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,i);
			String date = "2021-05-01 19:20";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			Date dt = sdf.parse(date);
			stmt.setTimestamp(2,new Timestamp(dt.getTime()));
			stmt.executeUpdate();
			}
			*/
			
			/*插入预约记录
				
				String sql = "INSERT INTO PREORDER VALUES(10002,?,?)"; // SQL语句
				PreparedStatement stmt = null;
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1,103);
				String date = "2021-05-27 15:20";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
				Date dt = sdf.parse(date);
				stmt.setTimestamp(2,new Timestamp(dt.getTime()));
				stmt.executeUpdate();
			*/	
			
			
			/*插入借阅记录
			for(int i=103;i<=106;++i) {
				String sql = "INSERT INTO LOAN VALUES(10001,?,1,?,30)"; // SQL语句
				PreparedStatement stmt = null;
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1,i);
				String date = "2021-05-27 15:20";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
				Date dt = sdf.parse(date);
				stmt.setTimestamp(2,new Timestamp(dt.getTime()));
				stmt.executeUpdate();
			}
			*/
		
			
			conn.commit();
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
		
		
	}
	
}
