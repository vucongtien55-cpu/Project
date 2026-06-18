package dao.impl;

import dao.IAdminDAO;
import model.Admin;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements IAdminDAO {
    @Override
    public Admin login(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                return admin;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
