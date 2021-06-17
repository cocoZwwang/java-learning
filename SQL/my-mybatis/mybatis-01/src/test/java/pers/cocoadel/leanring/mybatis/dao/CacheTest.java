package pers.cocoadel.leanring.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pers.cocoadel.leanring.mybatis.domain.User;
import pers.cocoadel.leanring.mybatis.utils.MybatisUtil;

import java.util.List;

public class CacheTest {


    @Test
    public void cacheTest1() {
        //结果 selectAll 查询的结果，并不能称为 selectById 的缓存
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            System.out.println("第一次 selectAll");
            List<User> users = mapper.selectAll();
            System.out.println("第二次 selectAll");
            mapper.selectAll();
            System.out.println("第一次根据 id 查询");
            User user = mapper.selectById(1);
            System.out.println("第二次根据 id 查询");
            mapper.selectById(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void  cacheTest2(){
        //第一次 selectAll 后  save user，新插入的 user 并不能动态修改缓存
        //也就是 save user 后 selectAll 需要重新查询
        //其实 mybatis 任何的 insert update delete 操作都会刷新缓存
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            System.out.println("第一次 selectAll");
            mapper.selectAll();
            User user = new User(6, "cocoadel", "123456");
            System.out.println("save user..........");
            mapper.save(user);
            sqlSession.commit();
            System.out.println("第二次 selectAll");
            mapper.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearCacheTest() {
        try (SqlSession session = MybatisUtil.getSqlSession()){
            UserMapper mapper = session.getMapper(UserMapper.class);
            System.out.println("第一次 selectAll");
            mapper.selectAll();
            System.out.println("第二次 selectAll");
            mapper.selectAll();
            //手动清理缓存
            session.clearCache();
            System.out.println("第三次 selectAll");
            mapper.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void twoLevelCacheTest() {
        SqlSession sqlSession1 = MybatisUtil.getSqlSession();
        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        System.out.println("sqlSession1 查询");
        mapper1.selectAll();
        //这里必须先关闭了，二级缓存才会起作用，因为关闭了一级缓存才会提交到二级缓存。
        sqlSession1.close();


        SqlSession sqlSession2 = MybatisUtil.getSqlSession();
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        System.out.println("sqlSession2 查询");
        mapper2.selectAll();

        sqlSession2.close();
    }
}
