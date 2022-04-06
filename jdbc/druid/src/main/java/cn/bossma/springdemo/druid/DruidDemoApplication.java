package cn.bossma.springdemo.druid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@Slf4j
public class DruidDemoApplication implements CommandLineRunner {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DruidDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        try (var conn = jdbcTemplate.getDataSource().getConnection();) {
            log.info("Connection is {}", conn.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Count is {}",jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PERSON",Long.class));
    }
}
