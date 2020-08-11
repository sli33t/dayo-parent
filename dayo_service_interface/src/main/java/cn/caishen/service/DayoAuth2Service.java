package cn.caishen.service;

import cn.caishen.domain.utils.LbMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "dayo-auth2")
public interface DayoAuth2Service {

    @PostMapping(value = "/oauth/token")
    LbMap loginAuth2(@RequestParam("telNo") String telNo, @RequestParam("password") String password,
                     @RequestParam(value = "grant_type", defaultValue = "password") String grant_type,
                     @RequestParam(value = "client_id", defaultValue = "myapp") String client_id,
                     @RequestParam(value = "scope", defaultValue = "all") String scope,
                     @RequestParam(value = "client_secret", defaultValue = "lxapp") String client_secret) throws Exception;

}
