package elements;

public class Rule {
    public static int getLevel(int exp){
        return 0;
    }
    public static int initExp(){
        return 0;
    }
    public static int initCredit(){
        return 300;
    }
    public static int maxBuffTuples(){
        return 50;
    }
    public static int dspIn1Page=20;
    public static int showImageSize=180;

    public static Integer convert_2i(String input){
        if(input==null)
            return null;
        if(input.equals(""))
            return null;
        Integer rV=null;
        try{
            rV=Integer.parseInt(input);
        }
        catch (Exception e){
            rV=null;
        }
        return rV;
    }
    public static String convert_2s(String input){
        if(input==null)
            return null;
        if(input.equals(""))
            return null;
        return input;
    }
}
