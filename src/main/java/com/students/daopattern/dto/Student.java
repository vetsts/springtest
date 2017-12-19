
package com.students.daopattern.dto;



import java.util.Date;

public class Student {

    private Integer id = null;
    private String name;
    private String surname;
    private Date enrolmentDate;

    public Integer getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
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
    
    public Date getEnrolmentDate() {
        return enrolmentDate;
    }
    
    public void setEnrolmentDate(Date enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
    }
    

    public Student(){
    }
    
    public Student(Integer id){
    this.id = id;
    
    }
    public Student(Integer id, String name, String surname){
    this.id = id;
    this.name = name;
    this.surname = surname;
    }
    
    public Student( String name, String surname){
    this.id = id;
    this.name = name;
    this.surname = surname;
    }
    
    public Student( String name, String surname, Date enrolmentDate ){
    
    this.name = name;
    this.surname = surname;
    this.enrolmentDate = enrolmentDate; 
    }
    public void print(){
    System.out.println(getId()+ " " + getName()+" " + getSurname());
    }
}
