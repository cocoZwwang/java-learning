package pers.cocoadel.leanring.mybatis.dao;

import pers.cocoadel.leanring.mybatis.domain.Blog;

import java.util.List;

public interface BlogMapper {
    int save(Blog blog);

    List<Blog> queryIf(Blog where);

    List<Blog> queryChoose(Blog where);

    int update(Blog blog);

    List<Blog> queryInIds(List<String> ids);
}
