package by.bsu.rfct.fclearn.config;

import by.bsu.rfct.fclearn.entity.CardStatus;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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

    @Bean
    public Map<Long, Map<Long, Queue<CardStatus>>> userCollectionBasketMap(){
        Map<Long, Map<Long, Queue<CardStatus>>> userCollectionBasketMap = new HashMap<>();
        return userCollectionBasketMap;
    }

    @Bean
    @Scope(scopeName = "prototype")
    public Queue<CardStatus> cardStatusQueue(){
        Queue<CardStatus> cardStatusQueue = new LinkedList<>();

        cardStatusQueue.add(CardStatus.NEW);
        cardStatusQueue.add(CardStatus.HIGH);
        cardStatusQueue.add(CardStatus.NEW);
        cardStatusQueue.add(CardStatus.HIGH);
        cardStatusQueue.add(CardStatus.NEW);
        cardStatusQueue.add(CardStatus.MEDIUM);
        cardStatusQueue.add(CardStatus.HIGH);
        cardStatusQueue.add(CardStatus.NEW);
        cardStatusQueue.add(CardStatus.MEDIUM);
        cardStatusQueue.add(CardStatus.HIGH);
        cardStatusQueue.add(CardStatus.NEW);
        cardStatusQueue.add(CardStatus.HIGH);
        cardStatusQueue.add(CardStatus.NEW);
        cardStatusQueue.add(CardStatus.LOW);
        cardStatusQueue.add(CardStatus.NEW);
        cardStatusQueue.add(CardStatus.MEDIUM);
        cardStatusQueue.add(CardStatus.NEW);
        cardStatusQueue.add(CardStatus.HIGH);
        cardStatusQueue.add(CardStatus.LOW);
        cardStatusQueue.add(CardStatus.NEW);
        cardStatusQueue.add(CardStatus.MEDIUM);
        cardStatusQueue.add(CardStatus.NEVER);

        return cardStatusQueue;
    }
}
