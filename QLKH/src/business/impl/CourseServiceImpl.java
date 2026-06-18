// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package business.impl;

import business.ICourseService;
import dao.ICourseDAO;
import dao.impl.CourseDAOImpl;
import java.util.List;
import model.Course;

public class CourseServiceImpl implements ICourseService {
    private final ICourseDAO courseDAO = new CourseDAOImpl();

    public CourseServiceImpl() {
    }

    public List<Course> findAll() {
        return this.courseDAO.findAll();
    }

    public boolean addCourse(Course var1) {
        if (var1 == null) {
            return false;
        } else if (var1.getName() != null && !var1.getName().trim().isEmpty()) {
            if (var1.getInstructor() != null && !var1.getInstructor().trim().isEmpty()) {
                return var1.getDuration() <= 0 ? false : this.courseDAO.addCourse(var1);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Course findById(int var1) {
        return this.courseDAO.findById(var1);
    }

    public boolean updateCourse(Course var1) {
        if (var1 == null) {
            return false;
        } else if (var1.getName() != null && !var1.getName().trim().isEmpty()) {
            if (var1.getInstructor() != null && !var1.getInstructor().trim().isEmpty()) {
                return var1.getDuration() <= 0 ? false : this.courseDAO.updateCourse(var1);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean deleteById(int var1) {
        return this.courseDAO.deleteById(var1);
    }

    public List<Course> findByName(String var1) {
        return this.courseDAO.findByName(var1);
    }

    public List<Course> sortById(boolean var1) {
        return this.courseDAO.sortById(var1);
    }

    public List<Course> sortByName(boolean var1) {
        return this.courseDAO.sortByName(var1);
    }
}
