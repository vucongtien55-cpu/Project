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

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AdminView {
    private final Scanner scanner = new Scanner(System.in);
    private final IStudentService studentService = new StudentServiceImpl();
    private final ICourseService courseService = new CourseServiceImpl();
    private final IEnrollmentService enrollmentService = new EnrollmentServiceImpl();
    private boolean isRunning = true;

    public void showAdminMenu() {
        while (isRunning) {
            System.out.println("\n======== MENU ADMIN ========");
            System.out.println("1. Quản lý học viên");
            System.out.println("2. Quản lý khóa học");
            System.out.println("3. Quản lý đăng ký học");
            System.out.println("4. Thống kê");
            System.out.println("5. Đăng xuất");

            int choice = InputUtil.inputChoice(scanner, "Nhập lựa chọn: ");
            switch (choice) {
                case 1:
                    showStudentManagementMenu();
                    break;
                case 2:
                    showCourseManagementMenu();
                    break;
                case 3:
                    showEnrollmentManagementMenu();
                    break;
                case 4:
                    showStatisticMenu();
                    break;
                case 5:
                    System.out.println("Đăng xuất khỏi menu admin.");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void showStudentManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- QUẢN LÝ HỌC VIÊN -----");
            System.out.println("1. Hiển thị danh sách học viên");
            System.out.println("2. Thêm học viên");
            System.out.println("3. Sửa học viên");
            System.out.println("4. Xóa học viên");
            System.out.println("5. Tìm kiếm học viên");
            System.out.println("6. Sắp xếp theo id");
            System.out.println("7. Sắp xếp theo tên");
            System.out.println("8. Quay lại");

            int choice = InputUtil.inputChoice(scanner, "Nhập lựa chọn: ");
            switch (choice) {
                case 1:
                    printStudentList(studentService.findAll());
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    searchStudent();
                    break;
                case 6:
                    sortStudentById();
                    break;
                case 7:
                    sortStudentByName();
                    break;
                case 8:
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void showCourseManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- QUẢN LÝ KHÓA HỌC -----");
            System.out.println("1. Hiển thị danh sách khóa học");
            System.out.println("2. Thêm khóa học");
            System.out.println("3. Sửa khóa học");
            System.out.println("4. Xóa khóa học");
            System.out.println("5. Tìm kiếm khóa học theo tên");
            System.out.println("6. Sắp xếp theo id");
            System.out.println("7. Sắp xếp theo tên");
            System.out.println("8. Quay lại");

            int choice = InputUtil.inputChoice(scanner, "Nhập lựa chọn: ");
            switch (choice) {
                case 1:
                    printCourseList(courseService.findAll());
                    break;
                case 2:
                    addCourse();
                    break;
                case 3:
                    updateCourse();
                    break;
                case 4:
                    deleteCourse();
                    break;
                case 5:
                    searchCourse();
                    break;
                case 6:
                    sortCourseById();
                    break;
                case 7:
                    sortCourseByName();
                    break;
                case 8:
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void showEnrollmentManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- QUẢN LÝ ĐĂNG KÝ HỌC -----");
            System.out.println("1. Gán học viên vào khóa học");
            System.out.println("2. Hiển thị toàn bộ đăng ký");
            System.out.println("3. Hiển thị danh sách chờ duyệt");
            System.out.println("4. Duyệt đăng ký");
            System.out.println("5. Từ chối đăng ký");
            System.out.println("6. Hiển thị học viên theo khóa học");
            System.out.println("7. Quay lại");

            int choice = InputUtil.inputChoice(scanner, "Nhập lựa chọn: ");
            switch (choice) {
                case 1:
                    assignStudentToCourse();
                    break;
                case 2:
                    printEnrollmentList(enrollmentService.findAll());
                    break;
                case 3:
                    printEnrollmentList(enrollmentService.findWaitingEnrollments());
                    break;
                case 4:
                    approveEnrollment();
                    break;
                case 5:
                    denyEnrollment();
                    break;
                case 6:
                    showStudentsByCourse();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void showStatisticMenu() {
        System.out.println("\n----- THỐNG KÊ -----");
        System.out.println("Tổng số học viên: " + enrollmentService.countAllStudents());
        System.out.println("Tổng số khóa học: " + enrollmentService.countAllCourses());
        System.out.println("Số học viên đã xác nhận theo từng khóa học:");
        List<String> statisticList = enrollmentService.statisticStudentByCourse();
        for (String line : statisticList) {
            System.out.println(line);
        }
    }

    private void addStudent() {
        String name = InputUtil.inputRequiredString(scanner, "Nhập tên học viên: ");
        LocalDate dob = InputUtil.inputDate(scanner, "Nhập ngày sinh (yyyy-MM-dd): ");
        String email = InputUtil.inputRequiredString(scanner, "Nhập email: ");
        boolean sex = InputUtil.inputGender(scanner, "Nhập giới tính");
        String phone = InputUtil.inputRequiredString(scanner, "Nhập số điện thoại: ");
        String password = InputUtil.inputRequiredString(scanner, "Nhập mật khẩu: ");

        Student student = new Student(name, dob, email, sex, phone, password);
        if (studentService.addStudent(student)) {
            System.out.println("Thêm học viên thành công.");
        } else {
            System.out.println("Thêm học viên thất bại. Kiểm tra email, số điện thoại, mật khẩu.");
        }
    }

    private void updateStudent() {
        int id = InputUtil.inputPositiveInt(scanner, "Nhập id học viên cần sửa: ");
        Student oldStudent = studentService.findById(id);
        if (oldStudent == null) {
            System.out.println("Không tìm thấy học viên.");
            return;
        }

        String name = InputUtil.inputRequiredString(scanner, "Nhập tên mới: ");
        LocalDate dob = InputUtil.inputDate(scanner, "Nhập ngày sinh mới (yyyy-MM-dd): ");
        String email = InputUtil.inputRequiredString(scanner, "Nhập email mới: ");
        boolean sex = InputUtil.inputGender(scanner, "Nhập giới tính mới");
        String phone = InputUtil.inputRequiredString(scanner, "Nhập số điện thoại mới: ");
        String password = InputUtil.inputRequiredString(scanner, "Nhập mật khẩu mới: ");

        Student newStudent = new Student(id, name, dob, email, sex, phone, password, oldStudent.getCreateAt());
        if (studentService.updateStudent(newStudent)) {
            System.out.println("Cập nhật học viên thành công.");
        } else {
            System.out.println("Cập nhật học viên thất bại.");
        }
    }

    private void deleteStudent() {
        int id = InputUtil.inputPositiveInt(scanner, "Nhập id học viên cần xóa: ");
        Student student = studentService.findById(id);
        if (student == null) {
            System.out.println("Không tìm thấy học viên.");
            return;
        }
        boolean confirm = InputUtil.inputYesNo(scanner, "Bạn có chắc muốn xóa học viên này không?");
        if (confirm) {
            if (studentService.deleteById(id)) {
                System.out.println("Xóa học viên thành công.");
            } else {
                System.out.println("Xóa học viên thất bại.");
            }
        }
    }

    private void searchStudent() {
        String keyword = InputUtil.inputRequiredString(scanner, "Nhập từ khóa tìm kiếm: ");
        printStudentList(studentService.searchStudent(keyword));
    }

    private void sortStudentById() {
        boolean asc = InputUtil.inputChoice(scanner, "1. Tăng dần - 2. Giảm dần: ") == 1;
        printStudentList(studentService.sortById(asc));
    }

    private void sortStudentByName() {
        boolean asc = InputUtil.inputChoice(scanner, "1. Tăng dần - 2. Giảm dần: ") == 1;
        printStudentList(studentService.sortByName(asc));
    }

    private void addCourse() {
        String name = InputUtil.inputRequiredString(scanner, "Nhập tên khóa học: ");
        int duration = InputUtil.inputPositiveInt(scanner, "Nhập thời lượng: ");
        String instructor = InputUtil.inputRequiredString(scanner, "Nhập giảng viên: ");

        Course course = new Course(name, duration, instructor);
        if (courseService.addCourse(course)) {
            System.out.println("Thêm khóa học thành công.");
        } else {
            System.out.println("Thêm khóa học thất bại.");
        }
    }

    private void updateCourse() {
        int id = InputUtil.inputPositiveInt(scanner, "Nhập id khóa học cần sửa: ");
        Course oldCourse = courseService.findById(id);
        if (oldCourse == null) {
            System.out.println("Không tìm thấy khóa học.");
            return;
        }

        String name = InputUtil.inputRequiredString(scanner, "Nhập tên mới: ");
        int duration = InputUtil.inputPositiveInt(scanner, "Nhập thời lượng mới: ");
        String instructor = InputUtil.inputRequiredString(scanner, "Nhập giảng viên mới: ");

        Course newCourse = new Course(id, name, duration, instructor, oldCourse.getCreateAt());
        if (courseService.updateCourse(newCourse)) {
            System.out.println("Cập nhật khóa học thành công.");
        } else {
            System.out.println("Cập nhật khóa học thất bại.");
        }
    }

    private void deleteCourse() {
        int id = InputUtil.inputPositiveInt(scanner, "Nhập id khóa học cần xóa: ");
        Course course = courseService.findById(id);
        if (course == null) {
            System.out.println("Không tìm thấy khóa học.");
            return;
        }
        boolean confirm = InputUtil.inputYesNo(scanner, "Bạn có chắc muốn xóa khóa học này không?");
        if (confirm) {
            if (courseService.deleteById(id)) {
                System.out.println("Xóa khóa học thành công.");
            } else {
                System.out.println("Xóa khóa học thất bại.");
            }
        }
    }

    private void searchCourse() {
        String keyword = InputUtil.inputRequiredString(scanner, "Nhập tên khóa học cần tìm: ");
        printCourseList(courseService.findByName(keyword));
    }

    private void sortCourseById() {
        boolean asc = InputUtil.inputChoice(scanner, "1. Tăng dần - 2. Giảm dần: ") == 1;
        printCourseList(courseService.sortById(asc));
    }

    private void sortCourseByName() {
        boolean asc = InputUtil.inputChoice(scanner, "1. Tăng dần - 2. Giảm dần: ") == 1;
        printCourseList(courseService.sortByName(asc));
    }

    private void assignStudentToCourse() {
        int studentId = InputUtil.inputPositiveInt(scanner, "Nhập id học viên: ");
        int courseId = InputUtil.inputPositiveInt(scanner, "Nhập id khóa học: ");
        if (enrollmentService.registerCourse(studentId, courseId)) {
            System.out.println("Đăng ký khóa học thành công. Trạng thái ban đầu là WAITING.");
        } else {
            System.out.println("Đăng ký thất bại. Kiểm tra học viên, khóa học hoặc trạng thái đăng ký cũ.");
        }
    }

    private void approveEnrollment() {
        printEnrollmentList(enrollmentService.findWaitingEnrollments());
        int enrollmentId = InputUtil.inputPositiveInt(scanner, "Nhập id đăng ký cần duyệt: ");
        if (enrollmentService.approveEnrollment(enrollmentId)) {
            System.out.println("Đã duyệt đăng ký thành công.");
        } else {
            System.out.println("Duyệt đăng ký thất bại.");
        }
    }

    private void denyEnrollment() {
        printEnrollmentList(enrollmentService.findWaitingEnrollments());
        int enrollmentId = InputUtil.inputPositiveInt(scanner, "Nhập id đăng ký cần từ chối: ");
        if (enrollmentService.denyEnrollment(enrollmentId)) {
            System.out.println("Đã từ chối đăng ký thành công.");
        } else {
            System.out.println("Từ chối đăng ký thất bại.");
        }
    }

    private void showStudentsByCourse() {
        int courseId = InputUtil.inputPositiveInt(scanner, "Nhập id khóa học: ");
        List<Student> students = enrollmentService.findStudentsByCourseId(courseId);
        printStudentList(students);
    }

    private void printStudentList(List<Student> students) {
        if (students == null || students.isEmpty()) {
            System.out.println("Không có dữ liệu học viên.");
            return;
        }
        System.out.printf("%-5s %-22s %-12s %-25s %-6s %-12s%n", "ID", "Tên", "Ngày sinh", "Email", "GT", "Phone");
        for (Student student : students) {
            System.out.printf("%-5d %-22s %-12s %-25s %-6s %-12s%n",
                    student.getId(),
                    student.getName(),
                    student.getDob(),
                    student.getEmail(),
                    student.isSex(),
                    student.getPhone());
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

    private void printEnrollmentList(List<Enrollment> enrollments) {
        if (enrollments == null || enrollments.isEmpty()) {
            System.out.println("Không có dữ liệu đăng ký.");
            return;
        }
        System.out.printf("%-5s %-10s %-22s %-10s %-30s %-12s%n", "ID", "StudentID", "Tên học viên", "CourseID", "Tên khóa học", "Trạng thái");
        for (Enrollment enrollment : enrollments) {
            System.out.printf("%-5d %-10d %-22s %-10d %-30s %-12s%n",
                    enrollment.getId(),
                    enrollment.getStudentId(),
                    enrollment.getStudentName(),
                    enrollment.getCourseId(),
                    enrollment.getCourseName(),
                    enrollment.getStatus());
        }
    }
}
