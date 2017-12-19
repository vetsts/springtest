
package com.students.daopattern.dto;


public class StudSubjMark {

    public int getId() {
        return id;
    }

    public void setId(int markId) {
        this.id = markId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

   

    private String name;
    private String surname;
    private String subject;
    private int mark;
    private int id;
    private int studentId;
    private int subjectId;
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
    public StudSubjMark() {
       
    }
public StudSubjMark(String name, String surname, String subject, int mark){
   this.name = name;
   this.surname = surname;
   this.subject = subject;
   this.mark = mark;

}
    
}
