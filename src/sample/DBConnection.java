package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public Connection createConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties agentInfo = new Properties();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", "Andrew", "password");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
//            userInfo.put("user","root");
//            userInfo.put("password","");
}
