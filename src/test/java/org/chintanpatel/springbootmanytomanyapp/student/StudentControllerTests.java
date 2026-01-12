package org.chintanpatel.springbootmanytomanyapp.student;

import org.chintanpatel.springbootmanytomanyapp.course.CourseService;
import org.chintanpatel.springbootmanytomanyapp.enrollment.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StudentControllerTests {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @Mock
    private CourseService courseService;

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void testListStudents() throws Exception {
        when(studentService.getAllStudentList()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/student-list"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    void testCreateStudentForm() throws Exception {
        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/student-form"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void testGetStudent() throws Exception {
        Student student = new Student();
        student.setStudentId(1L);
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/getStudent/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/student-form"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        mockMvc.perform(get("/students/deleteStudent/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attributeExists("successMessage"));
    }
}
