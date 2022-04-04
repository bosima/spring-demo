package cn.bossma.springdemo.transactiontemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootApplication
@Slf4j
public class TransactionTemplateApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public static void main(String[] args) {
        SpringApplication.run(TransactionTemplateApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("start...");
        log.info("First count is {}", getCount());
        transactionTemplate.executeWithoutResult(status->{
            jdbcTemplate.execute("INSERT INTO PERSON(NAME,CITY) VALUES('hello','world')");
            log.info("Second count is {}", getCount());
            status.setRollbackOnly();
        });
        log.info("Third count is {}", getCount());
    }

    private Long getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PERSON", Long.class);
    }
}
