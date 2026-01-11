package org.chintanpatel.springbootmanytomanyapp.enrollment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chintanpatel.springbootmanytomanyapp.course.Course;
import org.chintanpatel.springbootmanytomanyapp.student.Student;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "student_course")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id", nullable = false)
    private Long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enrolled_date", nullable = false)
    private LocalDateTime enrolledDate;

    @Column(name = "unenrolled_date")
    private LocalDateTime unenrolledDate;

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE";

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrolledDate = LocalDateTime.now();
        this.status = "ACTIVE";
    }
}
