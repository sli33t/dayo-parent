package cn.caishen.domain.common;

public class LoginException extends RuntimeException {

    public LoginException(){
        super();
    }

    public LoginException(String msg){
        super(msg);
    }
}
