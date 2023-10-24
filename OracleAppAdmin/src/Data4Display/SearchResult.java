package Data4Display;

import java.util.Vector;

public class SearchResult {
    private Vector<String> colName;
    private Vector<Object> sResult;
    private String type;
    public SearchResult(String _type){
        if(_type.equals("BOOK")){
            colName=new Vector<String>();
            colName.add("id");
            colName.add("书名");
            colName.add("作者");
            colName.add("类型");
            colName.add("封面");
        }
        if(_type.equals("PREORDER")){
            colName=new Vector<String>();
            colName.add("用户id");
            colName.add("图书id");
            //colName.add("书名");
            colName.add("时间");
        }
    }

    public Vector<Object> getsResult() { return sResult; }
    public Vector<String> getColName() { return colName; }
}