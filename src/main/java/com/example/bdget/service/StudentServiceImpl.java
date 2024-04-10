package com.example.bdget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bdget.model.Student;
import com.example.bdget.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        if (id == null) {
            System.out.println("El id es nulo");
            return null;
        }
        return studentRepository.findById(id);
    }

    @Override
    public Student createStudent(Student student) {
        // Validar que el objeto Student no sea nulo
        if (student == null) {
            return null;
        }

        // Validar que el nombre no esté vacío
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return null;
        }

        // Validar que el nombre tenga una longitud máxima
        if (student.getName().length() > 50) {
            return null;
        }

        // Otras validaciones según las reglas de negocio

        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {

        if (id == null || id < 0 || student == null || !studentRepository.existsById(id)) {
            System.out.println("Estudiante no válido");
            return null;
        }

        if (studentRepository.existsById(id)) {
            student.setId(id);
            return studentRepository.save(student);
        } else {
            return null;
        }
    }

    @Override
    public void deleteStudent(Long id) {

        if (id == null || id < 0 || !studentRepository.existsById(id)) {
            return;
        }
        studentRepository.deleteById(id);
    }
}
