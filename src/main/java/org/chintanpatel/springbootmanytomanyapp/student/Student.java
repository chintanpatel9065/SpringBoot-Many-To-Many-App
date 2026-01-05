package org.chintanpatel.springbootmanytomanyapp.student;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @NotEmpty(message = "Please Provide Student Name")
    @Column(name = "name", nullable = false)
    private String name;

    @Email(message = "Please Provide Valid Email")
    @NotEmpty(message = "Please Provide Student Email")
    @Column(name = "email", nullable = false)
    private String email;

    @NotEmpty(message = "Please Provide Student Mobile Number")
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment>enrollments = new ArrayList<>();
}
