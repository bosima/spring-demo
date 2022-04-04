package cn.bossma.springdemo;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
@XSlf4j
public class JdbcDataSourceDemoApplication implements CommandLineRunner {

    // Autowired 会自动进行装配，但是如果有两个数据源就搞不懂应该用哪个，有两个办法
    // 1、通过给DataSource添加@Primary注解
    // 2、使用@Qualifier指定 Qualifier多个时才使用
    @Autowired
    @Qualifier("barDataSource")
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(JdbcDataSourceDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        showConnection();
    }

    private void showConnection() throws SQLException {
        log.entry(1, 2, 3, 4);
        log.info(dataSource.toString());
        var connection = dataSource.getConnection();
        log.info(connection.toString());
        connection.close();
        log.exit(1);
    }
}
