package cn.bossma.springdemo.jdbcexception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class JdbcExceptionDemoApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testThrowingCustomException() {
        Assertions.assertThrows(CustomDuplicateKeyException.class, () -> {
            jdbcTemplate.execute("INSERT INTO PERSON(ID,NAME,CITY) VALUES(100,'hello','world')");
            jdbcTemplate.execute("INSERT INTO PERSON(ID,NAME,CITY) VALUES(100,'hello','world')");
        });
    }
}
