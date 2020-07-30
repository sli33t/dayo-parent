package cn.caishen.worklog.service.role;

import cn.caishen.domain.utils.LbMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "dayo-system")
public interface DayoRoleService {

    @GetMapping(value = "/roleService/findAll")
    LbMap findAll(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("jsonStr") String jsonStr) throws Exception;

    @GetMapping(value = "/roleService/findByRoleId")
    LbMap findByRoleId(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("roleId") String roleId);

    @PostMapping(value = "/roleService/edit")
    LbMap edit(@RequestParam("role") String role);

    @PostMapping(value = "/roleService/delete")
    LbMap delete(@RequestParam("roleId") String roleId, @RequestParam("rowVersion") String rowVersion);

    @GetMapping(value = "/roleService/findRoleByUserId")
    LbMap findRoleByUserId(@RequestParam("roleId") String roleId);

    @GetMapping(value = "/roleService/findById")
    LbMap findById(@RequestParam("roleId") String roleId);

    @PostMapping(value = "/roleService/update")
    LbMap update(@RequestParam("role") String role, @RequestParam("useRowVersion") boolean useRowVersion);

    @PostMapping(value = "/roleService/updateUserRole")
    LbMap updateUserRole(@RequestParam("jsonStr") String jsonStr);
}
