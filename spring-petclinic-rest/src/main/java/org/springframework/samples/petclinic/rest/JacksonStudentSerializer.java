package org.springframework.samples.petclinic.rest;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.springframework.samples.petclinic.model.Student;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonStudentSerializer extends StdSerializer<Student> {

    public JacksonStudentSerializer() {
        this(null);
    }

    public JacksonStudentSerializer(Class<Student> t) {
        super(t);
    }

    @Override
    public void serialize(Student student, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        jgen.writeStartObject();
        if (student.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeNumberField("id", student.getId());
        }

        jgen.writeStringField("firstName", student.getFirstName());
        jgen.writeStringField("lastName", student.getLastName());
        jgen.writeStringField("major", student.getMajor());
        jgen.writeNumberField("UsualMark", student.getUsualMark());
        jgen.writeNumberField("PracMark", student.getPracMark());
        jgen.writeNumberField("FinalMark", student.getFinalMark());
        jgen.writeNumberField("MajorMark", student.getMajorMark());
        jgen.writeEndObject(); // student
    }

}
