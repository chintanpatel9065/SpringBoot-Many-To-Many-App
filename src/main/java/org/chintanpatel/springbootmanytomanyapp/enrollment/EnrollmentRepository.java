package org.chintanpatel.springbootmanytomanyapp.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("enrollmentRepository")
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("select e from Enrollment e where e.student.studentId = :studentId and e.status = 'ACTIVE'")
    List<Enrollment>findActiveEnrollmentsByStudentId(@Param("studentId") Long studentId);

    @Query("select e from Enrollment e where e.course.courseId = :courseId and e.status = 'ACTIVE'")
    List<Enrollment>findActiveEnrollmentsByCourseId(@Param("courseId") Long courseId);

    @Query("select e from Enrollment e where e.student.studentId = :studentId and e.course.courseId = :courseId and e.status = 'ACTIVE'")
    Optional<Enrollment>findActiveEnrollments(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Query("select e from Enrollment e where e.student.studentId = :studentId order by e.enrolledDate desc")
    List<Enrollment>findAllEnrollmentsByStudentId(@Param("studentId") Long studentId);

    @Query("select e from Enrollment e where e.course.courseId = :courseId order by e.enrolledDate desc")
    List<Enrollment>findAllEnrollmentsByCourseId(@Param("courseId") Long courseId);
}