package org.springframework.samples.petclinic.rest;

import java.io.IOException;

import org.springframework.samples.petclinic.model.Student;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class JacksonStudentDeserializer extends StdDeserializer<Student> {
    public JacksonStudentDeserializer(){
        this(null);
    }

    public JacksonStudentDeserializer(Class<Student> t) {
        super(t);
    }

    @Override
    public Student deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        JsonNode node = parser.getCodec().readTree(parser);
        Student student = new Student();
        int id = node.get("id").asInt();
        String firstName = node.get("firstName").asText(null);
        String lastName = node.get("lastName").asText(null);
        String major = node.get("major").asText(null);
        double usualmark = node.get("UsualMark").asDouble();
        double pracmark = node.get("PracMark").asDouble();
        double finalmark = node.get("FinalMark").asDouble();
        double majormark = node.get("MajorMark").asDouble();

        if (!(id == 0)) {
            student.setId(id);
        }
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setMajor(major);
        student.setUsualMark(usualmark);
        student.setPracMark(pracmark);
        student.setFinalMark(finalmark);
        student.setMajorMark(majormark);
        return student;
    }

}
