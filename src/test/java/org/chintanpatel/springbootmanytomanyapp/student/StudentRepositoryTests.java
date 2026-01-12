package org.chintanpatel.springbootmanytomanyapp.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        student1 = new Student();
        student1.setName("John Doe");
        student1.setEmail("john@example.com");
        student1.setMobileNumber("1234567890");

        student2 = new Student();
        student2.setName("Jane Doe");
        student2.setEmail("jane@example.com");
        student2.setMobileNumber("0987654321");
    }

    @Test
    void testSaveStudent() {
        Student savedStudent = studentRepository.save(student1);
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getStudentId()).isGreaterThan(0);
    }

    @Test
    void testExistsByEmail() {
        studentRepository.save(student1);
        boolean exists = studentRepository.existsByEmail("john@example.com");
        assertThat(exists).isTrue();
    }

    @Test
    void testSearchStudents() {
        studentRepository.save(student1);
        studentRepository.save(student2);

        List<Student> results = studentRepository.searchStudents("John");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("John Doe");

        results = studentRepository.searchStudents("example.com");
        assertThat(results).hasSize(2);
    }
}
