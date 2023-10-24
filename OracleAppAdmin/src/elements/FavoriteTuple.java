package elements;

import java.sql.Timestamp;

public class FavoriteTuple {
    protected int uid;
    protected int bid;
    protected Timestamp time;

    protected void readInfo(int userid,int bookid,Timestamp ts){ uid=userid; bid=bookid; time=ts; }
    public FavoriteTuple(int userid, int bookid, Timestamp ts){
        readInfo(userid, bookid, ts);
    }

}
