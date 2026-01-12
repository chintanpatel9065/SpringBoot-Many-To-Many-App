package org.chintanpatel.springbootmanytomanyapp.student;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setStudentId(1L);
        student.setName("John Doe");
        student.setEmail("john@example.com");
    }

    @Test
    void testAddStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student savedStudent = studentService.addStudent(new Student());
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getName()).isEqualTo("John Doe");
    }

    @Test
    void testGetAllStudentList() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));
        List<Student> students = studentService.getAllStudentList();
        assertThat(students).hasSize(1);
    }

    @Test
    void testGetStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Optional<Student> foundStudent = studentService.getStudentById(1L);
        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void testDeleteStudentById() {
        doNothing().when(studentRepository).deleteById(1L);
        studentService.deleteStudentById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testIsEmailExist() {
        when(studentRepository.existsByEmail("john@example.com")).thenReturn(true);
        boolean exists = studentService.isEmailExist("john@example.com");
        assertThat(exists).isTrue();
    }

    @Test
    void testSearchStudents() {
        when(studentRepository.searchStudents("John")).thenReturn(Arrays.asList(student));
        List<Student> results = studentService.searchStudents("John");
        assertThat(results).hasSize(1);
    }
}
