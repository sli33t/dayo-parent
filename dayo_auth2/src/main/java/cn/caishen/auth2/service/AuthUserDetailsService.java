package cn.caishen.auth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cn.caishen.auth2.dao.UserDao;
import cn.caishen.domain.utils.DayoList;
import cn.caishen.domain.utils.DayoMap;

@Service
public class AuthUserDetailsService implements UserDetailsService{

	@Autowired
	UserDao userDao;	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 // 通过用户名获取用户信息	 
		 DayoList users = userDao.getUser(username);		 

		 if (users.size() > 0) {			 
			 DayoMap u = users.get(0);
			 //FUserID, FName, FPassWord
			 //long userID = u.getInt("FUserID".toLowerCase()); 
			 String name = u.getString("User_Name".toLowerCase());
			 String password = u.getString("PASSWORD".toLowerCase()); 

			 //注册时，如果存入数据库是加过密后，直接返回就可以，否则要加密返回
			 //BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			 //password = bCryptPasswordEncoder.encode(password);			 

			 //创建spring security安全用户和对应的权限（从数据库查找）
			 User user = new User(name, password, AuthorityUtils.createAuthorityList("admin", "manager"));			 
		
			 return user;
		 } else {
			 throw new UsernameNotFoundException("用户[" + username + "]不存在");
		 }		
			
	}	 
}
