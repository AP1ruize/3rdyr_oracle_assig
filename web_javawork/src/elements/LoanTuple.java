package elements;

import java.sql.Timestamp;

public class LoanTuple {
    private int uid;
    private int bid;
    private int amt;
    private Timestamp loanTime;
    private int duration;
    public int getBid() {
		return bid;
	}
	public Timestamp getLoanTime() {
		return loanTime;
	}
	public int getDuration() {
		return duration;
	}
	private void readInfo(int userid,int bookid,int amount,Timestamp ts,int dur){
        uid=userid; bid=bookid; amt=amount; loanTime=ts; duration=dur;
    }
    public LoanTuple(int userid, int bookid, int amount, Timestamp ts, int dur){
        readInfo(userid, bookid, amount, ts, dur);
    }
}
