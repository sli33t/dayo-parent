package cn.caishen.worklog.common;

public class LoginException extends RuntimeException {

    public LoginException(){
        super();
    }

    public LoginException(String msg){
        super(msg);
    }
}
