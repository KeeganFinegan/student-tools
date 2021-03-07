package com.studenttools.studenttools.controller;

import com.studenttools.studenttools.exception.NotFoundException;
import com.studenttools.studenttools.model.Grade;
import com.studenttools.studenttools.repository.GradeRepository;
import com.studenttools.studenttools.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GradeController {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;


    @GetMapping("/students/{studentId}/grades")
    public List<Grade> getGradeByStudentId(@PathVariable Long studentId){

        if(!studentRepository.existsById(studentId)){
            throw new NotFoundException("Student not found!");
        }
        return gradeRepository.findByStudentId(studentId);
    }

    @PostMapping("/students/{studentId}/grades")
    public Grade addGrade(@PathVariable Long studentId, @RequestBody Grade grade){
        return studentRepository.findById(studentId)
                .map(student -> {
                    grade.setStudent(student);
                    return gradeRepository.save(grade);
                }).orElseThrow(() -> new NotFoundException("Student not found!"));
    }

    @PutMapping("/students/{studentId}/grades/{gradeId}")
    public Grade updateGrade(@PathVariable Long studentId,
                             @PathVariable Long gradeId,
                             @RequestBody Grade updatedGrade){

        if(!studentRepository.existsById(studentId)){
            throw new NotFoundException("Student not found!");
        }
        return gradeRepository.findById(gradeId)
                .map(grade -> {
                    grade.setAssessment(updatedGrade.getAssessment());
                    grade.setScore(updatedGrade.getScore());
                    grade.setWeight(updatedGrade.getWeight());
                    grade.setPercentage(updatedGrade.getPercentage());

                    return gradeRepository.save(grade);
                }).orElseThrow(() -> new NotFoundException("Grade not found!"));

    }

    @DeleteMapping("/students/{studentId}/grades/{gradeId}")
    String deleteGrade(@PathVariable Long studentId, @PathVariable Long gradeId){
        if(!studentRepository.existsById(studentId)){
            throw new NotFoundException("Student not found!");
        }
        return gradeRepository.findById(gradeId)
                .map(grade -> {
                    gradeRepository.delete(grade);
                    return "Grade deleted successfully!";
                }).orElseThrow(() -> new NotFoundException("Grade not found!"));
    }


}
