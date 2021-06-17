package pers.cocoadel.leanring.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtil {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            //创建 Mybatis 的 SqlSessionFactory 对象
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回一个 SqlSessionFactory
     * SqlSession 不是线程安全的，最佳的实践是每一个线程一个 SqlSession 实例。
     * 比如一个 Web 应用中，较好的做法是一个 Http 请求，创建一个 SqlSession，并且 Http 请求完毕后关闭该 SqlSession 实例。
     */
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
