package com.project1.managementstudent.web.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project1.managementstudent.domain.Student;
import com.project1.managementstudent.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class StudentResource {

    private final StudentService studentService;

    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Import data từ file excel")
    @PostMapping("/student/import-data")
    public ResponseEntity<Void> importData(@RequestPart MultipartFile dataFile) {
        studentService.importData(dataFile);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Lấy danh sách sinh viên theo mã học phần")
    @GetMapping("/student/course-id/{courseId}")
    public ResponseEntity<List<Student>> findByCourseId(@PathVariable String courseId) {
        return ResponseEntity.ok(studentService.findByCourseId(courseId));
    }

    @Operation(summary = "Lấy danh sách sinh viên của từng phòng thi theo từng lớp học")
    @GetMapping("/student")
    public ResponseEntity<List<Student>> findByRoomAndExamId(@RequestParam String room, @RequestParam String examId) {
        return ResponseEntity.ok(studentService.findByRoomAndExamId(room, examId));
    }

    @Operation(summary = "Thống kế số lượng sinh viên")
    @GetMapping("/student/count")
    public ResponseEntity<Long> countStudents(@RequestParam String courseId, @RequestParam String classId, @RequestParam String teacherName){
        return ResponseEntity.ok(studentService.countStudents(courseId, classId, teacherName));
    }
}
