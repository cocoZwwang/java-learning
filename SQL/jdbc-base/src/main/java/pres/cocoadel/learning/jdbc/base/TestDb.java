package pres.cocoadel.learning.jdbc.base;

import pres.cocoadel.learning.jdbc.base.domain.User;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TestDb {

    public static final String DROP_USERS_TABLE_DDL_SQL = "DROP TABLE users";

    public static final String CREATE_USERS_TABLE_DDL_SQL = "CREATE TABLE users(" +
//            "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "id INT NOT NULL PRIMARY KEY, " +
            "name VARCHAR(32) NOT NULL, " +
            "pwd VARCHAR(64) NOT NULL" +
            ")";


    public static void main(String[] args) {
        runTest();
    }

    public static void runTest() {
        try (Connection connection = SqlUtils.getConnection();
             Statement statement = connection.createStatement();) {
            SqlUtils.createTestDb(connection);

            statement.execute("insert into users (id,name,pwd) values (1,'ruby','123456')");
            statement.execute("insert into users (id,name,pwd) values (2,'weiss','123456')");
            statement.execute("insert into users (id,name,pwd) values (3,'yang','123456')");
            statement.execute("insert into users (id,name,pwd) values (4,'black','123456')");

            ResultSet resultSet = statement.executeQuery("select * from users");
            List<User> users = new LinkedList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setPwd(resultSet.getString(3));
                users.add(user);
            }
            users.forEach(System.out::println);
            resultSet.close();
        } catch (SQLException e) {
            for (Throwable throwable : e) {
                SQLException sqlException = (SQLException) throwable;
                System.out.println("sql state: " + sqlException.getSQLState() + " error code: " + sqlException.getErrorCode());
            }
        }
    }
}
