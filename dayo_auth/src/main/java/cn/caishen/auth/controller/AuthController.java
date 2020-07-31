package cn.caishen.auth.controller;

import cn.caishen.auth.config.JwtProperties;
import cn.caishen.domain.auth.entity.Payload;
import cn.caishen.domain.auth.jwtUtils.JwtUtil;
import cn.caishen.domain.domain.po.User;
import cn.caishen.domain.utils.CookieUtils;
import cn.caishen.domain.utils.JSONUtil;
import cn.caishen.domain.utils.LbMap;
import cn.caishen.domain.utils.MD5Util;
import cn.caishen.service.DayoUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class AuthController {

    protected static Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    private DayoUserService dayoUserService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping(value = "/loginAuth")
    public LbMap loginAuth(@RequestParam("telNo") String telNo, @RequestParam("password") String password,
                           HttpServletResponse response, HttpServletRequest request){
        try {
            LbMap userMap = dayoUserService.findByTelNo(telNo);
            User user = JSONUtil.jsonStringToClass(userMap.getString("data"), User.class);

            if (!user.getPassword().equals(MD5Util.md5(password, telNo))){
                return LbMap.failResult("密码不正确");
            }

            //UserInfo userInfo = new UserInfo(user.getUserId(), user.getTelNo(), user.getUsername());

            String token = JwtUtil.generateTokenExpireInMinutes(user, jwtProperties.getPrivateKey(), jwtProperties.getUser().getExpire());
            // 写入cookie
            CookieUtils.newCookieBuilder()
                    .response(response) // response,用于写cookie
                    .httpOnly(true) // 保证安全防止XSS攻击，不允许JS操作cookie
                    .domain(jwtProperties.getUser().getCookieDomain()) // 设置domain
                    .name(jwtProperties.getUser().getCookieName()).value(token) // 设置cookie名称和值
                    .build();// 写cookie

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            LbMap resultMap = LbMap.successResult("用户登录成功", JSONUtil.classToJsonString(user));
            resultMap.put(jwtProperties.getUser().getCookieName(), token);
            return resultMap;
        }catch (Exception e){
            return LbMap.failResult("用户登录异常");
        }
    }


    @PostMapping(value = "/setToken")
    public LbMap setToken(@RequestParam("userStr") String userStr, HttpServletResponse response){
        try {
            User user = JSONUtil.jsonStringToClass(userStr, User.class);
            String token = JwtUtil.generateTokenExpireInMinutes(user, jwtProperties.getPrivateKey(), jwtProperties.getUser().getExpire());
            // 写入cookie
            CookieUtils.newCookieBuilder()
                    .response(response) // response,用于写cookie
                    .httpOnly(true) // 保证安全防止XSS攻击，不允许JS操作cookie
                    .domain(jwtProperties.getUser().getCookieDomain()) // 设置domain
                    .name(jwtProperties.getUser().getCookieName()).value(token) // 设置cookie名称和值
                    .build();// 写cookie
            return LbMap.successResult("用户登录成功", user);
        }catch (Exception e){
            return LbMap.failResult("用户登录异常");
        }

    }


    @PostMapping(value = "/getUserByToken")
    public LbMap getUserByToken(@RequestParam("token") String token, HttpServletRequest request){
        try {
            // 读取cookie
            //String token = CookieUtils.getCookieValue(request, jwtProperties.getUser().getCookieName());
            if (token.equals("")){
                logger.info("token令牌为空");
                return LbMap.failResult("令牌不能为空");
            }
            // 获取token信息
            Payload<User> payLoad = JwtUtil.getInfoFromToken(token, jwtProperties.getPublicKey(), User.class);
            User user = payLoad.getUserInfo();
            if (user==null){
                logger.info("用户为空");
                return LbMap.failResult("没有找到用户，请重新登录");
            }
            logger.info("用户获取成功");
            return LbMap.successResult("用户获取成功", JSONUtil.classToJsonString(user));
        } catch (Exception e) {
            // 抛出异常，证明token无效，直接返回401
            return LbMap.failResult("用户获取失败："+e.getMessage());
        }
    }
}
