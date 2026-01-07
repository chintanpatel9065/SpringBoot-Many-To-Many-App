package org.chintanpatel.springbootmanytomanyapp.course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    Course addCourse(Course course);

    List<Course>getAllCourseList();

    Optional<Course>getCourseById(Long courseId);

    void deleteCourseById(Long courseId);

    boolean isCourseCodeExist(String courseCode);

    List<Course>searchCourses(String keyword);
}
