package net.ion.system;
/*
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import kr.grid.services.UserService;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired UserDetailsService userService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		
		
		 * SpringSecurity 사용시. CsrfFilter 앞에 CharacterEncodingFilter를 놓아야 한다.
		 * http://millky.com/@origoni/post/1057?language=ko_kr
		 
		http.addFilterBefore(filter, CsrfFilter.class);
		http.authorizeRequests() //  요청에 대해서 권한 처리를 하는데
			.antMatchers("/").permitAll()
			.antMatchers("/index.do").permitAll()
			.antMatchers("/user/**").permitAll() // 권한처리 예외 URL패턴 이다.
			.anyRequest().authenticated() // 어떠한 요청에라도 인증을 요구한다.
				.and()
			.formLogin()
				.loginPage("/user/login.do") // <== 인터셉터 하게 된다.
				.defaultSuccessUrl("/")
				.permitAll()
				.and()
			.csrf().disable()
			.logout()
			.logoutUrl("/user/logout.do")
            .logoutSuccessUrl("/");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/login/register");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService)
			.passwordEncoder(userService.passwordEncoder());
	}
	
    @Override
    public UserDetailsService userDetailsServiceBean() {
        return userService;
    }

}
*/