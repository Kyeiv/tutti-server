package pl.com.tutti.tuttiserver.config;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import pl.com.tutti.tuttiserver.config.authentication.MyUserDetails;
import pl.com.tutti.tuttiserver.config.authentication.RestAuthenticationFailureHandler;
import pl.com.tutti.tuttiserver.config.authentication.RestAuthenticationSuccessHandler;
import pl.com.tutti.tuttiserver.config.webfilter.JsonObjectAuthenticationFilter;
import pl.com.tutti.tuttiserver.config.webfilter.JwtAuthorizationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationSuccessHandler authenticationSuccessHandler;
    private final RestAuthenticationFailureHandler authenticationFailureHandler;
    private final MyUserDetails userDetailsManager;
    private final String secret;

    public SecurityConfig(RestAuthenticationSuccessHandler authenticationSuccessHandler,
                          RestAuthenticationFailureHandler authenticationFailureHandler,
						  MyUserDetails userDetailsManager,
                          @Value("${jwt.secret}") String secret,
                          DataSource dataSource) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.userDetailsManager = userDetailsManager;
        this.secret = secret;
        securityDataSource = dataSource;
    }

	private DataSource securityDataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}

	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(new ArrayList<String>(Arrays.asList("*")));
        configuration.setAllowedMethods(new ArrayList<String>(Arrays.asList("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH")));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the data's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight data
        // will fail with 403 Invalid CORS data
        configuration.setAllowedHeaders(new ArrayList<String>(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "access-control-allow-origin")));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

	@Bean
	public JsonObjectAuthenticationFilter authenticationFilter() throws Exception {
		JsonObjectAuthenticationFilter filter = new JsonObjectAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(authenticationFailureHandler);
		filter.setAuthenticationManager(super.authenticationManagerBean());
		return filter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsManager);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
				.cors()
				.and()
				.authorizeRequests()
					.antMatchers("/auth/**")
					.permitAll()
					.and()
				.authorizeRequests()
					.anyRequest()
					.authenticated()
					.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 1
				.and()
				.addFilter(authenticationFilter())
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsManager, secret)) // 2
				.exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
	}

}
