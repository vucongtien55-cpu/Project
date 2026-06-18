// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package business.impl;

import business.IEnrollmentService;
import dao.ICourseDAO;
import dao.IEnrollmentDAO;
import dao.IStudentDAO;
import dao.impl.CourseDAOImpl;
import dao.impl.EnrollmentDAOImpl;
import dao.impl.StudentDAOImpl;
import java.util.List;
import model.Course;
import model.Enrollment;
import model.EnrollmentStatus;
import model.Student;

public class EnrollmentServiceImpl implements IEnrollmentService {
    private final IEnrollmentDAO enrollmentDAO = new EnrollmentDAOImpl();
    private final IStudentDAO studentDAO = new StudentDAOImpl();
    private final ICourseDAO courseDAO = new CourseDAOImpl();

    public EnrollmentServiceImpl() {
    }

    public boolean registerCourse(int var1, int var2) {
        Student var3 = this.studentDAO.findById(var1);
        Course var4 = this.courseDAO.findById(var2);
        return var3 != null && var4 != null ? this.enrollmentDAO.registerCourse(var1, var2) : false;
    }

    public boolean cancelEnrollment(int var1, int var2) {
        return this.enrollmentDAO.cancelEnrollment(var1, var2);
    }

    public List<Enrollment> findAll() {
        return this.enrollmentDAO.findAll();
    }

    public List<Enrollment> findWaitingEnrollments() {
        return this.enrollmentDAO.findWaitingEnrollments();
    }

    public boolean approveEnrollment(int var1) {
        return this.enrollmentDAO.updateEnrollmentStatus(var1, EnrollmentStatus.CONFIRMED);
    }

    public boolean denyEnrollment(int var1) {
        return this.enrollmentDAO.updateEnrollmentStatus(var1, EnrollmentStatus.DENIED);
    }

    public List<Enrollment> findEnrollmentsByStudentId(int var1) {
        return this.enrollmentDAO.findEnrollmentsByStudentId(var1);
    }

    public List<Student> findStudentsByCourseId(int var1) {
        return this.enrollmentDAO.findStudentsByCourseId(var1);
    }

    public int countStudentsByCourseId(int var1) {
        return this.enrollmentDAO.countStudentsByCourseId(var1);
    }

    public int countAllStudents() {
        return this.enrollmentDAO.countAllStudents();
    }

    public int countAllCourses() {
        return this.enrollmentDAO.countAllCourses();
    }

    public List<String> statisticStudentByCourse() {
        return this.enrollmentDAO.statisticStudentByCourse();
    }
}
