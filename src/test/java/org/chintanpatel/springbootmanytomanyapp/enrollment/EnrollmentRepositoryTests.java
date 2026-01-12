package org.chintanpatel.springbootmanytomanyapp.enrollment;

import org.chintanpatel.springbootmanytomanyapp.course.Course;
import org.chintanpatel.springbootmanytomanyapp.course.CourseRepository;
import org.chintanpatel.springbootmanytomanyapp.student.Student;
import org.chintanpatel.springbootmanytomanyapp.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class EnrollmentRepositoryTests {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    private Student student;
    private Course course;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setName("John Doe");
        student.setEmail("john@example.com");
        student.setMobileNumber("1234567890");
        student = studentRepository.save(student);

        course = new Course();
        course.setCourseName("Java Programming");
        course.setCourseCode("JAVA101");
        course.setDescription("Introduction to Java");
        course = courseRepository.save(course);
    }

    @Test
    void testSaveEnrollment() {
        Enrollment enrollment = new Enrollment(student, course);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        assertThat(savedEnrollment).isNotNull();
        assertThat(savedEnrollment.getEnrollmentId()).isGreaterThan(0);
        assertThat(savedEnrollment.getStudent().getStudentId()).isEqualTo(student.getStudentId());
        assertThat(savedEnrollment.getCourse().getCourseId()).isEqualTo(course.getCourseId());
    }

    @Test
    void testFindActiveEnrollmentsByStudentId() {
        Enrollment enrollment = new Enrollment(student, course);
        enrollmentRepository.save(enrollment);

        List<Enrollment> activeEnrollments = enrollmentRepository.findActiveEnrollmentsByStudentId(student.getStudentId());
        assertThat(activeEnrollments).hasSize(1);
        assertThat(activeEnrollments.get(0).getStatus()).isEqualTo("ACTIVE");
    }

    @Test
    void testFindActiveEnrollmentsByCourseId() {
        Enrollment enrollment = new Enrollment(student, course);
        enrollmentRepository.save(enrollment);

        List<Enrollment> activeEnrollments = enrollmentRepository.findActiveEnrollmentsByCourseId(course.getCourseId());
        assertThat(activeEnrollments).hasSize(1);
        assertThat(activeEnrollments.get(0).getStatus()).isEqualTo("ACTIVE");
    }

    @Test
    void testFindActiveEnrollments() {
        Enrollment enrollment = new Enrollment(student, course);
        enrollmentRepository.save(enrollment);

        Optional<Enrollment> foundEnrollment = enrollmentRepository.findActiveEnrollments(student.getStudentId(), course.getCourseId());
        assertThat(foundEnrollment).isPresent();
    }
}
