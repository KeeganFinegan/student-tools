package com.studenttools.studenttools.controller;

import com.studenttools.studenttools.exception.NotFoundException;
import com.studenttools.studenttools.model.Student;
import com.studenttools.studenttools.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {



    @Autowired
    private StudentRepository studentRepository;


    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentRepository.findAll();

    }

    @GetMapping("/students/{id}")
    @ResponseBody
    public Student getStudentById(@PathVariable Long id) {
        Optional<Student> optStudent = studentRepository.findById(id);

        if(optStudent.isPresent()) {
            return optStudent.get();
        }else {
            throw new NotFoundException("Student not found with id " + id);
        }

    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student){
        return studentRepository.save(student);

    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent){
        return studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(updatedStudent.getFirstName());
                    student.setLastName(updatedStudent.getLastName());
                    student.setEmail(updatedStudent.getEmail());
                    return studentRepository.save(student);
                }).orElseThrow(() -> new NotFoundException("Student not found with id " + id));

    }

    @DeleteMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id){
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Student not found with id " + id));
    }

}
