package elements;

public enum SearchType4UM {
    IDU,IDL,NICK,CL,BLC,NULL;
    public static String toAttrName(SearchType4UM st){
        if(st==IDU)
            return "USERID<=";
        if(st==IDL)
            return "USERID>=";
        if(st==NICK)
            return "NICKNAME";
        if(st==CL)
            return "EXP";
        if(st==BLC)
            return "CREDIT";
        return "";
    }
    public static String toDspName(SearchType4UM st){
        if(st==IDU)
            return "ID上限";
        if(st==IDL)
            return "ID下限";
        if(st==NICK)
            return "昵称";
        if(st==CL)
            return "等级";
        if(st==BLC)
            return "余额";
        return "空";
    }

    @Override
    public String toString() {
        return toDspName(this);
    }

    public String attrName(){return toAttrName(this);}
}
