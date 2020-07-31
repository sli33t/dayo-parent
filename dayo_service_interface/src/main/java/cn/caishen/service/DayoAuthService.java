package cn.caishen.service;

import cn.caishen.domain.utils.LbMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "dayo-auth")
public interface DayoAuthService {

    @PostMapping(value = "/loginAuth")
    LbMap loginAuth(@RequestParam("telNo") String telNo, @RequestParam("password") String password) throws Exception;

    @PostMapping(value = "/getUserByToken")
    LbMap getUserByToken(@RequestParam("token") String token) throws Exception;

    @PostMapping(value = "/setToken")
    LbMap setToken(@RequestParam("userStr") String userStr) throws Exception;
}
