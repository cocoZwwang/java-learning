package pres.cocoadel.learning.jdbc.base;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SqlUtils {


    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            ClassLoader classLoader = TestDb.class.getClassLoader();
            try (InputStream in = classLoader.getResourceAsStream("database.properties")) {
                properties.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String drivers = properties.getProperty("jdbc.driver-class-name");
            System.out.println("drivers = " + drivers);
            if (!StringUtils.isEmpty(drivers)) {
                System.setProperty("jdbc.url", drivers);
            }

            String url = properties.getProperty("jdbc.url");
            System.out.println("url = " + url);

            //注册驱动
            try {
                Class.forName(drivers);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final String DROP_USERS_TABLE_DDL_SQL = "DROP TABLE users";

    public static final String CREATE_USERS_TABLE_DDL_SQL = "CREATE TABLE users(" +
//            "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "id INT NOT NULL PRIMARY KEY, " +
            "name VARCHAR(32) NOT NULL, " +
            "pwd VARCHAR(64) NOT NULL" +
            ")";

    public static void createTestDb(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            try {
                statement.execute(DROP_USERS_TABLE_DDL_SQL);
            } catch (Exception e) {
                System.out.println("数据库 users 不存在！");
            }
            statement.execute(CREATE_USERS_TABLE_DDL_SQL);
            System.out.println("创建数据库成功!!!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
