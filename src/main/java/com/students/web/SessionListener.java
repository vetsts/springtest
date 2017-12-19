
package com.students.web;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.students.daopattern.dao.StudentDao;
import com.students.daopattern.mysql.MySqlStudentDao;


public class SessionListener implements HttpSessionListener {

@Override
public void sessionCreated(HttpSessionEvent arg){
 StudentDao stud = null;
        try{ 
        stud = new MySqlStudentDao();
        arg.getSession().setAttribute("connection", stud);
        }catch(Exception e){
            System.out.println("Не могу открыть соединение" + e.getMessage());
        }   
}
  @Override
  public void sessionDestroyed(HttpSessionEvent arg) {
      StudentDao stud = null;
try {
stud = (StudentDao) arg.getSession().getAttribute("connection");
if(stud != null){
stud.close();
}
}catch (Exception e) {
System.out.println("Ошибка в закрытии соединения" + e.getMessage());
}}
}