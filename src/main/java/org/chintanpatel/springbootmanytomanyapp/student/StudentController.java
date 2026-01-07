package org.chintanpatel.springbootmanytomanyapp.student;

import jakarta.validation.Valid;
import org.chintanpatel.springbootmanytomanyapp.course.Course;
import org.chintanpatel.springbootmanytomanyapp.course.CourseService;
import org.chintanpatel.springbootmanytomanyapp.enrollment.Enrollment;
import org.chintanpatel.springbootmanytomanyapp.enrollment.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public StudentController(StudentService studentService, CourseService courseService, EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    // Create a method for listing students with optional keyword search

    @GetMapping("/students")
    public String listStudents(@RequestParam(required = false) String keyword, Model model) {
        List<Student>studentList;
        if (keyword == null || keyword.isEmpty()) {
            studentList = studentService.getAllStudentList();
        } else {
            studentList = studentService.searchStudents(keyword);
        }
        Map<Long, Long>activeCountMap = new HashMap<>();
        for (Student student : studentList) {
            long activeCount = student.getEnrollments().stream().filter(enrollment -> "ACTIVE".equals(enrollment.getStatus())).count();
            activeCountMap.put(student.getStudentId(), activeCount);
        }
        model.addAttribute("students", studentList);
        model.addAttribute("activeCountMap", activeCountMap);
        return "student/student-list";
    }

    // Create a method for displaying a student form

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/student-form";
    }

    // Create a method for inserting or updating a student

    @PostMapping("/students/insertOrUpdateStudent")
    public String insertOrUpdateStudent(@Valid @ModelAttribute("student")Student student, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "student/student-form";
        }
        if (student.getStudentId() != null) {
            student.setStudentId(student.getStudentId());
            studentService.addStudent(student);
            redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully");
        } else {
            if (studentService.isEmailExist(student.getEmail())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Email already exists");
                return "redirect:/students/new";
            }
            studentService.addStudent(student);
            redirectAttributes.addFlashAttribute("successMessage", "Student added successfully");
        }
        return "redirect:/students";
    }

    // Create a method for getting a student by ID

    @GetMapping("/students/getStudent/{studentId}")
    public String getStudent(@PathVariable Long studentId, Model model) {
        Student student = studentService.getStudentById(studentId).orElseThrow(() -> new RuntimeException("Student not found with id :: " + studentId));
        model.addAttribute("student", student);
        return "student/student-form";
    }

    // Create a method for deleting a student by ID

    @GetMapping("/students/deleteStudent/{studentId}")
    public String deleteStudent(@PathVariable Long studentId, RedirectAttributes redirectAttributes) {
        studentService.deleteStudentById(studentId);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully");
        return "redirect:/students";
    }

    // Create a method for managing student courses

    @GetMapping("/students/{studentId}/courses")
    public String manageStudentCourse(@PathVariable Long studentId, Model model) {
        Student student = studentService.getStudentById(studentId).orElseThrow(() -> new RuntimeException("Student not found with id :: " + studentId));
        List<Enrollment>activeEnrollments = enrollmentService.getActiveEnrollmentsByStudent(studentId);
        List<Course>courseList = courseService.getAllCourseList();
        List<Long>enrolledCourseId = activeEnrollments.stream().map(enrollment -> enrollment.getCourse().getCourseId()).collect(Collectors.toList());
        List<Course>availableCourses = courseList.stream().filter(course -> !enrolledCourseId.contains(course.getCourseId())).collect(Collectors.toList());
        model.addAttribute("student", student);
        model.addAttribute("activeEnrollments", activeEnrollments);
        model.addAttribute("availableCourses", availableCourses);
        return "student/manage-course";
    }

    // Create a method for enrolling a student in a course

    @PostMapping("/students/{studentId}/enrollCourse/{courseId}")
    public String enrolledStudent(@PathVariable Long studentId, @PathVariable Long courseId, RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.enrollStudents(studentId, courseId);
            redirectAttributes.addFlashAttribute("successMessage", "Student enrolled successfully");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",e.getMessage());
        }
        return "redirect:/students/" + studentId + "/courses";
    }

    // Create a method for unenrolling a student from a course

    @PostMapping("/students/{studentId}/unenrollCourse/{courseId}")
    public String unenrolledStudent(@PathVariable Long studentId, @PathVariable Long courseId, RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.unenrollStudents(studentId, courseId);
            redirectAttributes.addFlashAttribute("successMessage", "Student unenrolled successfully");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/students/" + studentId + "/courses";
    }

    // Create a method for viewing the enrollment history of a student

    @GetMapping("/students/{studentId}/viewEnrollmentHistory")
    public String viewEnrollmentHistory(@PathVariable Long studentId, Model model) {
        Student student = studentService.getStudentById(studentId).orElseThrow(() -> new RuntimeException("Student not found with id :: " + studentId));
        List<Enrollment>allEnrollmentsByStudent = enrollmentService.getAllEnrollmentsByStudent(studentId);
        model.addAttribute("student", student);
        model.addAttribute("allEnrollmentsByStudent", allEnrollmentsByStudent);
        return "student/view-enrollment-history";
    }
}
