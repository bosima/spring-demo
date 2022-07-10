package cn.bossma.springdemo.mybatismysql.mapper;

import cn.bossma.springdemo.mybatismysql.model.BaseProduct;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface ProductMapper extends BaseProductMapper {

    @Select({
        "select",
        "id, `name`, price, `desc`, create_time, update_time",
        "from t_product",
        "where create_time >= #{createTime,jdbcType=TIMESTAMP}"
    })
    @ResultMap("cn.bossma.springdemo.mybatismysql.mapper.BaseProductMapper.BaseResultMap")
    List<BaseProduct> selectByCreateTime(Date createTime);

    // Defined in ProductMapper.xml
    List<BaseProduct> selectByName(String name);

    @Select("select * from t_product order by id")
    @ResultMap("cn.bossma.springdemo.mybatismysql.mapper.BaseProductMapper.BaseResultMap")
    List<BaseProduct> selectList(PageRowBounds rowBounds);

    @Select("select * from t_product order by id")
    @ResultMap("cn.bossma.springdemo.mybatismysql.mapper.BaseProductMapper.BaseResultMap")
    List<BaseProduct> selectByPageNumSize(@Param("pageNum") int pageNum,
                                          @Param("pageSize") int pageSize);
}