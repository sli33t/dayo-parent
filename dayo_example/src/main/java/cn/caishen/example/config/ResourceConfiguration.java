package cn.caishen.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled=true) 
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {
	
	private static final String SOURCE_ID = "order";
	 
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
 
	@Override
	@CrossOrigin
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(SOURCE_ID).stateless(true);
		resources.tokenServices(defaultTokenServices());
	}
 
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
        // 我们这里放开/order/*的请求，以/order/*开头的请求不用认证
		http.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/user/**").permitAll()
				.antMatchers("/order/*").permitAll()
				.antMatchers("/worklog/*").permitAll()
				.and().authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.anyRequest().authenticated();
		// @formatter:on
	}
 
	// 自定义的Token存储器，存到Redis中
	@Bean
	public TokenStore tokenStore() {
		RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		return tokenStore;
	}
 
	// Token转换器
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
		};
		accessTokenConverter.setSigningKey("SigningKey");
		return accessTokenConverter;
	}
 
	/**
	 * 创建一个默认的资源服务token
	 */
	@Bean
	public ResourceServerTokenServices defaultTokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		// 使用自定义的Token转换器
		defaultTokenServices.setTokenEnhancer(accessTokenConverter());
		// 使用自定义的tokenStore
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}
}
