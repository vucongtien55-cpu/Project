package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static final String URL = "jdbc:postgresql://localhost:5432/QLKH";
    private static final String USER = "postgres";
    private static final String PASS = "Vucongtien123";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection != null;
        } catch (Exception e) {
            return false;
        }
    }
}
