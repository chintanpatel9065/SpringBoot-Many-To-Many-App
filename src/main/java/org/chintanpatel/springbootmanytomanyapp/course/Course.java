package org.chintanpatel.springbootmanytomanyapp.course;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chintanpatel.springbootmanytomanyapp.enrollment.Enrollment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @NotEmpty(message = "Please Provide Course Name")
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @NotEmpty(message = "Please Provide Course Code")
    @Column(name = "course_code", nullable = false)
    private String courseCode;

    @NotEmpty(message = "Please Provide Course Description")
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment>enrollments = new ArrayList<>();
}
