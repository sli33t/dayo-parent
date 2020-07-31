package cn.caishen.domain.auth.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Payload<T> {

    //jwt的id
    private String id;

    //用户数据，不确定，可以是任意类型
    private T userInfo;

    //过期时间
    private Date expiration;
}
