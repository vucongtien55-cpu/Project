package presentation;

import business.IAdminService;
import business.IStudentService;
import business.impl.AdminServiceImpl;
import business.impl.StudentServiceImpl;
import model.Admin;
import model.Student;
import utils.DBUtil;
import utils.InputUtil;

import java.util.Scanner;

public class MainView {
    private final Scanner scanner = new Scanner(System.in);
    private final IAdminService adminService = new AdminServiceImpl();
    private final IStudentService studentService = new StudentServiceImpl();
    private boolean isRunning = true;

    public void showMainMenu() {
        while (isRunning) {
            System.out.println("\n===== HỆ THỐNG QUẢN LÝ KHÓA HỌC =====");
            System.out.println("1. Đăng nhập Admin");
            System.out.println("2. Đăng nhập Học viên");
            System.out.println("3. Kiểm tra kết nối database");
            System.out.println("4. Thoát");

            int choice = InputUtil.inputChoice(scanner, "Nhập lựa chọn: ");
            switch (choice) {
                case 1:
                    loginAdmin();
                    break;
                case 2:
                    loginStudent();
                    break;
                case 3:
                    testConnection();
                    break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void loginAdmin() {
        String username = InputUtil.inputRequiredString(scanner, "Nhập username: ");
        String password = InputUtil.inputRequiredString(scanner, "Nhập password: ");
        Admin admin = adminService.login(username, password);
        if (admin != null) {
            System.out.println("Đăng nhập admin thành công.");
            AdminView adminView = new AdminView();
            adminView.showAdminMenu();
        } else {
            System.out.println("Sai tài khoản hoặc mật khẩu admin.");
        }
    }

    private void loginStudent() {
        String email = InputUtil.inputRequiredString(scanner, "Nhập email: ");
        String password = InputUtil.inputRequiredString(scanner, "Nhập password: ");
        Student student = studentService.login(email, password);
        if (student != null) {
            System.out.println("Đăng nhập học viên thành công. Xin chào " + student.getName());
            StudentView studentView = new StudentView(student);
            studentView.showStudentMenu();
        } else {
            System.out.println("Sai email hoặc mật khẩu học viên.");
        }
    }

    private void testConnection() {
        if (DBUtil.testConnection()) {
            System.out.println("Kết nối PostgreSQL thành công.");
        } else {
            System.out.println("Không kết nối được database. Kiểm tra DBUtil.java và PostgreSQL.");
        }
    }
}