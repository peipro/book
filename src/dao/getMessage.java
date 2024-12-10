package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author pei
 * @version 1.0
 * 2024/12/3
 */
public class getMessage {
     private String url = " ";
     private String username = " ";
     private String password = " ";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  void getmessage() {
        String path = "mysql.properties";
        Properties prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            prop.load(fis);

            this.setUrl(prop.getProperty("url"));
            this.setUsername(prop.getProperty("username"));
            this.setPassword(prop.getProperty("password"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
