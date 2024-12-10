package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author pei
 * @version 1.0
 * 2024/12/3
 */
public class BaseDaoImpl {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 获得数据库连接
    public static Connection getConn(){
        getMessage message = new getMessage();
        message.getmessage();
        Connection conn=null;
        String url=message.getUrl();
        String name=message.getUsername();
        String password=message.getPassword();

        try {
            conn = DriverManager.getConnection(url, name,password); // 创建连接
//            System.out.println("数据库连接成功");
        } catch (SQLException e) {
//            System.out.println("数据库连接失败");
        }
        return conn;
    }
}