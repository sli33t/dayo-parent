package cn.caishen.worklog.controller;

import cn.caishen.domain.common.LoginException;
import cn.caishen.domain.constant.RequestConstant;
import cn.caishen.domain.domain.po.User;
import cn.caishen.domain.utils.CookieUtils;
import cn.caishen.domain.utils.DBUtil;
import cn.caishen.domain.utils.JSONUtil;
import cn.caishen.domain.utils.LbMap;
import cn.caishen.service.DayoAuthService;
import cn.caishen.service.DayoUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

public class BaseController {

    //private final static Logger logger = (Logger) LoggerFactory.getLogger(BaseController.class);

    protected static Logger logger = LogManager.getLogger(BaseController.class);

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    protected String userId;
    protected String username;
    protected String telNo;
    protected List<LbMap> roleList;
    protected Integer roleType;
    protected User user;

    @Autowired
    private DayoUserService dayoUserService;

    @Autowired
    private DayoAuthService dayoAuthService;

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        this.request = request;
        this.response = response;
        this.session = session;

        Enumeration<String> parameterNames = request.getParameterNames();
        String params = "";
        while (parameterNames.hasMoreElements()){
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            String paramValue = "";
            if (paramValues.length==1){
                paramValue = paramValues[0];
            }

            params = DBUtil.StringAdd(params, paramValue, "&");
        }

        logger.info("请求的地址："+request.getServletPath()+"|方法类型："+request.getMethod());
        logger.info("请求的参数："+params);
        logger.info("请求的token："+request.getHeader(RequestConstant.DAYO_TOKEN));

        //Object userStr = request.getSession().getAttribute("user");

        String token = CookieUtils.getCookieValue(request, RequestConstant.DAYO_TOKEN);
        LbMap userMap = dayoAuthService.getUserByToken(token);
        User user = null;
        if (userMap!=null){
            String userStr = userMap.getString("data");
            user = JSONUtil.jsonStringToClass(userStr, User.class);
        }

        if (user==null){
            throw new LoginException("用户没有登录");
        }else{
            //User user = JSONObject.parseObject(userStr.toString(), User.class);

            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.telNo = user.getTelNo();
            this.user = user;

            if (this.roleList==null||this.roleList.size()==0){
                LbMap resultMap = dayoUserService.findRoleByUserId(this.userId);
                if (resultMap!=null){
                    this.roleList = resultMap.getList("data");
                }
            }

            roleType = 1;
            if (roleList!=null&&roleList.size()>0){
                for (LbMap role : roleList) {
                    if (role.getInt("roleType")==0){
                        roleType = 0;
                    }
                }
            }
        }
    }
}
