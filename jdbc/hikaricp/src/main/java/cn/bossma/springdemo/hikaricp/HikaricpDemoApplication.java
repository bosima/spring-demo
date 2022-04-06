package cn.bossma.springdemo.hikaricp;

import com.zaxxer.hikari.pool.ProxyConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Slf4j
public class HikaricpDemoApplication implements CommandLineRunner {
    @Autowired
    JdbcTemplate jdbcTemplate;

    ExecutorService executors = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        SpringApplication.run(HikaricpDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Integer> taskList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            taskList.add(i);
        }

        var futures = taskList.stream().map(i -> CompletableFuture.runAsync(() -> {
            try {
                ProxyConnection conn = (ProxyConnection) jdbcTemplate.getDataSource().getConnection();
                Class clazz = ProxyConnection.class;
                Field field = clazz.getDeclaredField("poolEntry");
                field.setAccessible(true);
                var poolEntry = field.get(conn);
                log.info("Connection {} is {}", i, poolEntry.hashCode());
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }, executors)).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures);
    }
}
