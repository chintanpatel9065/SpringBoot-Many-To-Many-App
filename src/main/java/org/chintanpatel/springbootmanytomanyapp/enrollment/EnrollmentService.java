package org.chintanpatel.springbootmanytomanyapp.enrollment;

import java.util.List;

public interface EnrollmentService {

    List<Enrollment>getActiveEnrollmentsByStudent(Long studentId);

    List<Enrollment>getActiveEnrollmentsByCourse(Long courseId);

    List<Enrollment>getAllEnrollmentsByStudent(Long studentId);

    List<Enrollment>getAllEnrollmentsByCourse(Long courseId);

    Enrollment enrollStudents(Long studentId, Long courseId);

    void unenrollStudents(Long studentId, Long courseId);
}
