package Logic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.Date;
import java.sql.ResultSet;
import Runtime.*;
import elements.DBConnection;
/*
 * 注意不要让一个用户预定同一本书多次，否则会计算错误
 * 归还部分均假设已经查出用户借阅过该图书,借阅时有足够的剩余图书
 * 进行逻辑调用时注意先判断以上条件
 */
public class LendAndReturn
{
	public int delPreorder(ReaderLoginState user, BookInfo book, java.sql.Timestamp timeStamp)//删除一条预约记录
	{
		Connection conn=null;
		
        int num=0;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try{
        	
            String stmt1="delete from preorder " + 
            		" where USERID=? AND BOOKID=?";
            if(timeStamp!=null)
                stmt1+=" and time=?";
           
            PreparedStatement updStmt1=conn.prepareStatement(stmt1);
            updStmt1.setInt(1,user.getId());
            updStmt1.setInt(2,book.getid());
            if(timeStamp!=null)
                updStmt1.setTimestamp(3,timeStamp);
     
            num=updStmt1.executeUpdate();   
            
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        return num;
	}
	
	public Vector getAllPreorder(ReaderLoginState user)//获取用户全部预约
	{
		Vector v=new Vector();
		Connection conn=null;
	       
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        try{
        	String stmt="select * from preorder where userid=? ";
        			
        	PreparedStatement updStmt=conn.prepareStatement(stmt);
        	updStmt.setInt(1,user.getId());
            ResultSet rs = updStmt.executeQuery();
            while(rs.next())
            {
            	Vector tmpv=new Vector();
            	tmpv.addElement(rs.getInt(1));
            	tmpv.addElement(rs.getInt(2));
            	tmpv.addElement(rs.getTimestamp(3));
            	v.addElement(tmpv);
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
		return v;
	}
	public int booknum(BookInfo book)//获取图书库存数量
	{
		int num=0;
		Connection conn=null;
       
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try{
        	String stmt="select * from stock where bookid=? ";
        			
        	PreparedStatement updStmt=conn.prepareStatement(stmt);
        	updStmt.setInt(1,book.getid());
            ResultSet rs = updStmt.executeQuery();
            if(rs.next())
            {
            	num=rs.getInt(3);
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        return num;
	}
	public int getPONum(ReaderLoginState user, BookInfo book)//获取在此用户之前的预约人数
	{
		int num=0;
		Connection conn=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try{
        	String stmt0=
        			  " select * "
        			+ " from preorder "
        			+ " WHERE USERID=? AND BOOKID=? "
        			+ " order by time";
        	PreparedStatement updStmt0=conn.prepareStatement(stmt0);
            updStmt0.setInt(1,user.getId());
            updStmt0.setInt(2,book.getid());
            ResultSet rs0 = updStmt0.executeQuery();
            rs0.next();
            
            String stmt01=
      			  " select * "
      			+ " from preorder "
      			+ " WHERE USERID!=? and BOOKID=? AND TIME<?"
      			+ " order by time";
      	PreparedStatement updStmt01=conn.prepareStatement(stmt01);
          updStmt01.setInt(1,user.getId());
          updStmt01.setInt(2,book.getid());
          updStmt01.setTimestamp(3,rs0.getTimestamp(3));
          ResultSet rs01 = updStmt01.executeQuery();
          while(rs01.next())
          {
        	  num++;
          }
           
        	
            
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        return num;
	}
	public int allPONum(BookInfo book)//返回所有用户预定这本书的本数
	{
		int num=0;
		Connection conn=null;
        
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try{
        	String stmt="select * from preorder where bookid=? ";
        			
        	PreparedStatement updStmt=conn.prepareStatement(stmt);
        	updStmt.setInt(1,book.getid());
        	
            ResultSet rs = updStmt.executeQuery();
            while(rs.next())
            {
            	 num++;
            }
           
            
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        return num;
	}
	public int preordernum(ReaderLoginState user,BookInfo book)//返回该用户预定这本书的本数**请用此函数判断用户是否预定过此书**
	{
		int num=0;
		Connection conn=null;
   
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try{
        	String stmt="select * from preorder where bookid=? and userid=? ";
        			
        	PreparedStatement updStmt=conn.prepareStatement(stmt);
        	updStmt.setInt(1,book.getid());
        	updStmt.setInt(2,user.getId());
            ResultSet rs = updStmt.executeQuery();
            while(rs.next())
            {
            	 num++;
            }
           
            
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        return num;
	}


	public static int borrowbook(ReaderLoginState user,BookInfo book,int time)//用户借阅图书,time为期限
	{
		
		Connection conn=null;
		Date date = new Date(System.currentTimeMillis());
    	java.util.Date date1 = new java.util.Date();
    	java.sql.Timestamp timeStamp = new java.sql.Timestamp(date1.getTime());
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try{
            String sql="select remain from stock where bookid=?";
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,book.getId());
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()){
                if(rs.getInt("remain")<=0) {//检查库存
                    rs.close();
                    pstmt.close();
                    conn.close();
                    return -1;
                }
                //还有库存
                rs.close();
                pstmt.close();
            }
            else{
                rs.close();
                pstmt.close();
                conn.close();
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }

        try{
        	String stmt="insert into LOAN VALUES(?,?,?,?,?,?)";
        			
        	PreparedStatement updStmt=conn.prepareStatement(stmt);
        	
        	
        	updStmt.setInt(1,user.getId());
            updStmt.setInt(2,book.getid());
            updStmt.setInt(3,1);
            updStmt.setDate(4,date);
            updStmt.setTimestamp(5,timeStamp);
            updStmt.setInt(6,time);
            updStmt.execute();

      
           String stmt3="UPDATE STOCK SET REMAIN = REMAIN-1 WHERE bookid = ? ";
    		
            PreparedStatement updStmt3=conn.prepareStatement(stmt3);
            updStmt3.setInt(1,book.getid());
            updStmt3.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        return 0;
	}
	public static int returnbook(ReaderLoginState user,BookInfo book)//用户归还图书,图书未损坏，返回值为距离归还的剩余天数（负值表示超时）
    {
		Connection conn=null;
		Date date = new Date(System.currentTimeMillis());
    	java.util.Date date1 = new java.util.Date();
    	java.sql.Timestamp timeStamp = new java.sql.Timestamp(date1.getTime());
        long day=0;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try{
        	String stmt0=
        			  " select USERID,BOOKID,AMOUNT,loandate,loantime,DURATION,?,?,? "
        			+ " from LOAN "
        			+ " WHERE USERID=? AND BOOKID=? "
        			+ " order by loandate,loantime";
        	PreparedStatement updStmt0=conn.prepareStatement(stmt0);
        	
        	
        	updStmt0.setDate(1,date);
            updStmt0.setTimestamp(2,timeStamp);
            updStmt0.setInt(3,0);
            updStmt0.setInt(4,user.getId());
            updStmt0.setInt(5,book.getid());
            ResultSet rs0 = updStmt0.executeQuery();
            rs0.next();
            
            
            
//        	String stmt="insert into LOANHISTORY values(?,?,?,?,?,?,?,?,?)";
//
//        	PreparedStatement updStmt=conn.prepareStatement(stmt);
//        	updStmt.setInt(1,rs0.getInt(1));
//        	updStmt.setInt(2,rs0.getInt(2));
//        	updStmt.setInt(3,rs0.getInt(3));
//        	updStmt.setDate(4,rs0.getDate(4));
//        	updStmt.setTimestamp(5,rs0.getTimestamp(5));
//        	updStmt.setInt(6,rs0.getInt(6));
//        	updStmt.setDate(7,rs0.getDate(7));
//        	updStmt.setTimestamp(8,rs0.getTimestamp(8));
//        	updStmt.setInt(9,rs0.getInt(9));
//            updStmt.execute();
            
            
            java.sql.Timestamp timeStamp1=rs0.getTimestamp(5);
            int DURATION=rs0.getInt(6);
            day=timeStamp1.getTime()-timeStamp.getTime();
            day=day/86400000+DURATION;
            
            
            String stmt1="delete from LOAN " + 
            		" where USERID=? AND BOOKID=? and "
            		+ "loandate=? and loantime=?";
           
            PreparedStatement updStmt1=conn.prepareStatement(stmt1);
            updStmt1.setInt(1,user.getId());
            updStmt1.setInt(2,book.getid());
            updStmt1.setDate(3,rs0.getDate(4));
        	updStmt1.setTimestamp(4,rs0.getTimestamp(5));
            updStmt1.execute();
            
//            String stmt2="insert into STOCKCHANGE VALUES (?, ?, ?, ?,?,?)";
//
//            PreparedStatement updStmt2=conn.prepareStatement(stmt2);
//            updStmt2.setInt(1,user.getId());
//            updStmt2.setInt(2,book.getid());
//            updStmt2.setInt(3,-1);
//            updStmt2.setInt(4,0);
//            updStmt2.setDate(5,date);
//            updStmt2.setTimestamp(6,timeStamp);
//            updStmt2.execute();
            
            String stmt3="UPDATE STOCK SET REMAIN = REMAIN+1 WHERE bookid = ? ";
    		
            PreparedStatement updStmt3=conn.prepareStatement(stmt3);
            updStmt3.setInt(1,book.getid());
            updStmt3.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        return (int)day;
    }
	public static int returnbookdamage(ReaderLoginState user,BookInfo book,int p)//用户归还图书,图书损坏,p=0损坏，p=1丢失,返回值为距离归还的剩余天数（负值表示超时）
    {
		Connection conn=null;
		Date date = new Date(System.currentTimeMillis());
    	java.util.Date date1 = new java.util.Date();
    	java.sql.Timestamp timeStamp = new java.sql.Timestamp(date1.getTime());
        long day=0;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try{
        	String stmt0=
        			  " select USERID,BOOKID,AMOUNT,loandate,loantime,DURATION,?,?,? "
        			+ " from LOAN "
        			+ " WHERE USERID=? AND BOOKID=? "
        			+ " order by loandate,loantime";
        	PreparedStatement updStmt0=conn.prepareStatement(stmt0);
        	
        	
        	updStmt0.setDate(1,date);
            updStmt0.setTimestamp(2,timeStamp);
            updStmt0.setInt(3,p);
            updStmt0.setInt(4,user.getId());
            updStmt0.setInt(5,book.getid());
            ResultSet rs0 = updStmt0.executeQuery();
            rs0.next();
           
//        	String stmt="insert into LOANHISTORY values(?,?,?,?,?,?,?,?,?)";
//
//        	PreparedStatement updStmt=conn.prepareStatement(stmt);
//        	updStmt.setInt(1,rs0.getInt(1));
//        	updStmt.setInt(2,rs0.getInt(2));
//        	updStmt.setInt(3,rs0.getInt(3));
//        	updStmt.setDate(4,rs0.getDate(4));
//        	updStmt.setTimestamp(5,rs0.getTimestamp(5));
//        	updStmt.setInt(6,rs0.getInt(6));
//        	updStmt.setDate(7,rs0.getDate(7));
//        	updStmt.setTimestamp(8,rs0.getTimestamp(8));
//        	updStmt.setInt(9,rs0.getInt(9));
//            updStmt.execute();
            
            
            java.sql.Timestamp timeStamp1=rs0.getTimestamp(5);
            int DURATION=rs0.getInt(6);
            day=timeStamp1.getTime()-timeStamp.getTime();
            day=day/86400000+DURATION;
            
            
            String stmt1="delete from LOAN " + 
            		" where USERID=? AND BOOKID=? and "
            		+ "loandate=? and loantime=?";
           
            PreparedStatement updStmt1=conn.prepareStatement(stmt1);
            updStmt1.setInt(1,user.getId());
            updStmt1.setInt(2,book.getid());
            updStmt1.setDate(3,rs0.getDate(4));
        	updStmt1.setTimestamp(4,rs0.getTimestamp(5));
            updStmt1.execute();
            
//            String stmt2="insert into STOCKCHANGE VALUES (?, ?, ?, ?,?,?)";
//
//            PreparedStatement updStmt2=conn.prepareStatement(stmt2);
//            updStmt2.setInt(1,user.getId());
//            updStmt2.setInt(2,book.getid());
//            updStmt2.setInt(3,-1);
//            updStmt2.setInt(4,p);
//            updStmt2.setDate(5,date);
//            updStmt2.setTimestamp(6,timeStamp);
//            updStmt2.execute();
            if(p==1) {
                String stmt3 = "UPDATE STOCK SET AMOUNT = AMOUNT-1 WHERE bookid = ? ";

                PreparedStatement updStmt3 = conn.prepareStatement(stmt3);
                updStmt3.setInt(1, book.getid());
                updStmt3.execute();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        return (int)day;
    }
	public static int renewalbook(ReaderLoginState user,BookInfo book,int etime)//用户续借所有id=bookid的图书,etime表示额外续借时间,返回值为1表示成功，为0表示续借时已经超时失败
	{
		Connection conn=null;
		long day=0;
		java.util.Date date1 = new java.util.Date();
    	java.sql.Timestamp timeStamp = new java.sql.Timestamp(date1.getTime());
        int res=0;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try{
        	
        	
        	String stmt01="select * from LOAN " + 
            		" where USERID=? AND BOOKID=? "
            		+ " order by loandate,loantime";
            PreparedStatement updStmt01=conn.prepareStatement(stmt01);
            updStmt01.setInt(1,user.getId());
            updStmt01.setInt(2,book.getid());
            ResultSet rs = updStmt01.executeQuery();
            rs.next();
            java.sql.Timestamp timeStamp1=rs.getTimestamp(5);
            int DURATION=rs.getInt(6);
            day=timeStamp1.getTime()-timeStamp.getTime();
            day=day/86400000+DURATION;
        	
        	if(day>=0)//若未过期，可续借
        	{
        		res=1;
        		String stmt3="UPDATE loan SET duration = duration+? WHERE bookid = ? and userid = ?";
    		
        		PreparedStatement updStmt3=conn.prepareStatement(stmt3);
        		updStmt3.setInt(1,etime);
        		updStmt3.setInt(2,book.getid());
        		updStmt3.setInt(3,user.getId());
        		updStmt3.execute();
        	}
            
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        
        return res;
	}


	public int preorderbook(ReaderLoginState user,BookInfo book)//用户预约图书**注意不要让一个用户预定同一本书多次，否则会计算错误**
	{
		Connection conn=null;
		java.util.Date date1 = new java.util.Date();
    	java.sql.Timestamp timeStamp = new java.sql.Timestamp(date1.getTime());
    	
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try{
        	String stmt="insert into preorder VALUES(?,?,?)";
        			
        	PreparedStatement updStmt=conn.prepareStatement(stmt);
        	
        	
        	updStmt.setInt(1,user.getId());
            updStmt.setInt(2,book.getid());
            updStmt.setTimestamp(3,timeStamp);
            updStmt.execute();
            
      
           String stmt3="UPDATE STOCK SET REMAIN = REMAIN-1 WHERE bookid = ? ";
    		
            PreparedStatement updStmt3=conn.prepareStatement(stmt3);
            updStmt3.setInt(1,book.getid());
            updStmt3.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        return 0;
	}
	public int getpreorderbooknum(ReaderLoginState user,BookInfo book)//用户在客户端查看本书前方排队人数,-1为没有预定此书，返回值为前方排队人数,**注意不要让一个用户预定同一本书多次，否则会计算错误**
	{
		int num=0;
		if(preordernum(user,book)<=0)
		{
			return -1;//该用户没有预定此书
		}
		int bookremain=booknum(book);
		int allpreordernum= allPONum(book);
		int getprenum= getPONum(user,book);
		num=getprenum-(bookremain+allpreordernum)+1;//排在用户之前的人数减去真实书本数(remain+所有预约的人数)+1即为需要等待人数=
		if(num<=0)
		{
			return 0;//书足够其获取
		}
		return num;//前方还需等待人数
	}
	public int getpreorderbook(ReaderLoginState user,BookInfo book,int time)//用户尝试取得预约图书，返回0为成功，返回值为前方排队人数,time为借阅时间**注意不要让一个用户预定同一本书多次，否则会计算错误**
	{
		int num=getpreorderbooknum(user,book);
		if(num>0)
			return num;
		
		Connection conn=null;
	       
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try{
        	
        	String stmt="select * from preorder"
        			+ " where bookid=? and userid=? "
        			+ " order by time";
			
        	PreparedStatement updStmt=conn.prepareStatement(stmt);
        	updStmt.setInt(1,book.getid());
        	updStmt.setInt(2,user.getId());
            ResultSet rs = updStmt.executeQuery();
            if(rs.next())
            {
            	delPreorder(user,book,rs.getTimestamp(3));
        		String stmt3="UPDATE STOCK SET REMAIN = REMAIN+1 WHERE bookid = ? ";
        		PreparedStatement updStmt3=conn.prepareStatement(stmt3);
        		updStmt3.setInt(1,book.getid());
        		updStmt3.execute();
        		num=borrowbook(user,book,time);
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
		return num;
		
	}

}
