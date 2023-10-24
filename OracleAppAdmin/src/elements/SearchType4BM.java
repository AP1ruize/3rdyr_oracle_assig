package elements;

public enum SearchType4BM {
    IDU,IDL,NAME,PRS,ATH,TYP,RMU,RML,AMTU,AMTL,NULL;
    public static String toAttrName(SearchType4BM st){
        if(st==IDU)
            return "bookid<=";
        if(st==IDL)
            return "bookid>=";
        if(st==NAME)
            return "bookname";
        if(st==PRS)
            return "press";
        if(st==ATH)
            return "author";
        if(st==TYP)
            return "type";
        if(st==RMU)
            return "remain<=";
        if(st==RML)
            return "remain>=";
        if(st==AMTU)
            return "amount<=";
        if(st==AMTL)
            return "amount>=";
        return "";
    }
    public static String toDspName(SearchType4BM st){
        if(st==IDU)
            return "图书id上限";
        if(st==IDL)
            return "图书id下限";
        if(st==NAME)
            return "书名";
        if(st==PRS)
            return "出版社";
        if(st==ATH)
            return "作者";
        if(st==TYP)
            return "类型";
        if(st==RMU)
            return "库存剩余上限";
        if(st==RML)
            return "库存剩余下限";
        if(st==AMTU)
            return "总库存上限";
        if(st==AMTL)
            return "总库存下限";
        return "空";
    }

    @Override
    public String toString() {
        return toDspName(this);
    }

    public String attrName(){return toAttrName(this);}
}
