package pl.com.tutti.tuttiserver.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.logging.Logger;

@Configuration
@PropertySource("classpath:application.yml")
public class ServerConfig {

    @Autowired
    private Environment environment;

    private Logger logger = Logger.getLogger(getClass().getName());

//    @Bean
//    public DataSource secutiyDataSource() {
//
//        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
//
//        try {
//            securityDataSource.setDriverClass(environment.getProperty("spring.datasource.driver-class-name"));
//        } catch (PropertyVetoException e) {
//            throw new RuntimeException(e);
//        }
//
//        logger.info(">>> jdbc.url= " + environment.getProperty("spring.datasource.url"));
//        logger.info(">>> jdbc.user= " + environment.getProperty("spring.datasource.username"));
//
//        securityDataSource.setJdbcUrl(environment.getProperty("spring.datasource.url"));
//        securityDataSource.setUser(environment.getProperty("spring.datasource.username"));
//        securityDataSource.setPassword(environment.getProperty("spring.datasource.password"));
//
//        securityDataSource.setInitialPoolSize(getIntegerProperty("connection.pool.initialPoolSize"));
//        securityDataSource.setMinPoolSize(getIntegerProperty("connection.pool.minPoolSize"));
//        securityDataSource.setMaxPoolSize(getIntegerProperty("connection.pool.maxPoolSize"));
//        securityDataSource.setMaxIdleTime(getIntegerProperty("connection.pool.maxIdleTime"));
//
//        return securityDataSource;
//    }
//
//    private  Integer getIntegerProperty(String propertyName) {
//        String propertyValue = environment.getProperty(propertyName);
//
//        Integer integerProperty = Integer.parseInt(propertyValue);
//
//        return  integerProperty;
//    }
}
