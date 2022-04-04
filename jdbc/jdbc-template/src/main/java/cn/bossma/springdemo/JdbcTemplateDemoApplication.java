package cn.bossma.springdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class JdbcTemplateDemoApplication implements CommandLineRunner {
    @Autowired
    private BarDao barDao;

    @Autowired
    private BatchBarDao batchBarDao;

    public static void main(String[] args) {
        SpringApplication.run(JdbcTemplateDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        barDao.insertData();
        batchBarDao.batchInsert();
        barDao.listData();
    }
}
