package com.example.service;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(int id){
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public void deleteStudent(int id){
        Student student=studentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("com.example.demo.entity.Student not found"));
        studentRepository.delete(student);
    }

    public Student updateStudent(int id,Student studentDetails){
        Student student=studentRepository.findById(id).orElseThrow(()->new RuntimeException("com.example.demo.entity.Student not found"));
        student.setName(studentDetails.getName());
        student.setAge(studentDetails.getAge());
        student.setEmail(studentDetails.getEmail());
        student.setCourse(studentDetails.getCourse());
        return studentRepository.save(student);
    }
}
