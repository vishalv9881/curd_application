package com.example.curd.application.service;

import com.example.curd.application.dto.StudentRequestDto;
import com.example.curd.application.dto.StudentResponseDto;
import com.example.curd.application.entity.Student;
import com.example.curd.application.repository.StudentRepository;
import com.example.curd.application.translator.StudentTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentTranslator studentTranslator;

    public StudentResponseDto createStudent(StudentRequestDto studentRequestDto) {
        Student student = studentTranslator.translateToEntity(studentRequestDto);
        Student savedStudent = studentRepository.save(student);
        return studentTranslator.translateToDto(savedStudent);
    }

    //  Get all
    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentTranslator::translateToDto)
                .collect(Collectors.toList());
    }

    //  Get by ID
    public Optional<StudentResponseDto> getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(studentTranslator::translateToDto);
    }

    //  Update
    public Optional<StudentResponseDto> updateStudent(Long id, StudentRequestDto studentRequestDto) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setName(studentRequestDto.getName());
                    // you can update more fields here if needed
                    Student updatedStudent = studentRepository.save(existingStudent);
                    return studentTranslator.translateToDto(updatedStudent);
                });
    }

    //  Delete
    public boolean deleteStudent(Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return true;
                })
                .orElse(false);
    }
}
