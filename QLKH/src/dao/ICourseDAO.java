package dao;

import model.Course;

import java.util.List;

public interface ICourseDAO {
    List<Course> findAll();

    Course findById(int id);

    boolean addCourse(Course course);

    boolean updateCourse(Course course);

    boolean deleteById(int id);

    List<Course> findByName(String keyword);

    List<Course> sortById(boolean asc);

    List<Course> sortByName(boolean asc);
}
