package utils;

public class Validator {
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^[0-9]{9,11}$");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}
