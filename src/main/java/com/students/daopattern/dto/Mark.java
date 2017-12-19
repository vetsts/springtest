
package com.students.daopattern.dto;



/**
 * Объектное представление сущности Оценка
 */
public class Mark  {

    private Integer id = null;
    private int studentId;
    private int subjectId;
    private int mark;

   

    public Integer getId() {
        return id;
    }
    
    public void setId(int id) { this.id = id;  }
    
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

    public int getMark() {
        return mark;
    }
    
    public void setMark(int mark) {
        this.mark = mark;
    }

    public Mark (){
     
    }
    
    public Mark (Integer id){
      this.id = id;
      
    }
    
    public Mark (int studentId, int x){
     this.studentId = studentId;
    }
    
    public Mark (int studentId, int subjectId, int mark){
      this.studentId = studentId;
      this.subjectId = subjectId;
      this.mark = mark;
    }
    
    public Mark (Integer id, int studentId, int subjectId, int mark){
      this.id = id;
      this.studentId = studentId;
      this.subjectId = subjectId;
      this.mark = mark;
    }
     
}