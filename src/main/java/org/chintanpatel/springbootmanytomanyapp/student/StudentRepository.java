package org.chintanpatel.springbootmanytomanyapp.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("studentRepository")
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    @Query("select distinct s from Student s where lower(s.name)like lower(concat('%',:keyword,'%')) or " +
            "lower(s.email)like lower(concat('%',:keyword,'%'))")
    List<Student>searchStudents(String keyword);
}