package cn.bossma.springdemo;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
@XSlf4j
public class JdbcDataSourceDemoApplication implements CommandLineRunner {
    @Autowired
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
