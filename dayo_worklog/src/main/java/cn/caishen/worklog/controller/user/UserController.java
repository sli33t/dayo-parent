package cn.caishen.worklog.controller.user;

import cn.caishen.domain.domain.po.User;
import cn.caishen.domain.utils.JSONUtil;
import cn.caishen.domain.utils.LbMap;
import cn.caishen.service.DayoUserService;
import cn.caishen.worklog.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    //private final static Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    @Autowired
    private DayoUserService dayoUserService;

    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toUserList")
    public ModelAndView toUserList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/user-list");
        return mv;
    }


    /**
     * 查询所有用户信息
     * @return
     */
    @GetMapping(value = "/findAll")
    public LbMap findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String jsonStr){
        try {
            LbMap pageMap = dayoUserService.findAll(page, limit, jsonStr);
            logger.info("用户查询成功");
            return LbMap.successResult("用户查询成功", pageMap.getList("data"), pageMap.getLong("count"));
        }catch (Exception e){
            return LbMap.failResult("用户查询失败，"+e.getMessage());
        }
    }

    /**
     * 查询岗位用户信息
     * @param page
     * @param limit
     * @param roleId
     * @return
     */
    @GetMapping(value = "/findByRoleId")
    public LbMap findByRoleId(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit,  String roleId){
        try {
            if (roleId.equals("")){
                return LbMap.failResult("请选择岗位");
            }
            LbMap pageMap = dayoUserService.findByRoleId(page, limit, roleId);
            logger.info("用户查询成功");
            return LbMap.successResult("用户查询成功", pageMap.getList("data"), pageMap.getLong("count"));
        }catch (Exception e){
            return LbMap.failResult("用户查询失败，"+e.getMessage());
        }
    }


    /**
     * 跳转新增页面
     * @return
     */
    @GetMapping(value = "/toAdd")
    public ModelAndView toAdd(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/user-add");
        return mv;
    }

    /**
     * 通过编号查询用户信息
     * @param userId
     * @return
     */
    @GetMapping(value = "/toUpdate")
    public ModelAndView toUpdate(String userId){
        ModelAndView mv = new ModelAndView();
        try {
            if (userId.equals("")){
                throw new Exception("没有找到用户编号");
            }

            LbMap userMap = dayoUserService.findById(userId);
            if (userMap!=null){
                User user = JSONUtil.jsonStringToClass(userMap.getJsonString("data"), User.class);
                mv.addObject("user", user);
                mv.setViewName("user/user-update");
            }else {
                mv.setViewName("system/error");
                mv.addObject("errorMsg", "没有找到用户信息");
            }
            //logger.info("查询成功");
            return mv;
        }catch (Exception e){
            mv.setViewName("system/error");
            mv.addObject("errorMsg", e.getMessage());
            logger.info("查询成功");
            return mv;
        }
    }

    /**
     * 新增和修改
     * @param user
     * @return
     */
    @PostMapping(value = "/edit")
    public LbMap edit(User user) {
        try {
            if (user.getUsername().equals("")){
                return LbMap.failResult("用户编辑失败，用户名称不能为空！");
            }else if (user.getTelNo().equals("")){
                return LbMap.failResult("用户编辑失败，用户电话不能为空！");
            }

            String userStr = JSONUtil.classToJsonString(user);
            LbMap editMap = dayoUserService.edit(userStr);
            return editMap;
        }catch (Exception e){
            logger.info("用户编辑失败："+e.getMessage());
            return LbMap.failResult("用户编辑失败，"+e.getMessage());
        }
    }

    /**
     * 删除
     * @param userId
     * @return
     */
    @PostMapping(value = "/delete")
    public LbMap delete(String userId, String rowVersion){
        try {
            if (userId.equals("")){
                return LbMap.failResult("用户删除失败，没有找到用户编号！");
            }
            LbMap deleteMap = dayoUserService.delete(userId, rowVersion);
            logger.info(userId+"用户删除成功");
            return deleteMap;
        }catch (Exception e){
            return LbMap.failResult("用户删除失败，"+e.getMessage());
        }
    }

}
