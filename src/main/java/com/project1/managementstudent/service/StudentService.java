package com.project1.managementstudent.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project1.managementstudent.domain.Student;
import com.project1.managementstudent.repository.StudentRepository;
import com.project1.managementstudent.web.rest.errors.BadRequestAlertException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void importData(MultipartFile dataFile) {
        String pathFile = storeFile(dataFile);
        try (FileInputStream file = new FileInputStream(new File(pathFile));) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Row header = sheet.getRow(0);
            sheet.removeRow(header);
            List<Student> students = new ArrayList<Student>();
            for (Row row : sheet) {
                Student student = new Student();
                for (Cell cell : row) {
                    cell.setCellType(CellType.STRING);
                }
                if (row.getCell(0) != null) {
                    student.setExamId(row.getCell(0).getStringCellValue());
                }
                if (row.getCell(1) != null) {
                    student.setClassEnrollmentId(row.getCell(1).getStringCellValue());

                }
                if (row.getCell(2) != null) {
                    student.setClassId(row.getCell(2).getStringCellValue());

                }
                if (row.getCell(3) != null) {
                    student.setCourseId(row.getCell(3).getStringCellValue());

                }
                if (row.getCell(4) != null) {
                    student.setCourseName(row.getCell(4).getStringCellValue());

                }
                if (row.getCell(5) != null) {
                    student.setSectionType(row.getCell(5).getStringCellValue());

                }
                if (row.getCell(6) != null) {
                    student.setNote(row.getCell(6).getStringCellValue());

                }
                if (row.getCell(7) != null) {
                    student.setNumberOrder(row.getCell(7).getStringCellValue());

                }
                if (row.getCell(8) != null) {
                    student.setSoMay(row.getCell(8).getStringCellValue());

                }
                if (row.getCell(9) != null) {
                    student.setStudentId(row.getCell(9).getStringCellValue());

                }
                if (row.getCell(10) != null) {
                    student.setStudentName(row.getCell(10).getStringCellValue());

                }
                if (row.getCell(11) != null) {
                    student.setBirthday(row.getCell(11).getStringCellValue());

                }
                if (row.getCell(12) != null) {
                    student.setGroupName(row.getCell(12).getStringCellValue());

                }
                if (row.getCell(13) != null) {
                    student.setTermId(row.getCell(13).getStringCellValue());

                }
                if (row.getCell(14) != null) {
                    student.setAcademicName(row.getCell(14).getStringCellValue());

                }
                if (row.getCell(15) != null) {
                    student.setStudyGroupName(row.getCell(15).getStringCellValue());

                }
                if (row.getCell(16) != null) {
                    student.setStudyGroupId(row.getCell(16).getStringCellValue());

                }
                if (row.getCell(17) != null) {
                    student.setTeacherName(row.getCell(17).getStringCellValue());

                }
                if (row.getCell(18) != null) {
                    student.setWeek(row.getCell(18).getStringCellValue());

                }
                if (row.getCell(19) != null) {
                    student.setThu(row.getCell(19).getStringCellValue());

                }
                if (row.getCell(20) != null) {
                    student.setDate(row.getCell(20).getStringCellValue());

                }
                if (row.getCell(21) != null) {
                    student.setKip(row.getCell(21).getStringCellValue());

                }
                if (row.getCell(22) != null) {
                    student.setTime(row.getCell(22).getStringCellValue());

                }
                if (row.getCell(23) != null) {
                    student.setRoom(row.getCell(23).getStringCellValue());

                }

                students.add(student);
            }
            studentRepository.saveAll(students);
            file.close();
            Files.delete(Paths.get(pathFile));
        } catch (Exception e) {
            e.printStackTrace();
            // throw new BadRequestAlertException(e.getMessage(), "student", "importData");
        }

    }

    public List<Student> findByCourseId(String courseId) {
        if (courseId == null) {
            throw new BadRequestAlertException("Course id must be not null", "student",
                    "courseIdIsNull");
        }

        return studentRepository.findByCourseId(courseId);
    }

    public List<Student> findByRoomAndExamId(String room, String examId) {
        if (room == null || examId == null) {

            throw new BadRequestAlertException("Params must be not null", "student",
                    "paramsIsNull");

        }

        return studentRepository.findByRoomAndExamId(room, examId);
    }

    private String storeFile(MultipartFile file) {

        try {
            UUID randomUUID = UUID.randomUUID();
            // Copy file to the target location (Replacing existing file with the same name)
            String newFileName = randomUUID.toString();
            Path targetLocation = Paths.get(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException ex) {
            throw new BadRequestAlertException("Could not store file " + ". Please try again!", "FileStorageService",
                    "errorSave");
        }
    }

    public long countStudents(String courseId, String classId, String teacherName) {
        if (courseId != null && classId != null && teacherName != null) {
            return studentRepository.countByCourseIdAndClassIdAndTeacherName(courseId, classId, teacherName);
        }

        if (courseId != null && classId == null && teacherName == null) {
            return studentRepository.countByCourseId(courseId);
        }

        if (courseId != null && classId != null && teacherName == null) {
            return studentRepository.countByCourseIdAndClassId(courseId, classId);
        }

        if (courseId != null && classId == null && teacherName != null) {
            return studentRepository.countByCourseIdAndTeacherName(courseId, teacherName);
        }

        if (courseId == null && classId != null && teacherName != null) {
            return studentRepository.countByClassIdAndTeacherName(classId, teacherName);
        }
        return 0;
    }
}
