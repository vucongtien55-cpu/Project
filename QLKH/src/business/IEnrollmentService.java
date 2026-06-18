package business;

import model.Enrollment;
import model.Student;

import java.util.List;

public interface IEnrollmentService {
    boolean registerCourse(int studentId, int courseId);

    boolean cancelEnrollment(int studentId, int courseId);

    List<Enrollment> findAll();

    List<Enrollment> findWaitingEnrollments();

    boolean approveEnrollment(int enrollmentId);

    boolean denyEnrollment(int enrollmentId);

    List<Enrollment> findEnrollmentsByStudentId(int studentId);

    List<Student> findStudentsByCourseId(int courseId);

    int countStudentsByCourseId(int courseId);

    int countAllStudents();

    int countAllCourses();

    List<String> statisticStudentByCourse();
}
