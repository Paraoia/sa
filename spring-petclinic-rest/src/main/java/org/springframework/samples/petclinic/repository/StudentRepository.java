package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Student;


public interface StudentRepository {

    Student findById(int id) throws DataAccessException;
    void save(Student student) throws DataAccessException;
    Collection<Student> findAll() throws DataAccessException;
    void delete(Student student) throws DataAccessException;
    Collection<Student> findByLastName(String lastName) throws DataAccessException;
}
