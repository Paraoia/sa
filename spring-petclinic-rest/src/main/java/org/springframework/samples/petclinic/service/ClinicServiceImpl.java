/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers
 * Also a placeholder for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 */
@Service

public class ClinicServiceImpl implements ClinicService {

    private StudentRepository studentRepository;


    @Autowired
    public ClinicServiceImpl(
        StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student findStudentById(int id) throws DataAccessException{
        Student student = null;
        try {
            student = studentRepository.findById(id);
        } catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
            // just ignore not found exceptions for Jdbc/Jpa realization
            return null;
        }
        return student;
    }
    public Collection<Student> findAllStudents() throws DataAccessException{
        return studentRepository.findAll();
    }
    public void saveStudent(Student student) throws DataAccessException{
        studentRepository.save(student);
    }
    public void deleteStudent(Student student) throws DataAccessException{
        studentRepository.delete(student);
    }
    public Collection<Student> findStudentByLastName(String lastName) throws DataAccessException{
        return studentRepository.findByLastName(lastName);
    }


}
