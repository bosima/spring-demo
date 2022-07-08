package cn.bossma.springdemo.mybatis.mapper;

import cn.bossma.springdemo.mybatis.model.Product;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProductMapper {
    @Insert("insert into t_product(name,desc,price,create_time,update_time) " +
            "values(#{name},#{description},#{price},now(),now())"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Product product);

    @Select("select * from t_product where id=#{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "desc", property = "description"),
            //@Result(column = "create_time",property="createTime")
    })
    Product findById(@Param("id") Long id);
}
