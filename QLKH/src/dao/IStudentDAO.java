package dao;

import model.Student;

import java.util.List;

public interface IStudentDAO {
    List<Student> findAll();

    Student findById(int id);

    boolean addStudent(Student student);

    boolean updateStudent(Student student);

    boolean deleteById(int id);

    List<Student> searchStudent(String keyword);

    List<Student> sortById(boolean asc);

    List<Student> sortByName(boolean asc);

    Student login(String email, String password);

    boolean changePassword(int studentId, String oldPassword, String newPassword);

    boolean existsByEmail(String email);

    boolean existsByEmailExceptId(String email, int id);
}
