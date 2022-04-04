package cn.bossma.springdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@Slf4j
public class DeclarativeTransactionDemoApplication implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DeclarativeTransactionDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        personService.insertData();
        log.info("First count is {}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PERSON WHERE NAME='hello'", Long.class));

        try {
            personService.insertThenRollback();
        }catch (Exception e){
            log.info("Second count is {}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PERSON WHERE NAME='ni'", Long.class));
        }

        try {
            personService.invokeInsertThenRollback();
        }catch (Exception e){
            log.info("Third count is {}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PERSON WHERE NAME='ni'", Long.class));
        }
    }
}
