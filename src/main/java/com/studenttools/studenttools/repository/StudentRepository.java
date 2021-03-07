package com.studenttools.studenttools.repository;

import com.studenttools.studenttools.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
