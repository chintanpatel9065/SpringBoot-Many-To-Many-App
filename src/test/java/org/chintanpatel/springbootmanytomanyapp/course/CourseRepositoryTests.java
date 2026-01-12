package org.chintanpatel.springbootmanytomanyapp.course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CourseRepositoryTests {

    @Autowired
    private CourseRepository courseRepository;

    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        course1 = new Course();
        course1.setCourseName("Java Programming");
        course1.setCourseCode("JAVA101");
        course1.setDescription("Introduction to Java");

        course2 = new Course();
        course2.setCourseName("Spring Boot");
        course2.setCourseCode("SB202");
        course2.setDescription("Web development with Spring Boot");
    }

    @Test
    void testSaveCourse() {
        Course savedCourse = courseRepository.save(course1);
        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getCourseId()).isGreaterThan(0);
    }

    @Test
    void testExistsByCourseCode() {
        courseRepository.save(course1);
        boolean exists = courseRepository.existsByCourseCode("JAVA101");
        assertThat(exists).isTrue();
    }

    @Test
    void testSearchCourses() {
        courseRepository.save(course1);
        courseRepository.save(course2);

        List<Course> results = courseRepository.searchCourses("Java");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getCourseName()).isEqualTo("Java Programming");

        results = courseRepository.searchCourses("101");
        assertThat(results).hasSize(1);

        results = courseRepository.searchCourses("Boot");
        assertThat(results).hasSize(1);
    }
}
