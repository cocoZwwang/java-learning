package pers.cocoadel.leanring.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pers.cocoadel.leanring.mybatis.domain.User;
import pers.cocoadel.leanring.mybatis.utils.MybatisUtil;

import java.util.List;

import static org.junit.Assert.*;

public class UserAnnotationMapperTest {

    @Test
    public void selectAll() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            UserAnnotationMapper mapper = sqlSession.getMapper(UserAnnotationMapper.class);
            List<User> users = mapper.selectAll();
            users.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectById() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            UserAnnotationMapper mapper = sqlSession.getMapper(UserAnnotationMapper.class);
            User user = mapper.selectById(1);
            System.out.println(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void save() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            UserAnnotationMapper mapper = sqlSession.getMapper(UserAnnotationMapper.class);
            int res = mapper.save(new User(5, "cocoadel", "123456"));
            if (res > 0) {
                System.out.println("插入成功！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            UserAnnotationMapper mapper = sqlSession.getMapper(UserAnnotationMapper.class);
            User user = new User(5, "cocoadel", "123");
            int update = mapper.update(user);
            if (update > 0) {
                System.out.println("更新成功！！！");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            UserAnnotationMapper mapper = sqlSession.getMapper(UserAnnotationMapper.class);
            int res = mapper.delete(5);
            if (res > 0) {
                System.out.println("删除成功！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}