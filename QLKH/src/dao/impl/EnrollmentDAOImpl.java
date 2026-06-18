package dao.impl;

import dao.IEnrollmentDAO;
import model.Course;
import model.Enrollment;
import model.EnrollmentStatus;
import model.Student;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAOImpl implements IEnrollmentDAO {
    private Enrollment mapEnrollment(ResultSet resultSet) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(resultSet.getInt("id"));
        enrollment.setStudentId(resultSet.getInt("student_id"));
        enrollment.setCourseId(resultSet.getInt("course_id"));
        Timestamp timestamp = resultSet.getTimestamp("registered_at");
        if (timestamp != null) {
            enrollment.setRegisteredAt(timestamp.toLocalDateTime());
        }
        enrollment.setStatus(EnrollmentStatus.valueOf(resultSet.getString("status")));
        try {
            enrollment.setStudentName(resultSet.getString("student_name"));
        } catch (SQLException e) {
            enrollment.setStudentName(null);
        }
        try {
            enrollment.setCourseName(resultSet.getString("course_name"));
        } catch (SQLException e) {
            enrollment.setCourseName(null);
        }
        return enrollment;
    }

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

    private Student mapStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setName(resultSet.getString("name"));
        student.setDob(resultSet.getDate("dob").toLocalDate());
        student.setEmail(resultSet.getString("email"));
        student.setSex(resultSet.getBoolean("sex"));
        student.setPhone(resultSet.getString("phone"));
        student.setPassword(resultSet.getString("password"));
        return student;
    }

    private EnrollmentStatus findCurrentStatus(int studentId, int courseId) {
        String sql = "SELECT status FROM enrollment WHERE student_id = ? AND course_id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return EnrollmentStatus.valueOf(resultSet.getString("status"));
            }
        } catch (Exception e) {
            System.out.println("Loi kiem tra trang thai dang ky: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean registerCourse(int studentId, int courseId) {
        EnrollmentStatus currentStatus = findCurrentStatus(studentId, courseId);
        if (currentStatus == null) {
            String sql = "INSERT INTO enrollment(student_id, course_id, status) VALUES (?, ?, 'WAITING')";
            try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, studentId);
                preparedStatement.setInt(2, courseId);
                return preparedStatement.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Loi dang ky khoa hoc: " + e.getMessage());
                return false;
            }
        }

        if (currentStatus == EnrollmentStatus.CANCELED || currentStatus == EnrollmentStatus.DENIED) {
            String sql = "UPDATE enrollment SET status = 'WAITING', registered_at = CURRENT_TIMESTAMP WHERE student_id = ? AND course_id = ?";
            try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, studentId);
                preparedStatement.setInt(2, courseId);
                return preparedStatement.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Loi dang ky lai khoa hoc: " + e.getMessage());
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean cancelEnrollment(int studentId, int courseId) {
        String sql = "UPDATE enrollment SET status = 'CANCELED' WHERE student_id = ? AND course_id = ? AND status = 'WAITING'";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi huy dang ky: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateEnrollmentStatus(int enrollmentId, EnrollmentStatus status) {
        String sql = "UPDATE enrollment SET status = ? WHERE id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, enrollmentId);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi cap nhat trang thai dang ky: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Enrollment> findAll() {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT e.*, s.name AS student_name, c.name AS course_name FROM enrollment e " + "JOIN student s ON e.student_id = s.id " + "JOIN course c ON e.course_id = c.id ORDER BY e.id";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                list.add(mapEnrollment(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi lay danh sach dang ky: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Enrollment> findWaitingEnrollments() {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT e.*, s.name AS student_name, c.name AS course_name FROM enrollment e " + "JOIN student s ON e.student_id = s.id " + "JOIN course c ON e.course_id = c.id " + "WHERE e.status = 'WAITING' ORDER BY e.id";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                list.add(mapEnrollment(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi lay danh sach cho duyet: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Enrollment> findEnrollmentsByStudentId(int studentId) {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT e.*, s.name AS student_name, c.name AS course_name FROM enrollment e " + "JOIN student s ON e.student_id = s.id " + "JOIN course c ON e.course_id = c.id WHERE e.student_id = ? ORDER BY e.id";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapEnrollment(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi lay khoa hoc da dang ky: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Course> findCoursesByStudentId(int studentId) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT c.* FROM course c JOIN enrollment e ON c.id = e.course_id WHERE e.student_id = ? ORDER BY c.id";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapCourse(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi lay danh sach khoa hoc theo hoc vien: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Student> findStudentsByCourseId(int courseId) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT s.* FROM student s JOIN enrollment e ON s.id = e.student_id WHERE e.course_id = ? AND e.status <> 'CANCELED' ORDER BY s.id";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapStudent(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Loi lay hoc vien theo khoa hoc: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int countStudentsByCourseId(int courseId) {
        String sql = "SELECT COUNT(*) FROM enrollment WHERE course_id = ? AND status = 'CONFIRMED'";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Loi dem so hoc vien cua khoa hoc: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int countAllStudents() {
        String sql = "SELECT COUNT(*) FROM student";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Loi dem tong so hoc vien: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int countAllCourses() {
        String sql = "SELECT COUNT(*) FROM course";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Loi dem tong so khoa hoc: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public List<String> statisticStudentByCourse() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT c.id, c.name, COUNT(e.student_id) AS total FROM course c " + "LEFT JOIN enrollment e ON c.id = e.course_id AND e.status = 'CONFIRMED' " + "GROUP BY c.id, c.name ORDER BY c.id";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String line = "Khoa hoc " + resultSet.getInt("id") + " - " + resultSet.getString("name") + ": " + resultSet.getInt("total") + " hoc vien";
                list.add(line);
            }
        } catch (Exception e) {
            System.out.println("Loi thong ke hoc vien theo khoa hoc: " + e.getMessage());
        }
        return list;
    }
}
