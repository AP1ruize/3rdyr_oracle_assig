package elements;

public enum SearchType {
    BID,NAME,AUTHOR,TYPE;
    public static String toAttrName(SearchType st){
        if(st==BID)
            return "BOOKID";
        if(st==AUTHOR)
            return "AUTHOR";
        if(st==TYPE)
            return "TYPE";
        return "BOOKNAME";
    }

    public static String toDspName(SearchType st){
        if(st==BID)
            return "BOOKID";
        if(st==AUTHOR)
            return "作者";
        if(st==TYPE)
            return "类型";
        return "书名";
    }

    @Override
    public String toString() {
        return toDspName(this);
    }

    public String attrName(){return toAttrName(this);}
}
