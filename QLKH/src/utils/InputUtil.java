package utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtil {
    public static int inputChoice(Scanner scanner, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ.");
            }
        }
    }

    public static int inputPositiveInt(Scanner scanner, String message) {
        while (true) {
            int value = inputChoice(scanner, message);
            if (value > 0) {
                return value;
            }
            System.out.println("Giá trị phải lớn hơn 0.");
        }
    }

    public static String inputString(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public static String inputRequiredString(Scanner scanner, String message) {
        while (true) {
            String value = inputString(scanner, message);
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Không được để trống.");
        }
    }

    public static LocalDate inputDate(Scanner scanner, String message) {
        while (true) {
            try {
                System.out.print(message);
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Ngày không hợp lệ. Nhập theo dạng yyyy-MM-dd.");
            }
        }
    }

    public static boolean inputGender(Scanner scanner, String message) {
        while (true) {
            int choice = inputChoice(scanner, message + " (1.Nam - 2.Nữ): ");
            if (choice == 1) {
                return true;
            }
            if (choice == 2) {
                return false;
            }
            System.out.println("Chỉ được nhập 1 hoặc 2.");
        }
    }

    public static boolean inputYesNo(Scanner scanner, String message) {
        while (true) {
            System.out.print(message + " (y/n): ");
            String value = scanner.nextLine().trim().toLowerCase();
            if (value.equals("y")) {
                return true;
            }
            if (value.equals("n")) {
                return false;
            }
            System.out.println("Chỉ được nhập y hoặc n.");
        }
    }
}