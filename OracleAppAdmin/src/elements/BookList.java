package elements;

import Runtime.BookInfo;
import oracle.sql.BLOB;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class BookList {
    protected Vector<BookInfo> bookList;
    public int readInfo(SearchType srchAttr,String keyword){
        Connection conn=null;
        ResultSet rs=null;
        BookInfo tpl=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
        String stmt="SELECT BOOKID,BOOKNAME,AUTHOR,TYPE,PIC FROM BOOKINFO WHERE "+SearchType.toAttrName(srchAttr)+"="+keyword;
        try {
            PreparedStatement pstmt=conn.prepareStatement(stmt);
            rs=pstmt.executeQuery();
            while(rs.next()){
                tpl=new BookInfo(rs.getInt("BOOKID"),rs.getString("BOOKNAME"),
                        rs.getString("AUTHOR"),rs.getString("TYPE"),
                        new ImageIcon(rs.getBlob("PIC").getBinaryStream().readAllBytes()));
                bookList.add(tpl);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public Vector<BookInfo> getBookList() { return bookList; }

}
