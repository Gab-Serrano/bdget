package com.example.bdget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.bdget.model.Student;
import com.example.bdget.service.StudentService;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }
        
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> getStudentById(@PathVariable Long id) {
        if(studentService.getStudentById(id) == null || studentService.getStudentById(id).isEmpty() ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<Student> creaStudent(@RequestBody Student student) {

        if(studentService.createStudent(student) == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.createStudent(student));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        if(studentService.updateStudent(id, student) == null || id == null || student == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id){
        if(studentService.getStudentById(id) == null){
            ResponseEntity.notFound().build();
        }else{
            studentService.deleteStudent(id);
            ResponseEntity.ok().build();
        }
    }
}
