
package com.students.daopattern.dto;


public class TestMark {

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

    
   private String surname = null;
   private String subject = null;
    
    
    private int mark = 0;
    private int studentId = 0;
    private int subjectId = 0;
  
    
    
    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
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
    
    public TestMark(){
    
    }
       public TestMark(int mark, int studentId, int subjectId){
     this.mark = mark;
     this.studentId = studentId;
     this.subjectId   = subjectId;  
    } 
}
