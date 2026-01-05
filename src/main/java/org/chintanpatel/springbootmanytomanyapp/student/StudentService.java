package org.chintanpatel.springbootmanytomanyapp.student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student addStudent(Student student);

    List<Student>getAllStudentList();

    Optional<Student> getStudentById(Long studentId);

    void deleteStudentById(Long studentId);

    boolean isEmailExist(String email);

    List<Student> searchStudents(String keyword);
}
