package pers.cocoadel.leanring.mybatis.dao;

import pers.cocoadel.leanring.mybatis.domain.Student;

import java.util.List;

public interface StudentMapper {

    /**
     * 多对一的查询方式1：
     * 通过子查询的方式查找关联的 teacher 的信息
     */
    List<Student> selectAllBySubSelect();

    /**
     * 多对一的查询方式2：
     * 通过嵌套结果的方式联表查询
     */
    List<Student> selectAll();
}
