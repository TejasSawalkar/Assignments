package com.example.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.model.Student;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StudentService {
	private final String filePath = "students.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Student> getAllStudents() throws IOException {
        return readStudentsFromFile();
    }

    public Optional<Student> getStudentById(String id) throws IOException {
        return readStudentsFromFile().stream().filter(student -> student.getId().equals(id)).findFirst();
    }

    public Student addStudent(Student student) throws IOException {
        List<Student> students = readStudentsFromFile();
        student.setId(UUID.randomUUID().toString());
        students.add(student);
        writeStudentsToFile(students);
        return student;
    }

    public Student updateStudent(Student updatedStudent) throws IOException {
        List<Student> students = readStudentsFromFile();
        students.stream().filter(student -> student.getId().equals(updatedStudent.getId())).forEach(student -> {
            student.setName(updatedStudent.getName());
            student.setEmail(updatedStudent.getEmail());
            student.setCourse(updatedStudent.getCourse());
            student.setAge(updatedStudent.getAge());
        });
        writeStudentsToFile(students);
        return updatedStudent;
    }

    public void deleteStudent(String id) throws IOException {
        List<Student> students = readStudentsFromFile();
        students.removeIf(student -> student.getId().equals(id));
        writeStudentsToFile(students);
    }

    private List<Student> readStudentsFromFile() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file, new TypeReference<List<Student>>() {});
    }

    private void writeStudentsToFile(List<Student> students) throws IOException {
        objectMapper.writeValue(new File(filePath), students);
    }
}
