package pl.com.tutti.tuttiserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.web.csrf.CsrfFilter;

import pl.com.tutti.tuttiserver.config.webfilter.CsrfTokenResponseHeaderBindingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// add our users for in memory authentication
		
		UserBuilder users = User.withDefaultPasswordEncoder();
		
		auth.inMemoryAuthentication()
			.withUser(users.username("john").password("test123").roles("EMPLOYEE"))
			.withUser(users.username("mary").password("test123").roles("EMPLOYEE", "MANAGER"))
			.withUser(users.username("susan").password("test123").roles("EMPLOYEE", "ADMIN"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*http.authorizeRequests()
		.antMatchers("/api/customers/**").authenticated()
		.antMatchers("/api/employees/**").authenticated()
		.and()
		.httpBasic()
		.and()
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
		
	    http.authorizeRequests()
	    .antMatchers("/auth/**").authenticated()
	    .antMatchers("/api/**").authenticated();
	    http.formLogin().successHandler(authenticationSuccessHandler);
	    http.formLogin().failureHandler(authenticationFailureHandler);
	    //.and()
	    //.httpBasic();
	    http.logout().logoutSuccessUrl("/");
	    // CSRF tokens handling
	    http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
	   
	}

}
