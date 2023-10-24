package elements;

import java.sql.*;
import java.util.Vector;

public class Notice {
    private int adminID;
    private Timestamp timestamp;
    private String title;
    private String text;
    private int state;

    public Notice(){ adminID=0; timestamp=null; title=null; text=null; state=0; }
    public Notice(int aid,Timestamp ts,String ti,String tx,int sta){
        adminID=aid; timestamp=ts; title=ti; text=tx; state=sta;
    }

    public Vector<Object> toVec(){
        Vector<Object> reV=new Vector<>();
        reV.add(((Integer)adminID).toString());
        reV.add(timestamp);
        reV.add(title);
//        reV.add(text);
        if(state==0)
            reV.add("false");
        else reV.add("true");
        return reV;
    }
    static public Vector<String> colName(){
        Vector<String> reV=new Vector<String>();
        reV.add("管理员id");
        reV.add("最后修改时间");
        reV.add("标题");
        reV.add("是否已发布");
        return reV;
    }
    static public int sqlCode=0;
    static public Vector<Vector> getNotices(Integer aid,Timestamp tsU,Timestamp tsL){
        sqlCode=0;
        Connection conn=null;
        ResultSet rs=null;
        Vector<Vector> reV=new Vector<>();
        Vector<Object> tpl=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        String sql="select * from notice ";
        String suffix="";
        boolean hasSuf=false;
        if(aid!=null){
            suffix+="where ";
            suffix+=(SearchType4NM.AID.attrName()+aid.toString());
            hasSuf=true;
        }
        if(tsU!=null){
            System.out.println("Notice.getNotices:"+tsU.toString());
            if(hasSuf)
                suffix+=" and ";
            else suffix+="where ";
            suffix+=(SearchType4NM.STU.attrName()+SearchType4NM.toSqlFormat(tsU));
            hasSuf=true;
        }
        if(tsL!=null){
            System.out.println("Notice.getNotices:"+tsL.toString());
            if(hasSuf)
                suffix+=" and ";
            else suffix+="where ";
            suffix+=(SearchType4NM.STL.attrName()+SearchType4NM.toSqlFormat(tsL));
            hasSuf=true;
        }
        suffix+=" order by edittime desc";
        sql+=suffix;
        System.out.println("Notice.getNotices:"+sql);
        try {
            Statement stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            while (rs.next()){
                tpl=new Notice(rs.getInt("adminid"),rs.getTimestamp("edittime"),
                        rs.getString("title"),rs.getString("text"),
                        rs.getInt("status")).toVec();
                reV.add(tpl);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        return reV;
    }
    static public Notice getNotice(Integer aid, Timestamp ts){
        sqlCode=0;
        Connection conn=null;
        ResultSet rs=null;
        Notice rV=null;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        String sql="select * from notice where adminid="+aid+" and edittime="+SearchType4NM.toSqlFormat(ts);
        try {
            Statement stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            if(rs.next())
                rV=new Notice(aid,ts,rs.getString("title"),
                        rs.getString("text"),rs.getInt("status"));
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return null;
        }
        return rV;
    }
    static public boolean deleteNotice(Integer aid, Timestamp ts){
        sqlCode=0;
        Connection conn=null;
        int result=-1;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        String sql="delete from notice where adminid="+aid+" and edittime="+SearchType4NM.toSqlFormat(ts);
        try {
            Statement stmt=conn.createStatement();
            result=stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        if(result>=1)
            return true;
        return false;
    }
    static public boolean showNotice(Integer aid, Timestamp ts, String curStat){
        sqlCode=0;
        Connection conn=null;
        int result=1;
        if(curStat.equals("true"))
            result=0;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        String sql="update notice set status="+result+" where adminid="+aid+" and edittime="+SearchType4NM.toSqlFormat(ts);
        try {
            Statement stmt=conn.createStatement();
            result=stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        if(result>=1)
            return true;
        return false;
    }
    static public boolean update(Notice notice){
        sqlCode=0;
        Connection conn=null;
        int result=0;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        String sql="update notice set editdate="+SearchType4NM.toSqlFormat(new Date(System.currentTimeMillis()))
                +" , edittime="+SearchType4NM.toSqlFormat(new Timestamp(System.currentTimeMillis()))
                +" , title='"+notice.getTitle()+"'"
                +" , text='"+notice.getText()+"'"
                +" , status="+notice.getState()
                +" where adminid="+notice.getAdminID()+" and edittime="+SearchType4NM.toSqlFormat(notice.getTimestamp());
        System.out.println("Notice.update:"+sql);
        try {
            Statement stmt=conn.createStatement();
            result=stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        if(result>=1)
            return true;
        return false;
    }
    static public boolean newNotice(Notice notice){
        sqlCode=0;
        Connection conn=null;
        int result=0;
        try {
            conn=DBConnection.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        String sql="insert into notice (adminid,editdate,edittime,title,text,status) values ( ?, ?, ?, ?, ?, ?)";
//        System.out.println("Notice.newNotice:"+sql);
        try {
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,notice.adminID);
            pstmt.setDate(2,new Date(notice.getTimestamp().getTime()));
            pstmt.setTimestamp(3,notice.timestamp);
            pstmt.setString(4,notice.title);
            pstmt.setString(5,notice.text);
            pstmt.setInt(6,notice.state);
            result=pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sqlCode=e.getErrorCode();
            return false;
        }
        if(result==1)
            return true;
        return false;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setState(boolean st) {
        if(st)
            this.state = 1;
        else this.state=0;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getAdminID() {
        return adminID;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public String getTitle() {
        return title;
    }
    public String getText() {
        return text;
    }
    public int getState() {
        return state;
    }


}
