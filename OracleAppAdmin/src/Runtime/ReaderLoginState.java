package Runtime;

import elements.Reader;
import elements.User;

import java.util.Vector;

public class ReaderLoginState extends LoginState {//作为程序运行时传递的参数 比如借阅时向借阅页面传递用户信息
    private int exp;
    private int credit;

    public int getCredit() { return credit; }
    public int getExp() { return exp; }

    public void importUser(User u) {
        super.importUser(u); exp=u.getExp(); credit=u.getCredit();
    }
    public ReaderLoginState(Reader u){ importUser(u); }
    public ReaderLoginState(int _id,String nn,int _class,int blc){
        id=_id; nickname=nn; exp=_class; credit=blc;
    }
    public ReaderLoginState(int _id){ id=_id; nickname=null; exp=0; credit=0; }

    public Vector<String> toVec(){
        Vector<String> reV=new Vector<>();
        reV.add(((Integer)id).toString());
        reV.add(nickname);
        reV.add(((Integer)exp).toString());
        reV.add(((Integer)credit).toString());
        return reV;
    }

    static public Vector<String> getColName(){
        Vector<String> reV=new Vector<>();
        reV.add("ID");
        reV.add("昵称");
        reV.add("等级");
        reV.add("余额");
        return reV;
    }
}
