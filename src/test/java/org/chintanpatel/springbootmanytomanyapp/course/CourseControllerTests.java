package org.chintanpatel.springbootmanytomanyapp.course;

import org.chintanpatel.springbootmanytomanyapp.enrollment.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CourseControllerTests {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void testListCourses() throws Exception {
        when(courseService.getAllCourseList()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/course-list"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    void testCreateCourseForm() throws Exception {
        mockMvc.perform(get("/courses/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/course-form"))
                .andExpect(model().attributeExists("course"));
    }

    @Test
    void testGetCourse() throws Exception {
        Course course = new Course();
        course.setCourseId(1L);
        when(courseService.getCourseById(1L)).thenReturn(Optional.of(course));

        mockMvc.perform(get("/courses/getCourse/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("course/course-form"))
                .andExpect(model().attributeExists("course"));
    }

    @Test
    void testDeleteCourse() throws Exception {
        mockMvc.perform(get("/courses/deleteCourse/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"))
                .andExpect(flash().attributeExists("successMessage"));
    }
}
