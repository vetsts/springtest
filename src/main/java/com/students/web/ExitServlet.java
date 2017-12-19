package com.students.web;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ExitServlet extends HttpServlet  {
   
 
public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
 try {
            req.getSession().invalidate();
           getServletContext().getRequestDispatcher("/exit.jsp").forward(req, resp);
        
 }catch (Exception e) {
            System.out.println("Ошибка в закрытии соединения" + e.getMessage()); 
        }
}    
    }

