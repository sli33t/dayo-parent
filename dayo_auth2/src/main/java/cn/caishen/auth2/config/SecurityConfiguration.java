package cn.caishen.auth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cn.caishen.auth2.dao.UserDao;
import cn.caishen.auth2.service.AuthUserDetailsService;


@Configuration
@EnableWebSecurity //开启Web 资源的保护功能
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDao userDao;		
	
	@Autowired
	AuthUserDetailsService authUserDetailsService;	
	
	// 请配置这个，以保证在刷新Token时能成功刷新
	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		// 配置用户来源于数据库
	    // 配置密码加密方式  BCryptPasswordEncoder，添加用户加密的时候请也用这个加密
		auth.userDetailsService(authUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}	
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
    	//配置了AuthenticationManager, 密码类型的验证才会开启
        return super.authenticationManagerBean();
    }	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers().anyRequest()
			.and().authorizeRequests().antMatchers("/oauth/**").permitAll();
	}		
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//配置用户信息源、密码加密的策略。			
	    auth.userDetailsService(authUserDetailsService)
	    	.passwordEncoder(new BCryptPasswordEncoder());   //在创建用户时，密码加密也必须使用这个类。 
	}		
}
