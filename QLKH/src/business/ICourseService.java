package business;

import model.Course;
import java.util.List;

public interface ICourseService {
    List<Course> findAll();

    boolean addCourse(Course course);

    Course findById(int id);

    boolean updateCourse(Course course);

    boolean deleteById(int id);

    List<Course> findByName(String keyword);

    List<Course> sortById(boolean asc);

    List<Course> sortByName(boolean asc);
}