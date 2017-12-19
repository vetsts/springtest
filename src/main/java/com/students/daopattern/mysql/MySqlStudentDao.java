
package com.students.daopattern.mysql;

import com.students.daopattern.dao.PersistException;
import com.students.daopattern.dao.StudentDao;
import com.students.daopattern.dto.Mark;
import com.students.daopattern.dto.PageNav;
import com.students.daopattern.dto.StudSubjMark;
import com.students.daopattern.dto.Student;
import com.students.daopattern.dto.Subject;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import com.students.daopattern.dto.TestMark;


public class MySqlStudentDao implements StudentDao {

     private String db ;
   private Connection connection;
   private PreparedStatement select = null;
   private PreparedStatement selectSubj = null;
   private PreparedStatement selectSubjMark = null;
   private PreparedStatement selectPage = null;
   private PreparedStatement selectSubjMarkAll = null;
   private PreparedStatement create = null;
   private PreparedStatement createSubject = null;
   private PreparedStatement createStudent = null;
   private PreparedStatement update = null;
   private PreparedStatement delete = null;
   private PreparedStatement selectMarkId = null;
   private PreparedStatement selectStudentId = null;
   private PreparedStatement selectSubjectId = null;
   
   private PreparedStatement updateStudent = null;
     private PreparedStatement updateSubject = null;
       private PreparedStatement deleteStudent = null;
        private PreparedStatement deleteSubject = null;
    
        
    
private void loadProperties(Properties property) throws PersistException{
InputStream fis = null;

try {
fis = MySqlStudentDao.class.getResourceAsStream("/config.properties");
property.load(fis);
} catch (IOException ex) {
throw new PersistException("Файл свойств не найден" + ex.toString());
}finally{
if(fis!=null)
try{
fis.close();
}catch(Exception e){
throw new PersistException("Не могу закрыть соединение c файлом свойств" + e.toString());
}
}
}
private void loadTestProperties(Properties property) throws PersistException{
InputStream fis = null;
try {
fis = MySqlStudentDao.class.getResourceAsStream("/configtest.properties");
property.load(fis);
} catch (IOException ex) {
throw new PersistException("Файл свойств не найден" + ex.toString());
}finally{
if(fis!=null)
try{
fis.close();
}catch(Exception e){
throw new PersistException("Не могу закрыть соединение c файлом свойств" + e.toString());
}
}
}


public MySqlStudentDao() throws PersistException {
Properties property = new Properties();
db = "daotalk";
try {
loadProperties(property);
Class.forName(property.getProperty("driver"));
connection = DriverManager.getConnection(property.getProperty("url"), property.getProperty("user"), property.getProperty("password"));
} catch (ClassNotFoundException e) {
e.printStackTrace();
throw new PersistException("Не могу зарегестрировать драйвер",e);
}
catch (SQLException e) {
throw new PersistException("Не удается подключиться к базе данных" + e.toString());
}
}


public MySqlStudentDao(String test) throws PersistException {
Properties property = new Properties();
db = "testdaotalk";
try {
loadTestProperties(property);
Class.forName(property.getProperty("driver"));
connection = DriverManager.getConnection(property.getProperty("url"), property.getProperty("user"), property.getProperty("password"));
} catch (ClassNotFoundException e) {
e.printStackTrace();
throw new PersistException("Не могу зарегестрировать драйвер",e);
}
catch (SQLException e) {
throw new PersistException("Не удается подключиться к базе данных" + e.toString());
}

}
     
private String getMarkIdSelectQuery() {
    return "SELECT mark FROM "+db+".mark WHERE id = ?;";
}

private String getStudentIdSelectQuery() {
    return "SELECT surname FROM "+db+".student WHERE id = ?;";
}
private String getSubjectIdSelectQuery() {
    return "SELECT subject FROM "+db+".subject WHERE id = ?;";
}


private String getSelectQuery() {
    return "SELECT id, name, surname FROM "+db+".student";
}
private String getCreateQuery() {
        return "INSERT INTO "+db+".mark (student_id, subject_id, mark) \n" +
                "VALUES (?, ?, ?);";
    }  

private String getCreateQuerySubject(){
return "INSERT INTO "+db+".subject (subject) VALUES (? );";
}

private String getCreateQueryStudent(){
return "INSERT INTO "+db+".student (name, surname) VALUES (?, ?);";
}
private String getUpdateQuery() {
        return "UPDATE "+db+".mark SET student_id = ?, subject_id = ?, mark = ? WHERE id = ?;";
    }
private String getUpdateStudentQuery(){
return"UPDATE "+db+".student SET name = ?, surname = ? WHERE id = ?;";
}
private String getUpdateSubjectQuery(){
    return"UPDATE "+db+".subject SET subject = ? WHERE id = ?;";
}

private String getDeleteQuery() {
        return "DELETE FROM "+db+".mark WHERE id = ?;";
    }

private String getDeleteStudentQuery(){
return"DELETE FROM "+db+".student WHERE id = ?;";
}

private String getDleteSubjectQuery(){
return"DELETE FROM "+db+".subject WHERE id = ?;";
}

private String getSelectQuerySubj() {
      return "SELECT id, subject FROM "+db+".subject;";
}

private String getSelectQuerySubjMark() {
      return "SELECT M.id, M.student_id, M.subject_id, S.name, S.surname, SB.subject, M.mark  FROM "+db+".mark M \n" +
"INNER JOIN "+db+".student S  ON S.ID = M.student_id\n" +
"INNER JOIN "+db+".subject SB ON SB.ID = M.subject_id \n" +
"WHERE student_id = ?;";
}

private String getSelectQuerySubjMarkAll(){
return "SELECT M.id, S.name, S.surname, SB.subject, M.mark FROM "+db+".mark M \n" +
"INNER JOIN "+db+".student S  ON S.ID = M.student_id\n" +
"INNER JOIN "+db+".subject SB ON SB.ID = M.subject_id\n" +
"ORDER BY S.surname;";
}
private String getSelectQueryPage() {
       
    return "SELECT SQL_CALC_FOUND_ROWS id, name, surname FROM "+db+".student limit ?, ?;";
}

private void initUpdateStudent() throws SQLException {
if (updateStudent == null) {
    updateStudent = connection.prepareStatement(getUpdateStudentQuery());
 }
}
private void initUpdateSubject() throws SQLException {
if (updateSubject == null) {
    updateSubject = connection.prepareStatement(getUpdateSubjectQuery());
 }
}
private void initDeleteStudent() throws SQLException {
if (deleteStudent == null) {
    deleteStudent = connection.prepareStatement(getDeleteStudentQuery());
 }
}

private void initDeleteSubject() throws SQLException {
if (deleteSubject == null) {
    deleteSubject = connection.prepareStatement(getDleteSubjectQuery());
 }
}


private void initSelectStudent() throws SQLException {
if (selectStudentId == null) {
    selectStudentId = connection.prepareStatement(getStudentIdSelectQuery());
 }
}

private void initSelectSubject() throws SQLException {
if (selectSubjectId == null) {
    selectSubjectId = connection.prepareStatement(getSubjectIdSelectQuery());
 }
}

private void initSelectIdMark() throws SQLException {
if (selectMarkId == null) {
    selectMarkId = connection.prepareStatement(getMarkIdSelectQuery());
 }
}

private void initSelectPage() throws SQLException {
if (selectPage == null) {
    selectPage = connection.prepareStatement(getSelectQueryPage());
 }
}

private void initSelect() throws SQLException {
if (select == null) {
    select = connection.prepareStatement(getSelectQuery());
 }
}
private void initSelectSubj() throws SQLException  {
if (selectSubj == null) {
    selectSubj = connection.prepareStatement(getSelectQuerySubj());
 }
}
private void initSelectSubjMark() throws SQLException  {
if (selectSubjMark == null) {
    selectSubjMark = connection.prepareStatement(getSelectQuerySubjMark());
 }
}
private void initSelectSubjMarkAll() throws SQLException  {
if (selectSubjMarkAll == null) {
    selectSubjMarkAll = connection.prepareStatement(getSelectQuerySubjMarkAll());
 }
}
private void initCreate() throws SQLException {
if (create == null) {
    create = connection.prepareStatement(getCreateQuery());
 }
}
private void initCreateSubject() throws SQLException {
if (createSubject == null) {
    createSubject = connection.prepareStatement(getCreateQuerySubject());
 }
}

private void initCreateStudent() throws SQLException {
if (createStudent == null) {
    createStudent = connection.prepareStatement(getCreateQueryStudent());
 }
}

private void initUpdate() throws SQLException {
if (update == null) {
    update = connection.prepareStatement(getUpdateQuery());
 }
}
private void initDelete() throws SQLException {
if (delete == null) {
    delete = connection.prepareStatement(getDeleteQuery());
 }
}

private void prepareStatementForUpdateStudent(PreparedStatement statement, int studentId, String name, String surName) throws PersistException {
       
        try {
            statement.setString(1, name);
            statement.setString(2, surName);
            statement.setInt(3, studentId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException("Не могу установить аргументы updateStudent запроса", e);
        }
    }

private void prepareStatementForUpdateSubject(PreparedStatement statement, int subjectId, String subject) throws PersistException {
       
        try {
            statement.setString(1, subject);
            statement.setInt(2, subjectId);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException("Не могу установить аргументы updateSubject запроса", e);
        }
    }
private void prepareStatementForDeleteStudent(PreparedStatement statement, int studentId) throws PersistException {
       
        try {
            statement.setInt(1, studentId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException("Не могу установить аргументы deleteStudent запроса", e);
        }
    }

private void prepareStatementForDeleteSubject(PreparedStatement statement, int subjectId) throws PersistException {
       
        try {
            statement.setInt(1, subjectId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException("Не могу установить аргументы deleteSubject запроса", e);
        }
    }

private void prepareStatementForSelectStudent(PreparedStatement statement, int studentId) throws PersistException {
       
        try {
            statement.setInt(1, studentId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException("Не могу установить аргументы selectStudent запроса", e);
        }
    }

private void prepareStatementForSelectSubject(PreparedStatement statement, int subjectId) throws PersistException {
       
        try {
            statement.setInt(1, subjectId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException("Не могу установить аргументы selectSubject запроса", e);
        }
    }

private void prepareStatementForSelectMarkId(PreparedStatement statement, int markId) throws PersistException {
       
        try {
            statement.setInt(1, markId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException("Не могу установить аргументы selectMark запроса", e);
        }
    }


private void prepareStatementForCreateStudentPage(PreparedStatement statement, PageNav object) throws PersistException {
       
        try {
            statement.setInt(1, object.getOffset());
            statement.setInt(2, object.getNoOfRecords());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException("Не могу установить аргументы CreateStudentPage запроса", e);
        }
    }
private void prepareStatementForCreate(PreparedStatement statement, Mark object) throws PersistException {
       
        try {
            
            statement.setInt(1, object.getStudentId());
            statement.setInt(2, object.getSubjectId());
            statement.setInt(3, object.getMark());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException("Не могу установить аргументы create запроса", e);
        }
    }

private void prepareStatementForCreateSubject(PreparedStatement statement, Subject object) throws PersistException {
       
        try {
            statement.setString(1, object.getSubject());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException("Не могу установить аргументы createSubject запроса", e);
        }
    }
private void prepareStatementForCreateStudent(PreparedStatement statement, Student object) throws PersistException {
       
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistException("Не могу установить аргументы createSubject запроса", e);
        }
    }

private void prepareStatementForSelect(PreparedStatement statement, Mark object) throws PersistException {
        
        try {
           statement.setInt(1, object.getStudentId());  
        } catch (Exception e) {
            
            throw new PersistException("Не могу установить аргументы select запроса", e);
        } 
    }

private void prepareStatementForUpdate(PreparedStatement statement, Mark object) throws PersistException {
       
        try {  
           statement.setInt(1, object.getStudentId());
           statement.setInt(2, object.getSubjectId());
           statement.setInt(3, object.getMark());
           statement.setInt(4, object.getId()); 
        } catch (Exception e) {
         e.printStackTrace();
            throw new PersistException("Не могу установить аргументы update запроса", e);
        }
    }

private void prepareStatementForDelete(PreparedStatement statement, Mark object) throws PersistException {    
        try {  
         statement.setObject(1, object.getId()); 
        } catch (Exception e) {
         e.printStackTrace();
            throw new PersistException("Не могу установить параметры delete запроса", e);
        }
    }
   @Override
   public TestMark getTestMark(int markId) throws PersistException{
    ResultSet rs = null;
    TestMark tm = new TestMark();

    try{ 
    initSelectIdMark();
    prepareStatementForSelectMarkId(selectMarkId,  markId);
    rs = selectMarkId.executeQuery();
    if(rs.next()){
     tm.setMark(rs.getInt("mark"));
    }
    }catch(Exception e){
    e.printStackTrace();
    throw new PersistException("Не могу получить оценку  студента, в методе getTestMark ",e);
    }finally{
try {
     if(rs != null){
        rs.close();}
        }catch(SQLException e){    
        throw new PersistException("Не могу закрыть соединение ResultSet в методе getCheckMark", e);
        }
        }
    if(tm.getMark()!= 0){
    return tm;
    }else{
        return null;
    }
}

   @Override
   public TestMark getTestStudent(int studentid) throws PersistException{
    ResultSet rs = null;
    TestMark tm = new TestMark();
    try{
    initSelectStudent();
    prepareStatementForSelectStudent(selectStudentId, studentid);
    rs = selectStudentId.executeQuery();
    if(rs.next()){
     tm.setSurname(rs.getString("surname"));
    }
    
    }catch(Exception e){
        e.printStackTrace();
    throw new PersistException("Не могу получить студента в методе getTestStudent",e);
    }finally{
try {
     if(rs != null){
        rs.close();}
        }catch(SQLException e){    
        throw new PersistException("Не могу закрыть соединение ResultSet в методе getTestStudent", e);
        }
        }
if(tm.getSurname()!= null){
    return tm;
    }else{
        return null;
    }
}

   @Override
   public TestMark getTestSubject(int subjectId) throws PersistException{
   ResultSet rs = null;
   TestMark tm = new TestMark();
    try{
    initSelectSubject();
    prepareStatementForSelectSubject(selectSubjectId, subjectId);
    rs = selectSubjectId.executeQuery();
    if(rs.next()){
     tm.setSubject(rs.getString("subject"));
    }

    }catch(Exception e){
    throw new PersistException("Не могу получить предмет ",e);
    }finally{
try {
     if(rs != null){
        rs.close();}
        }catch(SQLException e){    
        throw new PersistException("Не могу закрыть соединение ResultSet в методе getSubject", e);
        }
        }
if(tm.getSubject()!= null){
    return tm;
    }else{
        return null;
    }
}

public void createMark (Mark object) throws PersistException {
       
        try {
            initCreate();
            prepareStatementForCreate(create, object);
            create.executeUpdate(); 
            
        } catch (Exception e) {  
            throw new PersistException("Не могу добавить запись", e);
        }
    }

public void createSubject (Subject object) throws PersistException {
       
        try {
            initCreateSubject();
            prepareStatementForCreateSubject(createSubject, object);
            createSubject.executeUpdate(); 
        } catch (Exception e) {  
            throw new PersistException("Не могу добавить запись", e);
        }
    }

public void createStudent (Student object) throws PersistException {
       
        try {
            initCreateStudent();
            prepareStatementForCreateStudent(createStudent, object);
            createStudent.executeUpdate(); 
        } catch (Exception e) {  
            throw new PersistException("Не могу добавить запись", e);
        }
    }

public void update(Mark object) throws PersistException {
        
        try {
            initUpdate();
            prepareStatementForUpdate(update, object); 
            int count = update.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
             
            throw new PersistException("Не могу обновить запись", e);
        }
    }
public void updateStudent(int studentId, String name, String surName) throws PersistException {
        
        try {
            initUpdateStudent();
            prepareStatementForUpdateStudent(updateStudent, studentId, name, surName); 
            int count = updateStudent.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
             
            throw new PersistException("Не могу обновить запись в методе updateStudent", e);
        }
    }
public void updateSubject(int subjectId, String subject) throws PersistException {
        
        try {
            initUpdateSubject();
            prepareStatementForUpdateSubject(updateSubject, subjectId, subject); 
            int count = updateSubject.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
             
            throw new PersistException("Не могу обновить запись в методе updateSubject", e);
        }
    }
public void delete(Mark object) throws PersistException {
        
        try {
            initDelete();
            prepareStatementForDelete(delete,object);
            int count = delete.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
            
        } catch (Exception e) {
           e.printStackTrace();
            throw new PersistException("Не могу удалить запись в базе данных", e);
        }
    }
public void deleteStudent(int studentId) throws PersistException {
        
        try {
            initDeleteStudent();
            prepareStatementForDeleteStudent(deleteStudent,studentId);
            int count = deleteStudent.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
            
        } catch (Exception e) {
           e.printStackTrace();
            throw new PersistException("Не могу удалить запись в методе deleteStudent", e);
        }
    }
public void deleteSubject(int subjectId) throws PersistException {
        
        try {
            initDeleteSubject();
            prepareStatementForDeleteSubject(deleteSubject,subjectId);
            int count = deleteSubject.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            } 
        } catch (Exception e) {
           e.printStackTrace();
            throw new PersistException("Не могу удалить запись в методе deleteSubject", e);
        }
    }

public List<Student> getStudents()throws PersistException {

List<Student> result = new LinkedList<>();
ResultSet rs = null;
try {
initSelect();
rs = select.executeQuery();
while (rs.next()) { 
Student object = new Student();
object.setId(rs.getInt("id"));
object.setName(rs.getString("name"));
object.setSurname(rs.getString("surname"));
result.add(object);
}
} catch (Exception e) {
  
throw new PersistException("Не могу подготовть к печати студентов",e);
} finally{
try {
     if(rs != null){
        rs.close();}
        }catch(SQLException e){    
        throw new PersistException("Не могу закрыть соединение ResultSet", e);
        }
        }
return result;
}
public List<Subject> getAllSubject() throws PersistException {
        ResultSet rs = null;
        List<Subject> result = new LinkedList();
        try  {
            initSelectSubj();
            rs = selectSubj.executeQuery();
            while(rs.next()){
            Subject object = new Subject();
            object.setId(rs.getInt("id"));
            object.setSubject(rs.getString("subject"));
            result.add(object);
            }
        } catch (Exception e) {  
            e.printStackTrace();
          throw new PersistException("Не могу получить список предметов", e);
        }finally{
        try {
            if(rs != null){
               rs.close();}
        }catch(SQLException e){  
       throw new PersistException("Не могу закрыть соединение ResultSet", e);
        }
        } 
        return result;
    }

public List<StudSubjMark> getStudSubjMark(Mark object) throws PersistException {
        ResultSet rs = null;
        List<StudSubjMark> result = new LinkedList();
        
        try  {
            initSelectSubjMark();
            prepareStatementForSelect(selectSubjMark, object);
            rs = selectSubjMark.executeQuery();
            while(rs.next()){
            StudSubjMark st = new StudSubjMark();
            
            st.setId(rs.getInt("id"));
            st.setStudentId(rs.getInt("student_id"));
            st.setSubjectId(rs.getInt("subject_id"));
            st.setName(rs.getString("name"));
            st.setSurname(rs.getString("surname"));
            st.setSubject(rs.getString("subject"));
            st.setMark(rs.getInt("mark"));
            result.add(st);
            }
        } catch (Exception e) { 
            e.printStackTrace();
            throw new PersistException("Не могу получить запись: студент предмет, оценка с сортировкой по фамилии", e);
        } finally{
        try {
            
            if(rs != null){
               
                rs.close();}
        }catch(SQLException e){
        throw new PersistException("Не могу закрыть соединение ResultSet", e);
        }
        } 
        return result;
    }
public List<StudSubjMark> getStudSubjMarkAll()throws PersistException {

List<StudSubjMark> result = new LinkedList<>();
ResultSet rs = null;
try {
initSelectSubjMarkAll();
rs = selectSubjMarkAll.executeQuery();
while (rs.next()) { 
StudSubjMark object = new StudSubjMark();
object.setId(rs.getInt("id"));
object.setName(rs.getString("name"));
object.setSurname(rs.getString("surname"));
object.setSubject(rs.getString("subject"));
object.setMark(rs.getInt("mark"));
result.add(object);
}
} catch (Exception e) {
  
throw new PersistException("Не могу подготовть к печати студентов",e);
} finally{
try {
     if(rs != null){
        rs.close();}
        }catch(SQLException e){    
        throw new PersistException("Не могу закрыть соединение ResultSet", e);
        }
        }
return result;
}


public List<Student> getStudentsPage(int offset, int noOfRecords)throws PersistException {
    
   PageNav pg = new PageNav();
   
pg.setOffset(offset);
pg.setNoOfRecords(noOfRecords);
List<Student> result = new LinkedList<>();
ResultSet rs = null;
try {
initSelectPage();
prepareStatementForCreateStudentPage(selectPage, pg);
rs = selectPage.executeQuery();
while (rs.next()) { 
Student object = new Student();
object.setId(rs.getInt("id"));
object.setName(rs.getString("name"));
object.setSurname(rs.getString("surname"));
result.add(object);
}
} catch (Exception e) {
  e.printStackTrace(); 
throw new PersistException("Не могу подготовть к печати студентов",e);
} finally{
try {
     if(rs != null){
        rs.close();}
        }catch(SQLException e){    
        throw new PersistException("Не могу закрыть соединение ResultSet", e);
        }
        }
return result;
}

public void close()throws PersistException {
Throwable err = null;
if(selectMarkId != null){
try{
selectMarkId.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(selectStudentId != null){
try{
selectStudentId.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(selectSubjectId != null){
try{
selectSubjectId.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}

if(select != null){
try{
select.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(selectSubj != null){
try{
selectSubj.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(selectSubjMark != null){
try{
selectSubjMark.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(create != null){
try{
create.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(update != null){
try{
update.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(delete != null){
try{
delete.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(connection != null){
try{
connection.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(selectPage != null){
try{
selectPage.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(selectSubjMarkAll != null){
try{
selectSubjMarkAll.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(createSubject != null){
try{
createSubject.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(createStudent != null){
try{
createStudent.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(updateStudent != null){
try{
updateStudent.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(updateSubject != null){
try{
updateSubject.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(deleteStudent != null){
try{
deleteStudent.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if(deleteSubject != null){
try{
deleteSubject.close();
}catch(Exception e){
if(err == null)
   err = e; 
}}
if (err != null) {
throw new PersistException("Ошибка!Соединение не закрыто ", err);
}
}

}