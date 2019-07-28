package pl.com.tutti.tuttiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import pl.com.tutti.tuttiserver.config.SecurityConfig;

@SpringBootApplication
public class TuttiServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TuttiServerApplication.class, args);
	}

}
