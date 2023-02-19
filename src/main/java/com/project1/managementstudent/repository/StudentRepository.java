package com.project1.managementstudent.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project1.managementstudent.domain.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findByCourseId(String courseId);

    List<Student> findByRoomAndExamId(String room, String examId);

    long countByCourseId(String courseId);

    long countByCourseIdAndClassId(String courseId, String classId);

    long countByCourseIdAndTeacherName(String courseId, String teacherName);

    long countByClassIdAndTeacherName(String classId, String teacherName);

    long countByCourseIdAndClassIdAndTeacherName(String courseId,String classId, String teacherName);
}
