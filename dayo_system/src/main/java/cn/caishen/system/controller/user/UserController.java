package cn.caishen.system.controller.user;

import cn.caishen.domain.domain.po.User;
import cn.caishen.domain.utils.JSONUtil;
import cn.caishen.domain.utils.LbMap;
import cn.caishen.domain.utils.MD5Util;
import cn.caishen.system.service.user.UserService;
import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/userService")
public class UserController /*extends BaseController*/ {

    //private final static Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);
    protected static Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    /**
     * 跳转列表页面
     * @return
     */
    /*@GetMapping(value = "/toUserList")
    public ModelAndView toUserList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/user-list");
        return mv;
    }*/


    /**
     * 查询所有用户信息
     * @return
     */
    @GetMapping(value = "/findAll")
    public LbMap findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String jsonStr){
        try {
//            String jsonStr = "";
            LbMap map = LbMap.fromObject(jsonStr);
            logger.info(jsonStr + "||" + map.toString());
            PageInfo<User> pages = userService.findAll(page, limit, map);
            logger.info("查询成功");
            return LbMap.successResult("用户查询成功", pages.getList(), pages.getTotal());
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
            PageInfo<LbMap> pages = userService.findByRoleId(page, limit, roleId);

            List<LbMap> newList = pages.getList();
            for (LbMap userMap : newList) {
                if (userMap.getInt("checked")==1){
                    userMap.put("LAY_CHECKED", true, false);
                }else {
                    userMap.put("LAY_CHECKED", false, false);
                }
            }

            logger.info("查询成功");
            return LbMap.successResult("用户查询成功", newList, pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("用户查询失败，"+e.getMessage());
        }
    }


    /**
     * 跳转新增页面
     * @return
     */
    /*@GetMapping(value = "/toAdd")
    public ModelAndView toAdd(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/user-add");
        return mv;
    }*/

    /**
     * 通过编号查询用户信息
     * @param userId
     * @return
     */
    /*@GetMapping(value = "/toUpdate")
    public ModelAndView toUpdate(String userId){
        ModelAndView mv = new ModelAndView();
        try {
            if (userId.equals("")){
                throw new Exception("没有找到用户编号");
            }

            User user = userService.findById(userId);
            mv.addObject("user", user);
            mv.setViewName("user/user-update");
            logger.info("查询成功");
            return mv;
        }catch (Exception e){
            mv.setViewName("system/error");
            mv.addObject("errorMsg", e.getMessage());
            logger.info("查询成功");
            return mv;
        }
    }*/

    /**
     * 新增和修改
     * @param userStr
     * @return
     */
    @PostMapping(value = "/edit")
    public LbMap edit(@RequestParam("user") String userStr) {
        try {
            User user = JSONUtil.jsonStringToClass(userStr, User.class);
            if (user.getUsername().equals("")){
                return LbMap.failResult("用户编辑失败，用户名称不能为空！");
            }else if (user.getTelNo().equals("")){
                return LbMap.failResult("用户编辑失败，用户电话不能为空！");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.setCreateTime(dateFormat.parse(dateFormat.format(new Date())));
            user.setDeleteFlag(0);

            //新增
            if (StringUtils.isEmpty(user.getUserId())){
                List<User> userList = userService.checkUserInfo(user.getTelNo(), user.getUsername());

                if (userList!=null&&userList.size()>0){
                    for (User oldUser : userList) {
                        if (oldUser.getUsername().equals(user.getUsername())){
                            return LbMap.failResult("用户名称已经存在");
                        }else if (oldUser.getTelNo().equals(user.getTelNo())){
                            return LbMap.failResult("用户电话已经存在");
                        }
                    }
                }

                if (!user.getPassword().equals("")){
                    user.setPassword(MD5Util.md5(user.getPassword(), user.getTelNo()));
                }

                //初始化的行版本号为0
                user.setRowVersion(0);
                //ID为空的为新增
                userService.save(user);
            }else {
                //ID不为空的为修改
                userService.update(user);
            }

            return LbMap.successResult("用户编辑成功");
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
            userService.delete(userId, rowVersion);
            return LbMap.successResult("用户删除成功");
        }catch (Exception e){
            return LbMap.failResult("用户删除失败，"+e.getMessage());
        }
    }

    /**
     * 查找用户对应的岗位
     * @param userId
     * @return
     */
    @GetMapping(value = "/findRoleByUserId")
    public LbMap findRoleByUserId(@RequestParam("userId") String userId){
        try {
            if (userId.equals("")){
                return LbMap.failResult("查找用户对应的岗位失败，没有找到用户编号！");
            }
            List<LbMap> roleList = userService.findRoleByUserId(userId);

            return LbMap.successResult("查找用户对应的岗位成功", roleList);
        }catch (Exception e){
            return LbMap.failResult("查找用户对应的岗位失败，"+e.getMessage());
        }
    }


    /**
     * 通过编号查找用户信息
     * @param userId
     * @return
     */
    @GetMapping(value = "/findById")
    public LbMap findById(@RequestParam("userId") String userId){
        try {
            if (userId.equals("")){
                return LbMap.failResult("通过编号查找用户信息失败，没有找到用户编号！");
            }
            User user = userService.findById(userId);
            return LbMap.successResult("通过编号查找用户信息成功", user);
        }catch (Exception e){
            return LbMap.failResult("通过编号查找用户信息失败，"+e.getMessage());
        }
    }

    /**
     * 更新用户信息
     * @param userStr
     * @param useRowVersion
     * @return
     */
    @PostMapping(value = "/update")
    public LbMap update(String userStr, boolean useRowVersion){
        try {
            User user = JSONUtil.jsonStringToClass(userStr, User.class);
            if (user==null){
                return LbMap.failResult("更新用户信息失败，没有找到用户编号！");
            }
            userService.update(user, useRowVersion);
            return LbMap.successResult("更新用户信息成功", user);
        }catch (Exception e){
            return LbMap.failResult("更新用户信息失败，"+e.getMessage());
        }
    }

    /**
     * 通过电话查询用户信息
     * @param telNo
     * @return
     */
    @GetMapping(value = "/findByTelNo")
    public LbMap findByTelNo(String telNo){
        try {
            if (telNo.equals("")){
                return LbMap.failResult("查询用户信息失败，没有找到用户编号！");
            }

            User user = userService.findByTelNo(telNo);
            return LbMap.successResult("查询用户信息成功", JSONUtil.classToJsonString(user));
        }catch (Exception e){
            return LbMap.failResult("查询用户信息失败，"+e.getMessage());
        }
    }
}
