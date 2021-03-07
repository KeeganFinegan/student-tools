package com.studenttools.studenttools.repository;

import com.studenttools.studenttools.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade,Long> {

    List<Grade> findByStudentId(Long studentId);
}
