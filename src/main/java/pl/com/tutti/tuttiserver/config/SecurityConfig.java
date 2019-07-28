package pl.com.tutti.tuttiserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

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
		
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin")
		.password("admin").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	    http.authorizeRequests() 
	    .antMatchers("/auth/**").authenticated()
	    .antMatchers("/api/**").authenticated();
	    http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
		http.formLogin().successHandler(authenticationSuccessHandler);
		http.formLogin().failureHandler(authenticationFailureHandler);
		http.logout().logoutSuccessUrl("/");
		
	    // CSRF tokens handling
	    http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
	}

}
