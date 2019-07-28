package pl.com.tutti.tuttiserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.SessionManagementFilter;

import pl.com.tutti.tuttiserver.config.webfilter.CorsFilter;
import pl.com.tutti.tuttiserver.config.webfilter.CsrfTokenResponseHeaderBindingFilter;
import pl.com.tutti.tuttiserver.rest.authentication.SimpleAuthenticationEntryPoint;
import pl.com.tutti.tuttiserver.rest.authentication.SimpleAuthenticationFailureHandler;
import pl.com.tutti.tuttiserver.rest.authentication.SimpleAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private SimpleAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private SimpleAuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private SimpleAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication().withUser("user").password("{noop}user").roles("USER").and().withUser("admin")
		.password("{noop}admin").roles("ADMIN");
	}
	
	@Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	    http
	    .addFilterBefore(corsFilter(), SessionManagementFilter.class) 
	    .authorizeRequests() 
	    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	    .antMatchers("/auth/**").authenticated()
	    .antMatchers("/api/**").authenticated()
	    .and()
	    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
	    .and()
		.formLogin()
		.successHandler(authenticationSuccessHandler)
		.failureHandler(authenticationFailureHandler)
		.and()
		.logout().logoutSuccessUrl("/")
	    .and()
	    .csrf().disable()
		.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);	
	}

}
