package pers.cocoadel.leanring.mybatis.dao;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pers.cocoadel.leanring.mybatis.domain.User;
import pers.cocoadel.leanring.mybatis.utils.MybatisUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class UserDaoTest {

    @Test
    public void selectAll() {

        try (SqlSession session = MybatisUtil.getSqlSession()) {
            //第一种使用方法
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> users = mapper.selectAll();

            //第二种使用方法 (这是旧的使用方法，不推荐使用)
//            List<User> users = session.selectList("pers.cocoadel.leanring.mybatis.dao.UserMapper.selectAll");
            users.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void selectByAnnotation() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.findAll();
            users.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectPage() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.selectPage(1, 2);
            users.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void selectRowBounds() {
        //使用 RowBounds 进行分页
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()) {
            RowBounds rowBounds = new RowBounds(1, 2);
            List<User> users =
                    sqlSession.selectList("pers.cocoadel.leanring.mybatis.dao.UserMapper.selectAll", null, rowBounds);
            users.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectById() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()) {
            UserMapper userDao = sqlSession.getMapper(UserMapper.class);
            User user = userDao.selectById(1);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insert() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()) {
            UserMapper userDao = sqlSession.getMapper(UserMapper.class);
            int res = userDao.save(new User(5, "cocoAdel", "123"));
            if (res > 0) {
                System.out.println(" 插入成功！！");
            }
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = new User(5, "cocoAdel", "123456");
            int res = mapper.update(user);
            if (res > 0) {
                System.out.println("更新成功！！！");
            }
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int delete = mapper.delete(5);
            if (delete > 0) {
                System.out.println("删除成功！！！");
            }
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectLike() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.selectLike("r");
            users.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateByMap() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<>();
            map.put("userId", 4);
            map.put("password", "1234567889");
            int res = mapper.updateByMap(map);
            if (res > 0) {
                System.out.println("updateByMap successful!!!");
            }
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectByMap() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<>();
            map.put("userName", "ruby");
            map.put("password", "123456");
            List<User> users = mapper.selectByMap(map);
            users.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}