package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection() {
        Connection conn = null;

        try {
            Properties prop = new Properties();

            InputStream input = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("utils/ConnectDB.properties");

            if (input == null) {
                throw new RuntimeException("Khong tim thay file ConnectDB.properties");
            }

            prop.load(input);

            String url = prop.getProperty("url");
            String user = prop.getProperty("userID");
            String password = prop.getProperty("password");

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            conn = DriverManager.getConnection(url, user, password);

            System.out.println("Ket noi SQL Server thanh cong");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}