package com.example.curd.application.translator;

import com.example.curd.application.dto.StudentRequestDto;
import com.example.curd.application.dto.StudentResponseDto;
import com.example.curd.application.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentTranslator {
      public Student translateToEntity(StudentRequestDto studentRequestDto){

          Student student= new Student();
          student.setId(studentRequestDto.getId());
          student.setName(studentRequestDto.getName());
          student.setRollNo(studentRequestDto.getRollNo());
          return student;
      }

      public StudentResponseDto translateToDto(Student student){
          StudentResponseDto studentResponseDto=new StudentResponseDto();
          studentResponseDto.setName(student.getName());
          studentResponseDto.setRollNo(student.getRollNo());
          return studentResponseDto;
      }

}
