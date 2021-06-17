package pers.cocoadel.leanring.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.cocoadel.leanring.mybatis.domain.User;

import java.util.List;

public interface UserAnnotationMapper {
    @Select("select * from users")
    List<User> selectAll();

    @Select("select * from users where id = #{id}")
    User selectById(@Param("id") int id);

    @Select("insert into users (id,name,pwd) values (#{id},#{name},#{password})")
    int save(User user);

    @Select("update users set name=#{name},pwd = #{password} where id = #{id}")
    int update(User user);

    @Select("delete from users where id = #{id}")
    int delete(@Param("id") int id);


}
