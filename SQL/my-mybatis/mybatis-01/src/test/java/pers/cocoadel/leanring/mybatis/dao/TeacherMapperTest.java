package pers.cocoadel.leanring.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pers.cocoadel.leanring.mybatis.domain.Teacher;
import pers.cocoadel.leanring.mybatis.utils.MybatisUtil;

import java.util.List;

public class TeacherMapperTest {

    @Test
    public void selectAll() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            List<Teacher> teachers = mapper.selectAll();
            teachers.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectAllBySubSelect() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            List<Teacher> teachers = mapper.selectAllBySubSelect();
            teachers.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
