package cn.caishen.auth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;
    
    /**
     * 退出登录,并清除redis中的token
     **/
    @GetMapping("/logout")
    public Boolean removeToken(String accessToken){
        return consumerTokenServices.revokeToken(accessToken);
    }
}
