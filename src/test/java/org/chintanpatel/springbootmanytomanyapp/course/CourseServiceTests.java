package org.chintanpatel.springbootmanytomanyapp.course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTests {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setCourseId(1L);
        course.setCourseName("Java Programming");
        course.setCourseCode("JAVA101");
    }

    @Test
    void testAddCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        Course savedCourse = courseService.addCourse(new Course());
        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getCourseName()).isEqualTo("Java Programming");
    }

    @Test
    void testGetAllCourseList() {
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));
        List<Course> courses = courseService.getAllCourseList();
        assertThat(courses).hasSize(1);
    }

    @Test
    void testGetCourseById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Optional<Course> foundCourse = courseService.getCourseById(1L);
        assertThat(foundCourse).isPresent();
        assertThat(foundCourse.get().getCourseName()).isEqualTo("Java Programming");
    }

    @Test
    void testDeleteCourseById() {
        doNothing().when(courseRepository).deleteById(1L);
        courseService.deleteCourseById(1L);
        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testIsCourseCodeExist() {
        when(courseRepository.existsByCourseCode("JAVA101")).thenReturn(true);
        boolean exists = courseService.isCourseCodeExist("JAVA101");
        assertThat(exists).isTrue();
    }

    @Test
    void testSearchCourses() {
        when(courseRepository.searchCourses("Java")).thenReturn(Arrays.asList(course));
        List<Course> results = courseService.searchCourses("Java");
        assertThat(results).hasSize(1);
    }
}
