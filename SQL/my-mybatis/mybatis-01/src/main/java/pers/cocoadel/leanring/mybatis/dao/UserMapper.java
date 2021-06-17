package pers.cocoadel.leanring.mybatis.dao;

import org.apache.ibatis.annotations.Select;
import pers.cocoadel.leanring.mybatis.domain.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    @Select("select * from users")
    List<User> findAll();

    List<User> selectAll();

    List<User> selectPage(int page,int pageSize);

    User selectById(int id);

    List<User> selectByMap(Map<String, Object> map);

    List<User> selectLike(String keyword);

    int save(User user);

    int updateByMap(Map<String, Object> map);

    int update(User user);

    int delete(int id);
}
