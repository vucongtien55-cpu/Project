package dao.impl;

import dao.ICourseDAO;
import model.Course;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class CourseDAOImpl implements ICourseDAO {
    private Course mapCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt("id"));
        course.setName(resultSet.getString("name"));
        course.setDuration(resultSet.getInt("duration"));
        course.setInstructor(resultSet.getString("instructor"));
        Timestamp timestamp = resultSet.getTimestamp("create_at");
        if (timestamp != null) {
            course.setCreateAt(timestamp.toLocalDateTime());
        }
        return course;
    }

    @Override
    public List<Course> findAll() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM course ORDER BY id";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                list.add(mapCourse(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi lay danh sach khoa hoc: " + e.getMessage());
        }
        return list;
    }

    @Override
    public Course findById(int id) {
        String sql = "SELECT * FROM course WHERE id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapCourse(resultSet);
            }
        } catch (Exception e) {
            System.out.println("Loi tim khoa hoc theo id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean addCourse(Course course) {
        String sql = "INSERT INTO course(name, duration, instructor) VALUES (?, ?, ?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setInt(2, course.getDuration());
            preparedStatement.setString(3, course.getInstructor());
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi them khoa hoc: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateCourse(Course course) {
        String sql = "UPDATE course SET name = ?, duration = ?, instructor = ? WHERE id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setInt(2, course.getDuration());
            preparedStatement.setString(3, course.getInstructor());
            preparedStatement.setInt(4, course.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi cap nhat khoa hoc: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM course WHERE id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi xoa khoa hoc: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Course> findByName(String keyword) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE name ILIKE ? ORDER BY id";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapCourse(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi tim kiem khoa hoc: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Course> sortById(boolean asc) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM course ORDER BY id " + (asc ? "ASC" : "DESC");
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                list.add(mapCourse(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi sap xep khoa hoc theo id: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Course> sortByName(boolean asc) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM course ORDER BY name " + (asc ? "ASC" : "DESC");
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                list.add(mapCourse(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi sap xep khoa hoc theo ten: " + e.getMessage());
        }
        return list;
    }

}
