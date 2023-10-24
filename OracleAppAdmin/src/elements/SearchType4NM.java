package elements;

import java.sql.Date;
import java.sql.Timestamp;

public enum SearchType4NM {
    AID,STU,STL,NULL;
    public static String toAttrName(SearchType4NM st){
        if(st==AID)
            return "adminid=";
        if(st==STU)
            return "edittime<=";
        if(st==STL)
            return "edittime>=";
        return "";
    }
    public static String toDspName(SearchType4NM st){
        if(st==AID)
            return "管理员id";
        if(st==STU)
            return "时间上限";
        if(st==STL)
            return "时间下限";
        return "空";
    }
    public static String toSqlFormat(Timestamp ts){
        return "to_timestamp('"+ts.toString()+"','yyyy-mm-dd hh24:mi:ss.ff')";
    }
    public static String toSqlFormat(Date dt){
        return "to_timestamp('"+dt.toString()+"','yyyy-mm-dd')";
    }

    @Override
    public String toString() {
        return toDspName(this);
    }

    public String attrName(){return toAttrName(this);}
}
