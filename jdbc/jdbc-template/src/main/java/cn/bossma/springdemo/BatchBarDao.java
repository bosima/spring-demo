package cn.bossma.springdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class BatchBarDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void batchInsert() {
        List<Bar> list = new ArrayList<>();
        list.add(Bar.builder().id(100L).foo("f100").build());
        list.add(Bar.builder().id(101L).foo("f101").build());
        jdbcTemplate.batchUpdate("INSERT INTO BAR(FOO) VALUES(?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, list.get(i).getFoo() + "-" + i);
            }

            @Override
            public int getBatchSize() {
                return 2;
            }
        });

        List<Bar> list2 = new ArrayList<>();
        list2.add(Bar.builder().id(200L).foo("f200").build());
        list2.add(Bar.builder().id(201L).foo("f201").build());
        // 参数的名字要和类的字段名字完全匹配，包括大小写。
        namedParameterJdbcTemplate.batchUpdate("INSERT INTO BAR(ID,FOO) VALUES(:id,:foo)",
                SqlParameterSourceUtils.createBatch(list2));
    }
}
