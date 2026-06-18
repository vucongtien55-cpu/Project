// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package business.impl;

import business.IStudentService;
import dao.IStudentDAO;
import dao.impl.StudentDAOImpl;
import java.util.List;
import model.Student;
import utils.Validator;

public class StudentServiceImpl implements IStudentService {
    private final IStudentDAO studentDAO = new StudentDAOImpl();

    public StudentServiceImpl() {
    }

    public List<Student> findAll() {
        return this.studentDAO.findAll();
    }

    public boolean addStudent(Student var1) {
        if (var1 == null) {
            return false;
        } else if (!Validator.isValidEmail(var1.getEmail())) {
            return false;
        } else if (!Validator.isValidPhone(var1.getPhone())) {
            return false;
        } else if (!Validator.isValidPassword(var1.getPassword())) {
            return false;
        } else {
            return this.studentDAO.existsByEmail(var1.getEmail()) ? false : this.studentDAO.addStudent(var1);
        }
    }

    public Student findById(int var1) {
        return this.studentDAO.findById(var1);
    }

    public boolean updateStudent(Student var1) {
        if (var1 == null) {
            return false;
        } else if (!Validator.isValidEmail(var1.getEmail())) {
            return false;
        } else if (!Validator.isValidPhone(var1.getPhone())) {
            return false;
        } else if (!Validator.isValidPassword(var1.getPassword())) {
            return false;
        } else {
            return this.studentDAO.existsByEmailExceptId(var1.getEmail(), var1.getId()) ? false : this.studentDAO.updateStudent(var1);
        }
    }

    public boolean deleteById(int var1) {
        return this.studentDAO.deleteById(var1);
    }

    public List<Student> searchStudent(String var1) {
        return this.studentDAO.searchStudent(var1);
    }

    public List<Student> sortById(boolean var1) {
        return this.studentDAO.sortById(var1);
    }

    public List<Student> sortByName(boolean var1) {
        return this.studentDAO.sortByName(var1);
    }

    public Student login(String var1, String var2) {
        return this.studentDAO.login(var1, var2);
    }

    public boolean changePassword(int var1, String var2, String var3) {
        return !Validator.isValidPassword(var3) ? false : this.studentDAO.changePassword(var1, var2, var3);
    }
}
