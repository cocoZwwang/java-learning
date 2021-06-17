package pers.cocoadel.leanring.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pers.cocoadel.leanring.mybatis.domain.Blog;
import pers.cocoadel.leanring.mybatis.utils.IdUtil;
import pers.cocoadel.leanring.mybatis.utils.MybatisUtil;

import java.util.*;


public class BlogMapperTest {

    @Test
   public void save(){
       try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
           BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
           mapper.save(new Blog(IdUtil.nextId(), "ruby 真好看", "cocoadel", new Date(), 1000));
           mapper.save(new Blog(IdUtil.nextId(), "weiss 真好看", "cocoadel", new Date(), 1000));
           mapper.save(new Blog(IdUtil.nextId(), "yang 真好看", "cocoadel", new Date(), 1000));
           mapper.save(new Blog(IdUtil.nextId(), "black 真好看", "cocoadel", new Date(), 1000));
           mapper.save(new Blog(IdUtil.nextId(), "cocoadel 真好看", "cocoadel", new Date(), 1000));
           sqlSession.commit();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   @Test
   public void  queryIf(){
       try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
           BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
           List<Blog> blogs = mapper.queryIf(new Blog(null, "ruby 真好看", "cocoadel", null, 0));
           blogs.forEach(System.out::println);
           //只有一个查询条件
           System.out.println("只有一个查询条件");
           blogs = mapper.queryIf(new Blog(null, null, "cocoadel", null, 0));
           blogs.forEach(System.out::println);
           //
           System.out.println("查询条件为空");
           blogs = mapper.queryIf(null);
           blogs.forEach(System.out::println);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    @Test
    public void  queryChoose(){
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            List<Blog> blogs = mapper.queryChoose(new Blog(null, "ruby 真好看", "cocoadel", null, 0));
            blogs.forEach(System.out::println);
            //只有一个查询条件
            System.out.println("只有一个查询条件");
            blogs = mapper.queryChoose(new Blog(null, null, "cocoadel", null, 0));
            blogs.forEach(System.out::println);
            //
            System.out.println("查询条件为空");
            blogs = mapper.queryChoose(new Blog(null, null, null, null, 1000));
            blogs.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            Blog blog = new Blog("51fd01e697fe428d88c6637fcc1d12f0", "ruby 真好看 v3", null, null, 0);
            int update = mapper.update(blog);
            if (update > 0) {
                System.out.println("更新成功");
            }
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryInIds() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            List<Blog> blogs = mapper.queryInIds(Arrays.asList("51fd01e697fe428d88c6637fcc1d12f0", "b09055852f024615b9cb0b7b09ef545b"));
//            List<Blog> blogs = mapper.queryInIds(Collections.emptyList());
            blogs.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}