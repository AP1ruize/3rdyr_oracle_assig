package elements;

import Runtime.BookInfo;
import oracle.sql.BLOB;

import javax.swing.*;
import java.io.*;
import java.sql.*;

public class Book {
    private int id;
    private String name;
    private String press;//出版社
    private String author;
    private String type;
    private String intro;
    private ImageIcon pic;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPress() {
        return press;
    }
    public String getAuthor() {
        return author;
    }
    public ImageIcon getPic() {
        return pic;
    }
    public String getType() {
        return type;
    }
    public String getIntro() {
        return intro;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void readInfo(int _id, String _name, String _press, String auth, String ty, String _intro, ImageIcon _pic){
        id=_id; name=_name; press=_press; author=auth; type=ty; intro=_intro; pic=_pic;
    }
    public int readInfo(BookInfo bi){
        id=bi.getId(); return readInfo(bi.getId());
    }
    public int readInfo(int bid){
        sqlCode=0;
        Connection conn=null;
        ResultSet rs=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return e.getErrorCode();
        }
        String stmt="SELECT BOOKID,BOOKNAME,AUTHOR,TYPE,PIC FROM BOOKINFO WHERE BOOKID="+bid;
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            rs=pstmt.executeQuery();
            while(rs.next()){
                if(rs.getBlob("PIC")!=null) {
                    readInfo(bid, rs.getString("BOOKNAME"), rs.getString("PRESS"),
                            rs.getString("AUTHOR"), rs.getString("TYPE"),
                            rs.getString("INTRO"),
                            new ImageIcon(rs.getBlob("PIC").getBinaryStream().readAllBytes()));
                }
                else{
                    readInfo(bid, rs.getString("BOOKNAME"), rs.getString("PRESS"),
                            rs.getString("AUTHOR"), rs.getString("TYPE"),
                            rs.getString("INTRO"),
                            new ImageIcon());
                }
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return e.getErrorCode();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public static int sqlCode=0;
    static public String ioMessage=null;
    public static Book verify(int bid){
        if(bid<=0)
            return null;
        sqlCode=0;
        Connection conn=null;
        ResultSet rs=null;
        Book rtValue=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        String stmt="SELECT * FROM BOOKINFO WHERE BOOKID="+bid;
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            rs=pstmt.executeQuery();
            if(rs.next()){
                rtValue=new Book();
                if(rs.getBlob("PIC")!=null) {
                    rtValue.readInfo(bid, rs.getString("BOOKNAME"), rs.getString("PRESS"),
                            rs.getString("AUTHOR"), rs.getString("TYPE"),
                            rs.getString("INTRO"),
                            new ImageIcon(rs.getBlob("PIC").getBinaryStream().readAllBytes()));
                }
                else{
                    rtValue.readInfo(bid, rs.getString("BOOKNAME"), rs.getString("PRESS"),
                            rs.getString("AUTHOR"), rs.getString("TYPE"),
                            rs.getString("INTRO"),
                            new ImageIcon());
                }
            }
            else rtValue=null;
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
        return rtValue;
    }

    public static boolean deleteBook(Book book){
        sqlCode=0;
        Connection conn=null;
        String sqlStmt;
        int retnValue=0;
        PreparedStatement pstmt= null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        sqlStmt="delete from stock where bookid=?";
        try {
            pstmt = conn.prepareStatement(sqlStmt);
            pstmt.setInt(1, book.getId());
            retnValue=pstmt.executeUpdate();
            pstmt.close();
//            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        sqlStmt="delete from favorite where bookid=?";
        try {
            pstmt = conn.prepareStatement(sqlStmt);
            pstmt.setInt(1, book.getId());
            retnValue=pstmt.executeUpdate();
            pstmt.close();
//            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        sqlStmt="delete from bookinfo where bookid=?";
        try {
            pstmt = conn.prepareStatement(sqlStmt);
            pstmt.setInt(1, book.getId());
            retnValue=pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if(retnValue>=1)
            return true;
        return false;
    }

    public static BookInfo toBookInfo(Book book){
        return new BookInfo(book.id,book.name,book.author,book.type,book.pic);
    }
    public boolean modifyInfo() {
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
        sqlStmt="update bookinfo set bookname=?, press=?, author=?, type=?, intro=? where bookid=?";
        int retnValue=0;
        try {
            PreparedStatement pstmt=conn.prepareStatement(sqlStmt);
            pstmt.setString(1,this.name);
            pstmt.setString(2,this.press);
            pstmt.setString(3,this.author);
            pstmt.setString(4,this.type);
            pstmt.setString(5,this.intro);
            pstmt.setInt(6,this.id);
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

    static public boolean newBook(String name, String press, String author, String type, String intro, int amt){
        sqlCode=0;
        Connection conn=null;
        String sqlStmt;
        int result=0;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        sqlStmt="select * from bookinfo where bookname=? and author=?";
        try {
            PreparedStatement pstmt1 =conn.prepareStatement(sqlStmt);
            pstmt1.setString(1,name);
            pstmt1.setString(2,author);
            ResultSet rs=pstmt1.executeQuery();
            if(rs.next()){
                rs.close();
                pstmt1.close();
                conn.close();
                return false;
            }
            rs.close();
            pstmt1.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }

        sqlStmt="insert into bookinfo (bookname,press,author,type,intro) values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt2 =conn.prepareStatement(sqlStmt);
            pstmt2.setString(1,name);
            pstmt2.setString(2,press);
            pstmt2.setString(3,author);
            pstmt2.setString(4,type);
            pstmt2.setString(5,intro);
            result= pstmt2.executeUpdate();
            pstmt2.close();
//            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }

        int bookid=0;
        sqlStmt="select bookid from bookinfo where bookname=? and press=? and author=? and type=?";
        PreparedStatement pstmt3= null;
        try {
            pstmt3 = conn.prepareStatement(sqlStmt);
            pstmt3.setString(1,name);
            pstmt3.setString(2,press);
            pstmt3.setString(3,author);
            pstmt3.setString(4,type);
            ResultSet rs=pstmt3.executeQuery();
            if(rs.next())
                bookid=rs.getInt("bookid");
            rs.close();
            pstmt3.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        if(bookid!=0){
            sqlStmt="insert into stock (bookid,amount,remain) values (?, ?, ?)";
            try {
                pstmt3=conn.prepareStatement(sqlStmt);
                pstmt3.setInt(1,bookid);
                pstmt3.setInt(2,amt);
                pstmt3.setInt(3,amt);
                pstmt3.executeUpdate();
                pstmt3.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                sqlCode=e.getErrorCode();
                return false;
            }
        }
        else{
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                sqlCode=e.getErrorCode();
            }
        }

        return true;
    }
    static public boolean newBook(String name, String press, String author, String type, String intro, int amt, String iconPath){
        if(iconPath==null)
            return newBook(name, press, author, type, intro, amt);

        File file=new File(iconPath);
        FileInputStream fis= null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        sqlCode=0;
        Connection conn=null;
        String sqlStmt;
        int result=0;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        sqlStmt="select * from bookinfo where bookname=? and author=?";
        try {
            PreparedStatement pstmt1 =conn.prepareStatement(sqlStmt);
            pstmt1.setString(1,name);
            pstmt1.setString(2,author);
            ResultSet rs=pstmt1.executeQuery();
            if(rs.next()){
                rs.close();
                pstmt1.close();
                conn.close();
                return false;
            }
            rs.close();
            pstmt1.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }

        sqlStmt="insert into bookinfo (bookname,press,author,type,intro,pic) values (?, ?, ?, ?, ?, empty_blob())";
        try {
            PreparedStatement pstmt2 =conn.prepareStatement(sqlStmt);
            pstmt2.setString(1,name);
            pstmt2.setString(2,press);
            pstmt2.setString(3,author);
            pstmt2.setString(4,type);
            pstmt2.setString(5,intro);
            result= pstmt2.executeUpdate();
            pstmt2.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }

        int bookid=0;
        sqlStmt="select bookid, pic from bookinfo where bookname=? and press=? and author=? and type=? for update";
        try {
            PreparedStatement pstmt3=conn.prepareStatement(sqlStmt);
            pstmt3.setString(1,name);
            pstmt3.setString(2,press);
            pstmt3.setString(3,author);
            pstmt3.setString(4,type);
            conn.setAutoCommit(false);
            ResultSet rs=pstmt3.executeQuery();
            if(rs.next()){
                bookid=rs.getInt("bookid");
                BLOB image=(BLOB)rs.getBlob("pic");
                BufferedOutputStream bos = new BufferedOutputStream(image.getBinaryOutputStream());
                int c;
                // 将实际文件中的内容以二进制的形式来输出到blob对象对应的输出流中
                while ((c = fis.read()) != -1) {
                    bos.write(c);
                }
                fis.close();
                bos.close();
            }
            conn.commit();
            rs.close();
            pstmt3.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            ioMessage=e.getMessage();
            return false;
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            ioMessage=e.getMessage();
        }
        sqlStmt="insert into stock (bookid,amount,remain) values (?, ?, ?)";
        try {
            PreparedStatement pstmt3=conn.prepareStatement(sqlStmt);
            pstmt3.setInt(1,bookid);
            pstmt3.setInt(2,amt);
            pstmt3.setInt(3,amt);
            pstmt3.executeUpdate();
            pstmt3.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }

        return true;
    }
}
