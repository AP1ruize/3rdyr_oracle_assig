package elements;

import java.sql.Timestamp;
import java.util.Vector;

public class LoanTuple {
    private int uid;
    private int bid;
    private int amt;
    private Timestamp loanTime;
    private int duration;
    private void readInfo(int userid,int bookid,int amount,Timestamp ts,int dur){
        uid=userid; bid=bookid; amt=amount; loanTime=ts; duration=dur;
    }
    public LoanTuple(int userid, int bookid, int amount, Timestamp ts, int dur){
        readInfo(userid, bookid, amount, ts, dur);
    }

    public int getUid() {
        return uid;
    }

    public int getBid() {
        return bid;
    }

    public int getAmt() {
        return amt;
    }

    public int getDuration() {
        return duration;
    }

    public Timestamp getLoanTime() {
        return loanTime;
    }


}
