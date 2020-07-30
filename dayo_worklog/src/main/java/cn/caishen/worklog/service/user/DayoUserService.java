package cn.caishen.worklog.service.user;

import cn.caishen.domain.utils.LbMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "dayo-system")
public interface DayoUserService {

    @GetMapping(value = "/userService/findAll")
    LbMap findAll(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("jsonStr") String jsonStr);

    @GetMapping(value = "/userService/findByRoleId")
    LbMap findByRoleId(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("roleId") String roleId);

    @PostMapping(value = "/userService/edit")
    LbMap edit(@RequestParam("user") String user);

    @PostMapping(value = "/userService/delete")
    LbMap delete(@RequestParam("userId") String userId, @RequestParam("rowVersion") String rowVersion);

    @GetMapping(value = "/userService/findRoleByUserId")
    LbMap findRoleByUserId(@RequestParam("userId") String userId);

    @GetMapping(value = "/userService/findById")
    LbMap findById(@RequestParam("userId") String userId);

    @PostMapping(value = "/userService/update")
    LbMap update(@RequestParam("user") String user, @RequestParam("useRowVersion") boolean useRowVersion);

}
