package presentation;

import business.ICourseService;
import business.IEnrollmentService;
import business.IStudentService;
import business.impl.CourseServiceImpl;
import business.impl.EnrollmentServiceImpl;
import business.impl.StudentServiceImpl;
import model.Course;
import model.Enrollment;
import model.Student;
import utils.InputUtil;

import java.util.List;
import java.util.Scanner;

public class StudentView {
    private final Scanner scanner = new Scanner(System.in);
    private final ICourseService courseService = new CourseServiceImpl();
    private final IEnrollmentService enrollmentService = new EnrollmentServiceImpl();
    private final IStudentService studentService = new StudentServiceImpl();
    private final Student currentStudent;
    private boolean isRunning = true;

    public StudentView(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public void showStudentMenu() {
        while (isRunning) {
            System.out.println("\n====== MENU HỌC VIÊN ======");
            System.out.println("1. Xem danh sách khóa học");
            System.out.println("2. Tìm kiếm khóa học theo tên");
            System.out.println("3. Đăng ký khóa học");
            System.out.println("4. Xem khóa học đã đăng ký");
            System.out.println("5. Hủy đăng ký (nếu đang WAITING)");
            System.out.println("6. Đổi mật khẩu");
            System.out.println("7. Đăng xuất");

            int choice = InputUtil.inputChoice(scanner, "Nhập lựa chọn: ");
            switch (choice) {
                case 1:
                    printCourseList(courseService.findAll());
                    break;
                case 2:
                    searchCourse();
                    break;
                case 3:
                    registerCourse();
                    break;
                case 4:
                    showMyEnrollments();
                    break;
                case 5:
                    cancelEnrollment();
                    break;
                case 6:
                    changePassword();
                    break;
                case 7:
                    System.out.println("Đăng xuất khỏi menu học viên.");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void searchCourse() {
        String keyword = InputUtil.inputRequiredString(scanner, "Nhập tên khóa học cần tìm: ");
        printCourseList(courseService.findByName(keyword));
    }

    private void registerCourse() {
        printCourseList(courseService.findAll());
        int courseId = InputUtil.inputPositiveInt(scanner, "Nhập id khóa học muốn đăng ký: ");
        if (enrollmentService.registerCourse(currentStudent.getId(), courseId)) {
            System.out.println("Đăng ký thành công. Trạng thái hiện tại là WAITING.");
        } else {
            System.out.println("Đăng ký thất bại. Có thể khóa học không tồn tại hoặc bạn đã đăng ký rồi.");
        }
    }

    private void showMyEnrollments() {
        List<Enrollment> enrollmentList = enrollmentService.findEnrollmentsByStudentId(currentStudent.getId());
        if (enrollmentList == null || enrollmentList.isEmpty()) {
            System.out.println("Bạn chưa đăng ký khóa học nào.");
            return;
        }
        System.out.printf("%-5s %-10s %-30s %-12s%n", "ID", "CourseID", "Tên khóa học", "Trạng thái");
        for (Enrollment enrollment : enrollmentList) {
            System.out.printf("%-5d %-10d %-30s %-12s%n",
                    enrollment.getId(),
                    enrollment.getCourseId(),
                    enrollment.getCourseName(),
                    enrollment.getStatus());
        }
    }

    private void cancelEnrollment() {
        showMyEnrollments();
        int courseId = InputUtil.inputPositiveInt(scanner, "Nhập id khóa học muốn hủy đăng ký: ");
        if (enrollmentService.cancelEnrollment(currentStudent.getId(), courseId)) {
            System.out.println("Hủy đăng ký thành công.");
        } else {
            System.out.println("Hủy đăng ký thất bại. Chỉ được hủy khi trạng thái là WAITING.");
        }
    }

    private void changePassword() {
        String oldPassword = InputUtil.inputRequiredString(scanner, "Nhập mật khẩu cũ: ");
        String newPassword = InputUtil.inputRequiredString(scanner, "Nhập mật khẩu mới: ");
        if (studentService.changePassword(currentStudent.getId(), oldPassword, newPassword)) {
            System.out.println("Đổi mật khẩu thành công.");
            currentStudent.setPassword(newPassword);
        } else {
            System.out.println("Đổi mật khẩu thất bại. Kiểm tra mật khẩu cũ và độ dài mật khẩu mới.");
        }
    }

    private void printCourseList(List<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            System.out.println("Không có dữ liệu khóa học.");
            return;
        }
        System.out.printf("%-5s %-30s %-12s %-20s%n", "ID", "Tên khóa học", "Thời lượng", "Giảng viên");
        for (Course course : courses) {
            System.out.printf("%-5d %-30s %-12d %-20s%n",
                    course.getId(),
                    course.getName(),
                    course.getDuration(),
                    course.getInstructor());
        }
    }
}
