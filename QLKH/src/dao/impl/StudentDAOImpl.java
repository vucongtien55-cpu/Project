package dao.impl;

import dao.IStudentDAO;
import model.Student;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements IStudentDAO {
    private Student mapStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setName(resultSet.getString("name"));
        student.setDob(resultSet.getDate("dob").toLocalDate());
        student.setEmail(resultSet.getString("email"));
        student.setSex(resultSet.getBoolean("sex"));
        student.setPhone(resultSet.getString("phone"));
        student.setPassword(resultSet.getString("password"));
        Timestamp timestamp = resultSet.getTimestamp("create_at");
        if (timestamp != null) {
            student.setCreateAt(timestamp.toLocalDateTime());
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student ORDER BY id";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                list.add(mapStudent(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi lay danh sach hoc vien: " + e.getMessage());
        }
        return list;
    }

    @Override
    public Student findById(int id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapStudent(resultSet);
            }
        } catch (Exception e) {
            System.out.println("Loi tim hoc vien theo id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO student(name, dob, email, sex, phone, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setDate(2, Date.valueOf(student.getDob()));
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setBoolean(4, student.isSex());
            preparedStatement.setString(5, student.getPhone());
            preparedStatement.setString(6, student.getPassword());
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi them hoc vien: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateStudent(Student student) {
        String sql = "UPDATE student SET name = ?, dob = ?, email = ?, sex = ?, phone = ?, password = ? WHERE id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setDate(2, Date.valueOf(student.getDob()));
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setBoolean(4, student.isSex());
            preparedStatement.setString(5, student.getPhone());
            preparedStatement.setString(6, student.getPassword());
            preparedStatement.setInt(7, student.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi cap nhat hoc vien: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM student WHERE id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi xoa hoc vien: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Student> searchStudent(String keyword) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student WHERE CAST(id AS TEXT) ILIKE ? OR name ILIKE ? OR email ILIKE ? ORDER BY id";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            String value = "%" + keyword + "%";
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, value);
            preparedStatement.setString(3, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapStudent(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi tim kiem hoc vien: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Student> sortById(boolean asc) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student ORDER BY id " + (asc ? "ASC" : "DESC");
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                list.add(mapStudent(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi sap xep hoc vien theo id: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Student> sortByName(boolean asc) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student ORDER BY name " + (asc ? "ASC" : "DESC");
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                list.add(mapStudent(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi sap xep hoc vien theo ten: " + e.getMessage());
        }
        return list;
    }

    @Override
    public Student login(String email, String password) {
        String sql = "SELECT * FROM student WHERE email = ? AND password = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapStudent(resultSet);
            }
        } catch (Exception e) {
            System.out.println("Loi dang nhap hoc vien: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean changePassword(int studentId, String oldPassword, String newPassword) {
        String sql = "UPDATE student SET password = ? WHERE id = ? AND password = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, studentId);
            preparedStatement.setString(3, oldPassword);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi doi mat khau: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM student WHERE email = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            System.out.println("Loi kiem tra email: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean existsByEmailExceptId(String email, int id) {
        String sql = "SELECT 1 FROM student WHERE email = ? AND id <> ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            System.out.println("Loi kiem tra email trung: " + e.getMessage());
        }
        return false;
    }

}
