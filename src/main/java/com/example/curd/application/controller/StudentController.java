package com.example.curd.application.controller;

import com.example.curd.application.dto.StudentRequestDto;
import com.example.curd.application.dto.StudentResponseDto;
import com.example.curd.application.entity.Student;
import com.example.curd.application.repository.StudentRepository;
import com.example.curd.application.translator.StudentTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentTranslator studentTranslator;


    @PostMapping("/create")
    public ResponseEntity<StudentResponseDto> createStudent(@RequestBody StudentRequestDto studentRequestDto) {
        Student student = studentTranslator.translateToEntity(studentRequestDto);
        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.ok(studentTranslator.translateToDto(savedStudent));
    }

    // ✅ Read (Get all)
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        List<StudentResponseDto> students = studentRepository.findAll()
                .stream()
                .map(studentTranslator::translateToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> ResponseEntity.ok(studentTranslator.translateToDto(student)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable Long id,
                                                            @RequestBody StudentRequestDto studentRequestDto) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setName(studentRequestDto.getName());
                    Student updatedStudent = studentRepository.save(existingStudent);
                    return ResponseEntity.ok(studentTranslator.translateToDto(updatedStudent));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
