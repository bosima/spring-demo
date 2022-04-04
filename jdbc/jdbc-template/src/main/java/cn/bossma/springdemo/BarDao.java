package cn.bossma.springdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;

@Repository
@Slf4j
public class BarDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert jdbcInsert;

    public void insertData() {
        Arrays.asList("a", "b", "c").forEach(foo -> {
            jdbcTemplate.update("INSERT INTO BAR(FOO) VALUES (?)", foo);
        });

        HashMap<String, String> rows = new HashMap<>();
        rows.put("FOO", "d");
        Number id = jdbcInsert.executeAndReturnKey(rows);
        log.info("id of d is {}", id.longValue());
    }

    public void listData() {
        // 查询表中的数据条数
        var infoCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM BAR", Long.class);
        log.info("Count is {}", infoCount);

        // 查询单个字段的列表
        var singleFiledList = jdbcTemplate.queryForList("SELECT FOO FROM BAR", String.class);
        singleFiledList.forEach(foo -> {
            log.info("Foo is {}", foo);
        });

        // 查询多个字段的列表
        var objectList = jdbcTemplate.query("SELECT * FROM BAR", (rs, rowNum) -> Bar.builder()
                .id(rs.getLong(1))
                .foo(rs.getString(2))
                .build());
        objectList.forEach(bar -> {
            log.info("Bar is {}", bar);
        });
    }
}
