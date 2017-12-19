
package com.students.daopattern.dao;

import com.students.daopattern.dto.Mark;
import com.students.daopattern.dto.StudSubjMark;
import com.students.daopattern.dto.Student;
import com.students.daopattern.dto.Subject;
import java.util.List;
import com.students.daopattern.dto.TestMark;

public interface StudentDao {

    public  List<Student> getStudents () throws PersistException;
    public  void close()throws PersistException;
    public  void createMark (Mark object) throws PersistException;
    public  void createSubject (Subject object) throws PersistException;
    public  void createStudent (Student object) throws PersistException;
    public  void update(Mark object) throws PersistException;
    public  void delete(Mark object) throws PersistException;
    public  List<Subject> getAllSubject()throws PersistException;
    public  List<StudSubjMark> getStudSubjMark(Mark object) throws PersistException;
    public  List<StudSubjMark> getStudSubjMarkAll()throws PersistException;
    public  List<Student> getStudentsPage (int offset, int noOfRecords) throws PersistException;
    
    public  TestMark getTestMark(int markId)throws PersistException;
    public  TestMark getTestSubject(int subjectId)throws PersistException;
    public  TestMark getTestStudent(int studentId )throws PersistException;
    
    public  void updateStudent(int studentId, String name, String surName)throws PersistException;
    public  void updateSubject(int subjectId, String subject)throws PersistException;
    public  void deleteStudent(int studentId)throws PersistException;
    public  void deleteSubject(int subjectId)throws PersistException;
   
    
}
