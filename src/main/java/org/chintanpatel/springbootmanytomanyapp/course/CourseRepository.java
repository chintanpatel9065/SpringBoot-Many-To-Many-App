package org.chintanpatel.springbootmanytomanyapp.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("courseRepository")
public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCourseCode(String courseCode);

    @Query("select distinct c from Course c where lower(c.courseName)like lower(concat('%',:keyword,'%')) or " +
            "lower(c.courseCode) like lower(concat('%',:keyword,'%'))")
    List<Course>searchCourses(String keyword);
}