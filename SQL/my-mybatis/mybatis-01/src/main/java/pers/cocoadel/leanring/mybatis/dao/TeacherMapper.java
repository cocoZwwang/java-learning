package pers.cocoadel.leanring.mybatis.dao;

import pers.cocoadel.leanring.mybatis.domain.Teacher;

import java.util.List;

public interface TeacherMapper {
    /**
     * 一对多查询1：
     * 通过子查询来联合查询
     */
    List<Teacher> selectAllBySubSelect();

    /**
     * 一对多查询2：
     * 通过联合查询 + 嵌套结构映射
     */
    List<Teacher> selectAll();

}
