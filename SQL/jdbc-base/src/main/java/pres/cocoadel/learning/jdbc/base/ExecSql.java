package pres.cocoadel.learning.jdbc.base;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;

public class ExecSql {


    //E:\Projects\java\java-learning\SQL\jdbc-base\src\main\resources\test.sql
    public static void main(String[] args) {
        //读入 sql 文件路径
        try (Scanner scanner = new Scanner(System.in);
             Connection connection = SqlUtils.getConnection();) {

            SqlUtils.createTestDb(connection);

            System.out.println("请输入 sql 的文件路径：");
            String pathString = scanner.nextLine();
            Path path = Paths.get(pathString);

            //读取文件内容并且执行
            try (FileChannel fileChannel = FileChannel.open(path);
                 Scanner fileScanner = new Scanner(fileChannel);){
                while (fileScanner.hasNextLine()) {
                    String sql = fileScanner.nextLine().trim();
                    if(sql.endsWith(";")){
                        sql = sql.substring(0, sql.length() - 1);
                    }
                    System.out.println("sql = " + sql);
                    //如果是 Exit 则推出
                    if(sql.equalsIgnoreCase("EXIT")){
                        return;
                    }

                    try (Statement statement = connection.createStatement()) {
                        boolean isResultSet = statement.execute(sql);
                        if (isResultSet) {
                            //如果是查询，则显示查询结果
                            try (ResultSet resultSet = statement.getResultSet()) {
                                showResultSet(resultSet);
                            }
                        } else {
                            //否则显示更新的记录数量。这里的更新包括：insert 、delete、 update
                            int updateCnt = statement.getUpdateCount();
                            System.out.println("updateCnt = " + updateCnt);
                        }
                    } catch (SQLException e) {
                        for (Throwable t : e) {
                            t.printStackTrace();
                        }
                    }
                }
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    private static void showResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        //打印字段名称
        for (int i = 1; i <= columnCount; i++) {
            if(i > 1){
                System.out.print(",\t\t");
            }
            System.out.print(resultSetMetaData.getColumnLabel(i));
        }
        System.out.println();
        //迭代打印结果
        while (resultSet.next()) {
            for(int i =1; i <= columnCount; i++){
                if(i > 1){
                    System.out.print(",\t\t");
                }
                Object value = resultSet.getString(i);
                System.out.print(value);
            }
            System.out.println();
        }
    }
}
