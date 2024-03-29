package cn.caishen.worklog.controller.system;

import cn.caishen.domain.utils.LbMap;
import cn.caishen.worklog.controller.BaseController;
import cn.caishen.worklog.service.system.SelectService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/select")
public class SelectController extends BaseController {

    @Autowired
    private SelectService selectService;

    //private final static Logger logger = (Logger) LoggerFactory.getLogger(SelectController.class);

    @GetMapping(value = "/toSelectTable")
    public ModelAndView toSelectTable(String tableUrl){
        ModelAndView mv = new ModelAndView();
        try {
            mv.addObject("tableUrl", tableUrl);
            mv.setViewName("common/selectTable");
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
     * 查询区域
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/findArea")
    public LbMap findArea(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        try {
            PageInfo<LbMap> pages = selectService.findArea(page, limit);
            logger.info("findArea查询成功");
            return LbMap.successResult("findArea查询成功", pages.getList(), pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("findArea查询失败，"+e.getMessage());
        }
    }


    /**
     * 查询用户
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/findUser")
    public LbMap findUser(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        try {
            PageInfo<LbMap> pages = selectService.findUser(page, limit);
            logger.info("findUser查询成功");
            return LbMap.successResult("findUser查询成功", pages.getList(), pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("findUser查询失败，"+e.getMessage());
        }
    }


    /**
     * 查询区域
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/findCustomer")
    public LbMap findCustomer(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        try {
            PageInfo<LbMap> pages = selectService.findCustomer(page, limit);
            logger.info("findCustomer查询成功");
            return LbMap.successResult("findCustomer查询成功", pages.getList(), pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("findCustomer查询失败，"+e.getMessage());
        }
    }

    /**
     * 跳转文件上传
     * @return
     */
    @GetMapping(value = "/toFileUpload")
    public ModelAndView toFileUpload(){
        ModelAndView mv = new ModelAndView();
        try {
            mv.setViewName("common/fileUpload");
            logger.info("查询成功");
            return mv;
        }catch (Exception e){
            mv.setViewName("system/error");
            mv.addObject("errorMsg", e.getMessage());
            logger.info("查询失败");
            return mv;
        }
    }
}
