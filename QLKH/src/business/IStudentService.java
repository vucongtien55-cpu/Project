package business;

import model.Student;
import java.util.List;

public interface IStudentService {
    List<Student> findAll();

    boolean addStudent(Student student);

    Student findById(int id);

    boolean updateStudent(Student student);

    boolean deleteById(int id);

    List<Student> searchStudent(String keyword);

    List<Student> sortById(boolean asc);

    List<Student> sortByName(boolean asc);

    Student login(String email, String password);

    boolean changePassword(int studentId, String oldPassword, String newPassword);
}