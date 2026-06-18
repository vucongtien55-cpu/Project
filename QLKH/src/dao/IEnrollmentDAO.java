package dao;

import model.Course;
import model.Enrollment;
import model.EnrollmentStatus;
import model.Student;

import java.util.List;

public interface IEnrollmentDAO {
    boolean registerCourse(int studentId, int courseId);

    boolean cancelEnrollment(int studentId, int courseId);

    boolean updateEnrollmentStatus(int enrollmentId, EnrollmentStatus status);

    List<Enrollment> findAll();

    List<Enrollment> findWaitingEnrollments();

    List<Enrollment> findEnrollmentsByStudentId(int studentId);

    List<Course> findCoursesByStudentId(int studentId);

    List<Student> findStudentsByCourseId(int courseId);

    int countStudentsByCourseId(int courseId);

    int countAllStudents();

    int countAllCourses();

    List<String> statisticStudentByCourse();
}
