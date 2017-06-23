package org.springframework.samples.petclinic.repository.jdbc;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.repository.StudentRepository;
import org.springframework.stereotype.Repository;


import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;


@Repository
@Profile("jdbc")
public class JdbcStudentRepositoryImpl implements StudentRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertStudent;

    @Autowired
    public JdbcStudentRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        this.insertStudent = new SimpleJdbcInsert(dataSource)
            .withTableName("students")
            .usingGeneratedKeyColumns("id");
    }


    public Collection<Student> findByLastName(String lastName) throws DataAccessException {
        Map<String, Object> params = new HashMap<>();
        params.put("lastName", lastName + "%");
        List<Student> students = this.namedParameterJdbcTemplate.query(
            "SELECT id, first_name, last_name, major, usual_mark, prac_mark, final_mark, major_mark FROM students WHERE last_name like :lastName",
            params,
            BeanPropertyRowMapper.newInstance(Student.class)
        );
        return students;
    }

    /**
     * Loads the {@link Student} with the supplied <code>id</code>; also loads the {@link Pet Pets} and {@link Visit Visits}
     * for the corresponding owner, if not already loaded.
     */
    @Override
    public Student findById(int id) throws DataAccessException {
        Student student;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            student = this.namedParameterJdbcTemplate.queryForObject(
                "SELECT id, first_name, last_name, major, usual_mark, prac_mark, final_mark, major_mark FROM students WHERE id=:id",
                params,
                BeanPropertyRowMapper.newInstance(Student.class)
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Student.class, id);
        }
        return student;
    }

    @Override
    public void save(Student student) throws DataAccessException {
        System.out.println("get save");
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(student);
        if (student.isNew()) {
            System.out.println("Update save but is new");
            Number newKey = this.insertStudent.executeAndReturnKey(parameterSource);
            student.setId(newKey.intValue());
        } else {
            System.out.println("Update save 2");
            this.namedParameterJdbcTemplate.update(
                "UPDATE students SET first_name=:firstName, last_name=:lastName, major=:major, usual_mark=:UsualMark, prac_mark=:PracMark, final_mark=:FinalMark, major_mark=:MajorMark " +
                    "WHERE id=:id",
                parameterSource);
            System.out.println("Update save");
        }
    }



    @Override
    public Collection<Student> findAll() throws DataAccessException {
        List<Student> students = this.namedParameterJdbcTemplate.query(
            "SELECT id, first_name, last_name, major, usual_mark, prac_mark, final_mark, major_mark FROM students",
            new HashMap<String, Object>(),
            BeanPropertyRowMapper.newInstance(Student.class));
        return students;
    }

    @Override
    public void delete(Student student) throws DataAccessException {
        Map<String, Object> student_params = new HashMap<>();
        student_params.put("id", student.getId());

        this.namedParameterJdbcTemplate.update("DELETE FROM students WHERE id=:id", student_params);
    }


}
