package cn.caishen.worklog.controller;

import cn.caishen.domain.domain.po.User;
import cn.caishen.domain.utils.JSONUtil;
import cn.caishen.domain.utils.LbMap;
import cn.caishen.service.DayoAuth2Service;
import cn.caishen.service.DayoAuthService;
import cn.caishen.worklog.config.RabbitProperties;
import cn.caishen.worklog.constant.MQConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录控制
 */
@RestController
public class LoginController {

    //private final static Logger logger = (Logger) LoggerFactory.getLogger(LoginController.class);

    protected static Logger logger = LogManager.getLogger(LoginController.class);

    /*@Autowired
    private Environment environment;*/

    @Autowired
    private RabbitProperties rabbitProperties;

    @Autowired
    private DayoAuthService dayoAuthService;

    @Autowired
    private DayoAuth2Service dayoAuth2Service;

    /**
     * 跳转登录页面
     * @return
     */
    @GetMapping(value = "/")
    public ModelAndView toLogin(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("system/login");
        return mv;
    }

    /**
     * 登录方法
     * @param telNo
     * @param password
     * @param request
     * @return
     */
    /*@PostMapping(value = "/login")
    public LbMap login(String telNo, String password, HttpServletRequest request) {
        if (StringUtils.isEmpty(telNo)||StringUtils.isEmpty(password)){
            return LbMap.failResult("用户名或者密码不能为空！");
        }

        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(telNo, password);
            subject.login(token);
            User user = (User) subject.getPrincipal();
            if (user!=null){
                String userStr = JSON.toJSONString(user);
                HttpSession session = request.getSession();
                session.setAttribute("user", userStr);

                LbMap rabbitMap = new LbMap();
                *//*String rbHost = environment.getProperty("spring.rabbitmq.host");
                String rbPort = environment.getProperty("spring.rabbitmq.port");
                String rbUsername = environment.getProperty("spring.rabbitmq.username");
                String rbPassword = environment.getProperty("spring.rabbitmq.password");
                String rbVirtualHost = environment.getProperty("spring.rabbitmq.virtual-host");*//*

                String rbHost = rabbitProperties.getHost();
                Integer rbPort = rabbitProperties.getPort();
                String rbUsername = rabbitProperties.getUsername();
                String rbPassword = rabbitProperties.getPassword();
                String rbVirtualHost = rabbitProperties.getVirtualhost();

                rabbitMap.put("rbHost", rbHost);
                rabbitMap.put("rbPort", rbPort);
                rabbitMap.put("rbUsername", rbUsername);
                rabbitMap.put("rbPassword", rbPassword);
                rabbitMap.put("rbVirtualHost", rbVirtualHost);

                session.setAttribute(MQConstant.RABBIT_MQ_SETTING, rabbitMap.toString());

                logger.info("登录成功："+user.toString());
                return LbMap.successResult("");
            }else {
                logger.info("登录失败："+token.getUsername()+"，密码："+ MD5Util.md5(password));
                return LbMap.failResult("用户名不存在");
            }
        }catch (Exception e){
            logger.info("登录失败："+e.getMessage());
            return LbMap.failResult("登录失败："+e.getMessage());
        }
    }*/


    /*@PostMapping(value = "/login")
    public LbMap login(String telNo, String password, HttpServletRequest request){
        try {
            LbMap lbMap = dayoAuthService.loginAuth(telNo, password);

            if (!lbMap.getBoolean("result")){
                return lbMap;
            }

            User user = JSONUtil.jsonStringToClass(lbMap.getString("data"), User.class);
            logger.info("登录成功："+user.toString());

            LbMap rabbitMap = new LbMap();

            String rbHost = rabbitProperties.getHost();
            Integer rbPort = rabbitProperties.getPort();
            String rbUsername = rabbitProperties.getUsername();
            String rbPassword = rabbitProperties.getPassword();
            String rbVirtualHost = rabbitProperties.getVirtualhost();

            rabbitMap.put("rbHost", rbHost);
            rabbitMap.put("rbPort", rbPort);
            rabbitMap.put("rbUsername", rbUsername);
            rabbitMap.put("rbPassword", rbPassword);
            rabbitMap.put("rbVirtualHost", rbVirtualHost);

            HttpSession session = request.getSession();
            session.setAttribute(MQConstant.RABBIT_MQ_SETTING, rabbitMap.toString());
            session.setAttribute("user", user);

            return lbMap;
        }catch (Exception e){
            logger.info("登录失败："+e.getMessage());
            return LbMap.failResult("登录失败："+e.getMessage());
        }
    }*/


    @PostMapping(value = "/login")
    public LbMap login(String telNo, String password, HttpServletRequest request) throws Exception {
        LbMap lbMap = dayoAuth2Service.loginAuth2(telNo, password, "password", "myapp", "all", "lxapp");
        return lbMap;
    }

    /**
     * 登出方法
     * @param request
     * @return
     */
    @GetMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        mv.setViewName("system/login");
        return mv;
    }


}
