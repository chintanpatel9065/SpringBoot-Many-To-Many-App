package org.chintanpatel.springbootmanytomanyapp.student;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service("studentService")
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student>getAllStudentList() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student>getStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public void deleteStudentById(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public boolean isEmailExist(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public List<Student>searchStudents(String keyword) {
        return studentRepository.searchStudents(keyword);
    }
}
