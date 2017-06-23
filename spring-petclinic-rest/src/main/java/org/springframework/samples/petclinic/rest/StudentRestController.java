package org.springframework.samples.petclinic.rest;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;


@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/students")
public class StudentRestController {

    @Autowired
    private ClinicService clinicService;

    @RequestMapping(value = "/*/lastname/{lastName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Student>> getStudentsList(@PathVariable("lastName") String StudentLastName) {
        if (StudentLastName == null) {
            StudentLastName = "";
        }
        Collection<Student> students = this.clinicService.findStudentByLastName(StudentLastName);
        if (students.isEmpty()) {
            return new ResponseEntity<Collection<Student>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Student>>(students, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Student>> getStudents() {
        Collection<Student> students = this.clinicService.findAllStudents();
        if (students.isEmpty()) {
            return new ResponseEntity<Collection<Student>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Student>>(students, HttpStatus.OK);
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Student> getStudent(@PathVariable("studentId") int studentId) {
        Student student = null;
        student = this.clinicService.findStudentById(studentId);
        if (student == null) {
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Student> addStudent(@RequestBody @Valid Student student, BindingResult bindingResult,
                                              UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (student == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Student>(headers, HttpStatus.BAD_REQUEST);
        }
        this.clinicService.saveStudent(student);
        headers.setLocation(ucBuilder.path("/api/students/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<Student>(student, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Student> updateStudent(@PathVariable("studentId") int studentId, @RequestBody @Valid Student student,
                                                 BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        System.out.println("updateStudent");
        System.out.println("student id : " + studentId);
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (student == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Student>(headers, HttpStatus.BAD_REQUEST);
        }
        Student currentStudent = this.clinicService.findStudentById(studentId);
        if (currentStudent == null) {
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }

        currentStudent.setFirstName(student.getFirstName());
        currentStudent.setLastName(student.getLastName());
        currentStudent.setMajor(student.getMajor());
        currentStudent.setUsualMark(student.getUsualMark());
        currentStudent.setPracMark(student.getPracMark());
        currentStudent.setFinalMark(student.getFinalMark());
        currentStudent.setMajorMark(student.getMajorMark());
        System.out.println("currentStudent major mark : " + currentStudent.getMajorMark());
        this.clinicService.saveStudent(currentStudent);
        return new ResponseEntity<Student>(currentStudent, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public ResponseEntity<Void> deleteStudent(@PathVariable("studentId") int studentId) {
        Student student = this.clinicService.findStudentById(studentId);
        if (student == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deleteStudent(student);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
