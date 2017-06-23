package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.samples.petclinic.rest.JacksonStudentDeserializer;
import org.springframework.samples.petclinic.rest.JacksonStudentSerializer;

@Entity
@Table(name = "student")
@JsonSerialize(using = JacksonStudentSerializer.class)
@JsonDeserialize(using = JacksonStudentDeserializer.class)
public class Student extends Person {
    //    private String id;
    private String major;

    // Marks: MajorMark = UsualMark + PracMark + FinalMark;
    private double UsualMark;
    private double PracMark;
    private double FinalMark;

    private double MajorMark;

    //    public void setId(String id){this.id = id;}
    public void setMajor(String major){this.major = major;}
    public void setUsualMark(double UsualMark){this.UsualMark = UsualMark;}
    public void setPracMark(double PracMark){this.PracMark = PracMark;}
    public void setFinalMark(double FinalMark){this.FinalMark = FinalMark;}
    public void setMajorMark(double MajorMark){this.MajorMark = MajorMark;}

    //    public String getId() { return this.id; }
    public String getMajor() { return this.major; }
    public double getUsualMark() { return this.UsualMark; }
    public double getPracMark() { return this.PracMark; }
    public double getFinalMark() { return this.FinalMark; }
    public double getMajorMark() { return this.MajorMark; }

    public String getName(){return firstName + lastName;}
//    @Override
//    public String toString() {
//        return new ToStringCreator(this)
//
//            .append("id", this.getId())
//            .append("new", this.isNew())
//            .append("lastName", this.getLastName())
//            .append("firstName", this.getFirstName())
//            .append("major", this.getMajor())
//            .append("Usual Mark", this.getUsualMark())
//            .append("Prac Mark", this.getPracMark())
//            .append("Final Mark", this.getFinalMark())
//            .append("Major Mark", this.getMajorMark())
//            .toString();
//    }
}
