package org.chintanpatel.springbootmanytomanyapp.course;

import jakarta.validation.Valid;
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

@Controller
public class CourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public CourseController(CourseService courseService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    // Create a method to list courses with optional keyword search and active enrollment count

    @GetMapping("/courses")
    public String listCourses(@RequestParam(required = false) String keyword, Model model) {
        List<Course> courseList;
        if (keyword == null || keyword.isEmpty()) {
            courseList = courseService.getAllCourseList();
        } else {
            courseList = courseService.searchCourses(keyword);
        }
        Map<Long, Long>activeCountMap = new HashMap<>();
        for (Course course : courseList) {
            long activeCount = course.getEnrollments().stream().filter(enrollment -> "ACTIVE".equals(enrollment.getStatus())).count();
            activeCountMap.put(course.getCourseId(), activeCount);
        }
        model.addAttribute("courses", courseList);
        model.addAttribute("activeCounts", activeCountMap);
        return "course/course-list";
    }

    // Create a method for displaying a course form

    @GetMapping("/courses/new")
    public String createCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "course/course-form";
    }

    // Create a method for inserting or updating a course

    @PostMapping("/courses/insertOrUpdateCourse")
    public String insertOrUpdateCourse(@Valid @ModelAttribute("course")Course course, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "course/course-form";
        }
        if (course.getCourseId() != null) {
            course.setCourseId(course.getCourseId());
            courseService.addCourse(course);
            redirectAttributes.addFlashAttribute("successMessage", "Course updated successfully");
        } else {
            if (courseService.isCourseCodeExist(course.getCourseCode())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Course code already exists");
            }
            courseService.addCourse(course);
            redirectAttributes.addFlashAttribute("successMessage", "Course saved successfully");
        }
        return "redirect:/courses";
    }

    // Create a method for getting a course by ID

    @GetMapping("/courses/getCourse/{courseId}")
    public String getCourse(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourseById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id :: " + courseId));
        model.addAttribute("course", course);
        return "course/course-form";
    }

    // Create a method for deleting a course by ID

    @GetMapping("/courses/deleteCourse/{courseId}")
    public String deleteCourse(@PathVariable Long courseId, RedirectAttributes redirectAttributes) {
        courseService.deleteCourseById(courseId);
        redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully");
        return "redirect:/courses";
    }

    // Create a method for viewing the list of students enrolled in a course

    @GetMapping("/courses/{courseId}/viewStudents")
    public String viewCourseStudents(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourseById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id :: " + courseId));
        List<Enrollment>activeEnrollments = enrollmentService.getActiveEnrollmentsByCourse(courseId);
        model.addAttribute("course", course);
        model.addAttribute("activeEnrollments", activeEnrollments);
        return "course/view-students";
    }

    // Create a method for viewing the enrollment history of a course

    @GetMapping("/courses/{courseId}/viewEnrollmentHistory")
    public String viewCourseEnrollmentHistory(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourseById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id :: " + courseId));
        List<Enrollment>allEnrollmentsByCourse = enrollmentService.getAllEnrollmentsByCourse(courseId);
        model.addAttribute("course", course);
        model.addAttribute("allEnrollmentsByCourse", allEnrollmentsByCourse);
        return "course/view-enrollment-history";
    }
}
