package pl.com.tutti.tuttiserver.config;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
	
//	@Bean
//    CorsFilter corsFilter() {
//        CorsFilter filter = new CorsFilter();
//        return filter;
//    }
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(new ArrayList<String>(Arrays.asList("*")));
        configuration.setAllowedMethods(new ArrayList<String>(Arrays.asList("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH")));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(new ArrayList<String>(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "access-control-allow-origin")));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	    http
	    .cors().and()
//	    .addFilterBefore(corsFilter(), SessionManagementFilter.class)
	    .authorizeRequests() 
	    //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
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
