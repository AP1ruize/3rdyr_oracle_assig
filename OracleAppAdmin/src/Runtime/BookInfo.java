package Runtime;

import elements.Book;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BLOB;

//import javax.print.attribute.standard.MediaSize;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
//import java.sql.SQLException;
import java.util.Vector;

public class BookInfo {//作为程序运行时传递的参数 比如借阅时向借阅页面传递图书信息
    private int id;
    private String name;
    private String author;
    private String type;
    private ImageIcon pic;
    public void importBook(Book b){
        id=b.getId();
        name=b.getName();
        author=b.getAuthor();
        pic=b.getPic();
        type=b.getType();
    }

    public void readInfo(int _id, String _name, String auth, String ty, ImageIcon _pic){
        id=_id; name=_name; author=auth; type=ty; pic=_pic;
    }
    public BookInfo(int _id,String _name,String auth,String ty,ImageIcon _pic){readInfo(_id, _name, auth, ty, _pic);}
    public BookInfo(Book b){importBook(b);}
    public BookInfo(int bid){id=bid;}
    public BookInfo(Vector vec){
        if(this.readInfo(vec))
            return;
        id=-1;
    }

    public int getId() { return id; }
    public int getid() { return id; }
    public String getType() { return type; }
    public String getAuthor() { return author; }
    public String getName() { return name; }
    public ImageIcon getPic() { return pic; }

    public Vector toVec() {
        Blob blob;
        Vector result=new Vector();
        result.add(id);
        result.add(name);
        result.add(author);
        result.add(type);
        if(pic!=null){
            ImageIcon imageIcon= pic;
            if(imageIcon.getImage()!=null)
                imageIcon.setImage(imageIcon.getImage().getScaledInstance(100,100, Image.SCALE_FAST));
            result.add(imageIcon);
        }
        else result.add(null);
        return result;
    }

    public boolean readInfo(Vector vec){
        if(vec.size()!=5)
            return false;
        readInfo((int)vec.get(0),(String)vec.get(1),(String)vec.get(2),(String)vec.get(3),null);
        return true;
    }

    public static Vector<String> getColName(){
        Vector<String> result=new Vector<>();
        result.add("BookID");
        result.add("书名");
        result.add("作者");
        result.add("类型");
        result.add("预览图");
        return result;
    }
}
