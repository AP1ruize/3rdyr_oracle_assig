package Runtime;

import elements.User;

public class LoginState {//作为程序运行时传递的参数 比如借阅时向借阅页面传递用户信息 对于Reader类请使用ReaderLoginState
    protected int id;
    protected String nickname;

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
    public void importUser(User u){
        if(u==null){
            id=-1; return;
        }
        id=u.getId();
        nickname=u.getNickname();
    }
}
