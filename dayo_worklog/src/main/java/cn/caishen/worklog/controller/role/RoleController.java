package cn.caishen.worklog.controller.role;

import cn.caishen.domain.domain.po.Role;
import cn.caishen.domain.utils.JSONUtil;
import cn.caishen.domain.utils.LbMap;
import cn.caishen.worklog.controller.BaseController;
import cn.caishen.worklog.service.role.DayoRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

    //private final static Logger logger = (Logger) LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private DayoRoleService dayoRoleService;


    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toRoleList")
    public ModelAndView toRoleList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("role/role-list");
        return mv;
    }


    /**
     * 查询所有岗位信息
     * @return
     */
    @GetMapping(value = "/findAll")
    public LbMap findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String jsonStr){
        try {
            LbMap pageMap = dayoRoleService.findAll(page, limit, jsonStr);
            logger.info("岗位查询成功");
            return LbMap.successResult("岗位查询成功", pageMap.getList("data"), pageMap.getLong("count"));
        }catch (Exception e){
            return LbMap.failResult("岗位查询失败，"+e.getMessage());
        }
    }


    /**
     * 跳转新增页面
     * @return
     */
    @GetMapping(value = "/toAdd")
    public ModelAndView toAdd(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("role/role-add");
        return mv;
    }

    /**
     * 通过编号查询岗位信息
     * @param roleId
     * @return
     */
    @GetMapping(value = "/toUpdate")
    public ModelAndView toUpdate(String roleId){
        ModelAndView mv = new ModelAndView();
        try {
            if (roleId.equals("")){
                throw new Exception("没有找到岗位编号");
            }

            LbMap roleMap = dayoRoleService.findById(roleId);
            if (roleMap!=null){
                Role role = JSONUtil.jsonStringToClass(roleMap.getJsonString("data"), Role.class);
                mv.addObject("role", role);
                mv.setViewName("role/role-update");
            }else {
                mv.setViewName("system/error");
                mv.addObject("errorMsg", "没有找到岗位信息");
            }
            logger.info("查询成功");
            return mv;
        }catch (Exception e){
            mv.setViewName("system/error");
            mv.addObject("errorMsg", e.getMessage());
            logger.info("查询失败");
            return mv;
        }
    }

    /**
     * 新增和修改
     * @param role
     * @return
     */
    @PostMapping(value = "/edit")
    public LbMap edit(Role role) {
        try {
            if (role.getRoleName().equals("")){
                return LbMap.failResult("岗位编辑失败，岗位名称不能为空！");
            }

            String roleStr = JSONUtil.classToJsonString(role);
            LbMap editMap = dayoRoleService.edit(roleStr);
            return editMap;
        }catch (Exception e){
            logger.info("岗位编辑失败："+e.getMessage());
            return LbMap.failResult("岗位编辑失败，"+e.getMessage());
        }
    }

    /**
     * 删除
     * @param roleId
     * @return
     */
    @PostMapping(value = "/delete")
    public LbMap delete(String roleId, String rowVersion){
        try {
            if (roleId.equals("")){
                return LbMap.failResult("岗位删除失败，没有找到岗位编号！");
            }
            dayoRoleService.delete(roleId, rowVersion);
            logger.info(roleId+"岗位删除成功");
            return LbMap.successResult("岗位删除成功");
        }catch (Exception e){
            return LbMap.failResult("岗位删除失败，"+e.getMessage());
        }
    }

    /**
     * 跳转分配用户页面
     * @param roleId
     * @return
     */
    @GetMapping(value = "/toUserRole")
    public ModelAndView toUserRole(String roleId){
        ModelAndView mv = new ModelAndView();
        try {
            if (roleId.equals("")){
                throw new Exception("没有找到岗位编号");
            }

            LbMap roleMap = dayoRoleService.findById(roleId);
            Role role = JSONUtil.jsonStringToClass(roleMap.getString("data"), Role.class);
            mv.addObject("role", role);
            mv.setViewName("role/role-userRole");
            logger.info("查询成功");
            return mv;
        }catch (Exception e){
            mv.setViewName("system/error");
            mv.addObject("errorMsg", e.getMessage());
            logger.info("查询失败");
            return mv;
        }
    }

    /**
     * 保存分配用户
     * @param jsonStr
     * @return
     */
    @PostMapping(value = "/updateUserRole")
    public LbMap updateUserRole(String jsonStr){
        LbMap map = LbMap.fromObject(jsonStr);
        if (map==null||map.size()==0){
            return LbMap.failResult("没有选择用户信息");
        }

        try {
            return dayoRoleService.updateUserRole(jsonStr);
        }catch (Exception e){
            return LbMap.failResult("用户分配失败，"+e.getMessage());
        }
    }

}
