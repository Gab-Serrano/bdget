package com.example.bdget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/students")
public class StudentController {

    // Inicializa logger
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        log.info("GET /students");
        log.info("Retornando todos los estudantes");
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);

        if (student.isEmpty()) {
            log.error("No se encontro el estudiante con ID {}", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Estudiante encontrado con exito");
        return ResponseEntity.ok(studentService.getStudentById(id));
    }


//Devuelve respuesta JSON con error
/* 
    @GetMapping("/{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            log.error("No se encontró el estudiante con ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontró el estudiante con ID " + id));
        }
        return ResponseEntity.ok(student);
    }
 */

    @PostMapping
    public ResponseEntity<Student> creaStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        if (createdStudent == null) {
            log.error("Error al crear el estudiante");
            return ResponseEntity.badRequest().build();
        }
        log.info("Estudiante creado con éxito");
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    //Devuelve respuesta JSON con error
/* 
    @PostMapping
    public ResponseEntity<Object> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        if (createdStudent == null) {
            log.error("Error al crear el estudiante {}", student);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error al crear el estudiante"));
        }
        return ResponseEntity.ok(createdStudent);
    }
 */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        if (id == null || student == null) {
            log.error("ID o estudiante nulos en la solicitud de actualizacion");
            return ResponseEntity.badRequest().build();
        }

        Student updatedStudent = studentService.updateStudent(id, student);
        if (updatedStudent == null) {
            log.error("No se encontro el estudiante con ID {} para actualizar", id);
            return ResponseEntity.badRequest().build();
        }
        log.info("Estudiante actualizado con exito");
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    //Devuelve respuesta JSON con error
/* 
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        if (id == null || student == null) {
            log.error("ID o estudiante nulos en la solicitud de actualización");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("ID o estudiante nulos en la solicitud");
        }
        Student updatedStudent = studentService.updateStudent(id, student);
        if (updatedStudent == null) {
            log.error("No se encontró el estudiante con ID {} para actualizar", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No se encontró el estudiante con ID " + id));
        }
        return ResponseEntity.ok(updatedStudent);
    }
 */

/* 
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            log.error("No se encontro el estudiante con ID {} para eliminar", id);
            ResponseEntity.notFound().build();
        } else {
            log.info("Estudiante eliminado con exito");
            studentService.deleteStudent(id);
            ResponseEntity.ok().build();
        }
    }
 */
    //Devuelve respuesta JSON con error

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            log.error("No se encontró el estudiante con ID {} para eliminar", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No se encontró el estudiante con ID " + id));
        }
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Estudiante eliminado correctamente");
    }


// Clases auxiliares para las respuestas JSON

static class ErrorResponse {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

static class SuccessResponse {
    private final String message;

    public SuccessResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
}

