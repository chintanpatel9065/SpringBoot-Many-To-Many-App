package org.chintanpatel.springbootmanytomanyapp.enrollment;

import org.chintanpatel.springbootmanytomanyapp.course.Course;
import org.chintanpatel.springbootmanytomanyapp.course.CourseRepository;
import org.chintanpatel.springbootmanytomanyapp.student.Student;
import org.chintanpatel.springbootmanytomanyapp.student.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service("enrollmentService")
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Enrollment>getActiveEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findActiveEnrollmentsByStudentId(studentId);
    }

    @Override
    public List<Enrollment>getActiveEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findActiveEnrollmentsByCourseId(courseId);
    }

    @Override
    public List<Enrollment>getAllEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findAllEnrollmentsByStudentId(studentId);
    }

    @Override
    public List<Enrollment>getAllEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findAllEnrollmentsByCourseId(courseId);
    }

    @Override
    public Enrollment enrollStudents(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found with id :: " + studentId));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id :: " + courseId));
        Optional<Enrollment>existingEnrollment = enrollmentRepository.findActiveEnrollments(studentId, courseId);
        if (existingEnrollment.isPresent()) {
            throw new RuntimeException("Student already enrolled in this course");
        }
        Enrollment enrollment = new Enrollment(student, course);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void unenrollStudents(Long studentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findActiveEnrollments(studentId, courseId).orElseThrow(() -> new RuntimeException("Student not enrolled in this course"));
        enrollment.setStatus("UNENROLLED");
        enrollment.setUnenrolledDate(LocalDateTime.now());
        enrollmentRepository.save(enrollment);
    }
}
