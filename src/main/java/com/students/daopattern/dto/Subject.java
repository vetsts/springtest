
package com.students.daopattern.dto;



/**
 * Объектное представление сущности Предмет
 */
public class Subject  {

    private Integer id = null;
    
    private String subject;

    
    public Integer getId() {
        return id;
    }
    
    public void setId(int id) 
    { this.id = id;  }
    
    
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    
    public Subject (){
     
    }
        public Subject (Integer id){
        
        this.id = id;
        
    }
    public Subject (Integer id,  String subject){
        
        this.id = id;
        this.subject = subject;
    }
    public Subject (String subject){

        this.subject = subject;
    }
}