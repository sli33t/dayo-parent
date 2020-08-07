package cn.caishen.example.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class AuthUtils {
	public static String getReqUser(HttpServletRequest req) {
		String header = req.getHeader("Authorization");
		String token = StringUtils.substringAfter(header, "Bearer");
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey("SigningKey".getBytes("UTF-8")).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
		String localUser = (String) claims.get("userinfo");
		// 拿到当前用户
		return localUser;
	}

}
