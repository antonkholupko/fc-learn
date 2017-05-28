package by.bsu.rfct.fclearn.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "by.bsu.rfct.fclearn.dao")
@PropertySource("classpath:db.properties")
public class ApplicationConfig {

    @Value("${db.driverName}")
    private String driverName;
    @Value("${db.url}")
    private String url = "url";
    @Value("${db.login}")
    private String login;
    @Value("${db.password}")
    private String dbPassword;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setUsername(login);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean
    @Scope(scopeName = "prototype")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
