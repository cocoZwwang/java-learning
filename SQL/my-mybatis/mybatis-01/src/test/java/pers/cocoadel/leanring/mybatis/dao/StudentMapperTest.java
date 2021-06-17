package pers.cocoadel.leanring.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pers.cocoadel.leanring.mybatis.domain.Student;
import pers.cocoadel.leanring.mybatis.domain.User;
import pers.cocoadel.leanring.mybatis.utils.MybatisUtil;

import java.util.List;

import static org.junit.Assert.*;

public class StudentMapperTest {

    @Test
    public void selectAllBySubSelect() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            List<Student> students = mapper.selectAllBySubSelect();
            students.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectAll() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            List<Student> students = mapper.selectAll();
            students.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}