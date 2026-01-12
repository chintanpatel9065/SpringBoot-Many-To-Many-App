package org.chintanpatel.springbootmanytomanyapp.enrollment;

import org.chintanpatel.springbootmanytomanyapp.course.Course;
import org.chintanpatel.springbootmanytomanyapp.course.CourseRepository;
import org.chintanpatel.springbootmanytomanyapp.student.Student;
import org.chintanpatel.springbootmanytomanyapp.student.StudentRepository;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTests {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    private Student student;
    private Course course;
    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setStudentId(1L);
        student.setName("John Doe");

        course = new Course();
        course.setCourseId(1L);
        course.setCourseName("Java Programming");

        enrollment = new Enrollment(student, course);
    }

    @Test
    void testGetActiveEnrollmentsByStudent() {
        when(enrollmentRepository.findActiveEnrollmentsByStudentId(1L)).thenReturn(Arrays.asList(enrollment));
        List<Enrollment> result = enrollmentService.getActiveEnrollmentsByStudent(1L);
        assertThat(result).hasSize(1);
    }

    @Test
    void testEnrollStudents_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findActiveEnrollments(1L, 1L)).thenReturn(Optional.empty());
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        Enrollment result = enrollmentService.enrollStudents(1L, 1L);
        assertThat(result).isNotNull();
        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void testEnrollStudents_AlreadyEnrolled() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findActiveEnrollments(1L, 1L)).thenReturn(Optional.of(enrollment));

        assertThatThrownBy(() -> enrollmentService.enrollStudents(1L, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Student already enrolled in this course");
    }

    @Test
    void testUnenrollStudents_Success() {
        when(enrollmentRepository.findActiveEnrollments(1L, 1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        enrollmentService.unenrollStudents(1L, 1L);
        assertThat(enrollment.getStatus()).isEqualTo("UNENROLLED");
        assertThat(enrollment.getUnenrolledDate()).isNotNull();
        verify(enrollmentRepository, times(1)).save(enrollment);
    }
}
