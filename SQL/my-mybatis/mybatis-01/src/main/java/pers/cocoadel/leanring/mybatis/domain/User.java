package pers.cocoadel.leanring.mybatis.domain;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

//这里的Alias 注解会被别名扫描到，注解名称会替代默认的别名
@Alias("user")
public class User implements Serializable {

    private static final long serialVersionUID = 5687835191360081817L;
    private int id;

    private String name;

    private String password;

    public User() {
    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
