package com.students.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.students.daopattern.dao.PersistException;
import com.students.daopattern.dao.StudentDao;
import com.students.daopattern.dto.Mark;
import com.students.daopattern.dto.PageNav;
import com.students.daopattern.dto.StudSubjMark;
import com.students.daopattern.dto.Student;
import com.students.daopattern.dto.Subject;
import com.students.daopattern.dto.TestMark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GeneralServlet extends HttpServlet {

    @Autowired
    private StudentDao stud = null;

    public StudentDao getDao() {
        return stud;
    }

    private StudentDao getStudentDao(HttpServletRequest req) throws PersistException {
        stud = (StudentDao) req.getSession().getAttribute("connection");
        if (stud == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
            GeneralServlet con = (GeneralServlet) context.getBean("daocon");
            stud = con.getDao();
        }
        return stud;
    }

    private void checkMarkStudentSubject(int markId, int studentId, int subjectId, HttpServletRequest req) throws PersistException {
        if (markId != 0) {
            if (getStudentDao(req).getTestMark(markId) == null) {
                String markDeleted = "Нет такой оценки !";
                req.setAttribute("markDeleted", markDeleted);
            }
        }
        if (studentId != 0) {
            if (getStudentDao(req).getTestStudent(studentId) == null) {
                String studentDeleted = "Нет такого студента !";
                req.setAttribute("studentDeleted", studentDeleted);
            }
        }
        if (subjectId != 0) {
            if (getStudentDao(req).getTestSubject(subjectId) == null) {
                String subjectDeleted = "Нет такого предмета !";
                req.setAttribute("subjectDeleted", subjectDeleted);
            }
        }
    }

    private void studenSubjetList(HttpServletRequest req) throws PersistException {
        List<Student> std = new LinkedList();
        StudentDao dao = getStudentDao(req);
        std = dao.getStudents();
        req.setAttribute("studentList", std);
        List<Subject> subj = new LinkedList();
        subj = dao.getAllSubject();
        req.setAttribute("subjectList", subj);
        List<StudSubjMark> stdm = new LinkedList();
        stdm = dao.getStudSubjMarkAll();
        req.setAttribute("markUpdateList", stdm);
    }

    private void studentMarks(HttpServletRequest req, HttpServletResponse resp) throws PersistException, ServletException, IOException {
        PageNav p = new PageNav();
        StudentDao dao = getStudentDao(req);
        List<Student> lists = dao.getStudents();
        int allStudent = lists.size();
        int allPages = (int) Math.ceil((float) allStudent / p.getRecordsPerPage());
        req.setAttribute("allPages", allPages);
        if (req.getParameter("page") != null) {
            try {
                p.setPage(Integer.parseInt(req.getParameter("page")));
            } catch (Exception e) {
                System.out.println("Не могу сконвертировать параметр page в методе studentMarks " + e.getMessage());
            }
        }
        List<Student> list = dao.getStudentsPage((p.getPage() - 1) * p.getRecordsPerPage(), p.getRecordsPerPage());
        int noOfRecords = p.getNoOfRecords();
        int noOfPages = (int) Math.ceil((float) noOfRecords * (p.getPage() - 1) / p.getRecordsPerPage());
        req.setAttribute("studentList", list);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", p.getPage());
        RequestDispatcher view = req.getRequestDispatcher("marks.jsp");
        view.forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws PersistException, ServletException, IOException {

        String markId = (String) req.getParameter("markId");
        String studentId = (String) req.getParameter("studentId");
        req.setAttribute("studentId", studentId);
        if (markId != null) {
            String someValue = (String) req.getParameter("value");
            try {
                int intMarkId = Integer.parseInt(markId);
                int intSomeValue = 0;
                if (someValue != null) {
                    intSomeValue = Integer.parseInt(someValue);
                }
                TestMark testMark = getStudentDao(req).getTestMark(intMarkId);
                if (testMark != null) {
                    String message = "Вы действительно хотите удалить эту оценку ?";
                    req.setAttribute("message", message);

                } else {
                    checkMarkStudentSubject(intMarkId, 0, 0, req);
                }
                req.setAttribute("markId", markId);
                if (testMark != null && intMarkId != 0 && intSomeValue == 1) {
                    getStudentDao(req).delete(new Mark(intMarkId));
                    req.setAttribute("studentIdRedirect", studentId);
                }
            } catch (Exception e) {
                System.out.println("Не могу сконвертирвать переменную в методе delete " + e.getMessage());
            }
        }
        getServletContext().getRequestDispatcher("/delete.jsp").forward(req, resp);
    }

    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws PersistException, ServletException, IOException {

        String studentId = (String) req.getParameter("studentId");
        req.setAttribute("studentId", studentId);
        if (studentId != null) {
            String someValue = (String) req.getParameter("value");
            try {
                int intStudentId = Integer.parseInt(studentId);
                int intSomeValue = 0;
                if (someValue != null) {
                    intSomeValue = Integer.parseInt(someValue);
                }
                TestMark testStudent = getStudentDao(req).getTestStudent(intStudentId);
                if (testStudent != null) {
                    String message = "Вы действительно хотите удалить студента ?";
                    req.setAttribute("message", message);
                } else {
                    checkMarkStudentSubject(0, intStudentId, 0, req);
                }
                if (testStudent != null && intStudentId != 0 && intSomeValue == 1) {
                    getStudentDao(req).deleteStudent(intStudentId);
                    req.setAttribute("studentIdRedirect", studentId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Не могу сконвертирвать переменную в методе deleteStudent " + e.getMessage());
            }
        }
        getServletContext().getRequestDispatcher("/delete-student.jsp").forward(req, resp);
    }

    private void deleteSubject(HttpServletRequest req, HttpServletResponse resp) throws PersistException, ServletException, IOException {

        String subjectId = (String) req.getParameter("subjectId");
        req.setAttribute("subjectId", subjectId);
        if (subjectId != null) {
            String someValue = (String) req.getParameter("value");
            try {
                int intSubjectId = Integer.parseInt(subjectId);
                int intSomeValue = 0;
                if (someValue != null) {
                    intSomeValue = Integer.parseInt(someValue);
                }
                TestMark testSubject = getStudentDao(req).getTestSubject(intSubjectId);
                if (testSubject != null) {
                    String message = "Вы действительно хотите удалить предмет ?";
                    req.setAttribute("message", message);
                } else {
                    checkMarkStudentSubject(0, 0, intSubjectId, req);
                }
                if (testSubject != null && intSubjectId != 0 && intSomeValue == 1) {
                    getStudentDao(req).deleteSubject(intSubjectId);
                    req.setAttribute("studentIdRedirect", subjectId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Не могу сконвертирвать переменную в методе deleteSubject " + e.getMessage());
            }
        }
        getServletContext().getRequestDispatcher("/delete-subject.jsp").forward(req, resp);
    }

    private void subjectAdd(HttpServletRequest req, HttpServletResponse resp) throws PersistException, ServletException, IOException {

        String subjectName = null;
        subjectName = (String) req.getParameter("subjectName");
        String value = null;
        req.setAttribute("value", value);
        if (subjectName != null && !"".equals(subjectName)) {
            getStudentDao(req).createSubject(new Subject(subjectName));
            value = "not null";
            req.setAttribute("value", value);
        } else {
            if ("".equals(subjectName)) {
                String subjectnm = "Вы не ввели название предмета!";
                req.setAttribute("subjectnm", subjectnm);
            }
        }
        getServletContext().getRequestDispatcher("/subjectadd.jsp").forward(req, resp);
    }

    private void studentAdd(HttpServletRequest req, HttpServletResponse resp) throws PersistException, ServletException, IOException {

        String studentName = null;
        String studentSurName = null;
        studentName = (String) req.getParameter("studentName");
        studentSurName = (String) req.getParameter("studentSurName");
        String value = null;
        req.setAttribute("value", value);
        if ((!"".equals(studentName) && !"".equals(studentSurName)) && (studentName != null && studentSurName != null)) {
            getStudentDao(req).createStudent(new Student(studentName, studentSurName));
            value = "not null";
            req.setAttribute("value", value);
        } else {
            if ("".equals(studentName)) {
                req.setAttribute("surName", studentSurName);
                String namest = "Вы не ввели имя!";
                req.setAttribute("namest", namest);
            }
            if ("".equals(studentSurName)) {
                req.setAttribute("name", studentName);
                String surnamest = "Вы не ввели фамилию!";
                req.setAttribute("surnamest", surnamest);
            }
        }
        getServletContext().getRequestDispatcher("/studentadd.jsp").forward(req, resp);
    }

    private void markAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, PersistException, IOException {
        String studentIdd = req.getParameter("studentId");
        String subjectIdd = req.getParameter("subjectId");
        String markd = req.getParameter("mark");

        try {
            if (studentIdd != null && subjectIdd != null && markd != null) {
                int studentId = Integer.parseInt(studentIdd);
                int subjectId = Integer.parseInt(subjectIdd);
                int mark = Integer.parseInt(markd);
                TestMark testStudent = getStudentDao(req).getTestStudent(studentId);
                TestMark testSubject = getStudentDao(req).getTestSubject(subjectId);
                if (studentId != 0 && subjectId != 0 && mark != 0) {
                    checkMarkStudentSubject(0, studentId, subjectId, req);
                }
                if (studentId != 0 && subjectId != 0 && mark != 0 && testStudent != null && testSubject != null) {
                    getStudentDao(req).createMark(new Mark(studentId, subjectId, mark));
                } else {
                    req.setAttribute("selectMark", markd);
                    req.setAttribute("selectSubject", subjectIdd);
                    req.setAttribute("selectStudent", studentIdd);
                    if (studentId == 0) {
                        String stId = "Вы не выбрали студента!";
                        req.setAttribute("stId", stId);
                    }
                    if (subjectId == 0) {
                        String sbId = "Вы не выбрали предмет!";
                        req.setAttribute("sbId", sbId);
                    }
                    if (mark == 0) {
                        String studMark = "Вы не выбрали оценку!";
                        req.setAttribute("studMark", studMark);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Не могу сконвертирвать переменные в методе markAdd  " + e.getMessage());
        }

        getServletContext().getRequestDispatcher("/markadd.jsp").forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, PersistException, IOException {
        String markIdd = (String) req.getParameter("markId");
        req.setAttribute("markId", markIdd);

        String studentIdd = (String) req.getParameter("studentId");
        String subjectIdd = (String) req.getParameter("subjectId");
        String markd = (String) req.getParameter("mark");
        String value = null;
        req.setAttribute("value", value);

        try {

            int markId = Integer.parseInt(markIdd);
            if ((studentIdd == null && subjectIdd == null && markd == null) && (markIdd != null)) {

                checkMarkStudentSubject(markId, 0, 0, req);
            }
            if (studentIdd != null && subjectIdd != null && markd != null) {
                int studentId = Integer.parseInt(studentIdd);
                int subjectId = Integer.parseInt(subjectIdd);
                int mark = Integer.parseInt(markd);
                req.setAttribute("studentId", studentId);
                req.setAttribute("subjectId", subjectId);
                req.setAttribute("mark", mark);
                req.setAttribute("markId", markId);
                TestMark testMark = getStudentDao(req).getTestMark(markId);
                TestMark testStudent = getStudentDao(req).getTestStudent(studentId);
                TestMark testSubject = getStudentDao(req).getTestSubject(subjectId);

                if ((studentId != 0 && subjectId != 0 && mark != 0) || markId != 0) {
                    checkMarkStudentSubject(markId, studentId, subjectId, req);
                }

                if (studentId != 0 && subjectId != 0 && mark != 0 && testMark != null && testStudent != null && testSubject != null) {
                    getStudentDao(req).update(new Mark(markId, studentId, subjectId, mark));
                    value = "not null";
                    req.setAttribute("value", value);
                } else {
                    req.setAttribute("selectMark", markd);
                    req.setAttribute("selectSubject", subjectIdd);
                    req.setAttribute("selectStudent", studentIdd);
                    if (studentId == 0) {
                        String stId = "Вы не выбрали студента!";
                        req.setAttribute("stId", stId);
                    }
                    if (subjectId == 0) {
                        String sbId = "Вы не выбрали предмет!";
                        req.setAttribute("sbId", sbId);
                    }
                    if (mark == 0) {
                        String studMark = "Вы не выбрали оценку!";
                        req.setAttribute("studMark", studMark);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Не могу сконвертирвать переменные в методе edit " + e.getMessage());
        }

        getServletContext().getRequestDispatcher("/edit.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, PersistException, IOException, SQLException {

        String studentId = (String) req.getParameter("studentId");
        int studentIdMark;
        if (studentId != null) {
            try {
                studentIdMark = Integer.parseInt(studentId);
                checkMarkStudentSubject(0, studentIdMark, 0, req);
                List<StudSubjMark> listm = getStudentDao(req).getStudSubjMark(new Mark(studentIdMark, 1));
                req.setAttribute("studentMarks", listm);
                req.setAttribute("studentId", studentId);
            } catch (Exception e) {
                System.out.println("Не могу сконвертирвать переменные в методе update  " + e.getMessage());
            }
        }
        getServletContext().getRequestDispatcher("/update.jsp").forward(req, resp);
    }

    private void editStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, PersistException, IOException, SQLException {
        String studentId = (String) req.getParameter("studentId");
        req.setAttribute("studentId", studentId);
        try {
            int studentIdMark = 0;
            if (studentId != null) {
                studentIdMark = Integer.parseInt(studentId);
            }
            TestMark testStudent = getStudentDao(req).getTestStudent(studentIdMark);
            checkMarkStudentSubject(0, studentIdMark, 0, req);

            String studentName = null;
            String studentSurName = null;
            studentName = (String) req.getParameter("studentName");
            studentSurName = (String) req.getParameter("studentSurName");
            String value = null;
            req.setAttribute("value", value);

            if ((!"".equals(studentName) && !"".equals(studentSurName)) && (studentName != null && studentSurName != null) && testStudent != null) {
                getStudentDao(req).updateStudent(studentIdMark, studentName, studentSurName);
                value = "not null";
                req.setAttribute("value", value);
            } else {
                if ("".equals(studentName)) {
                    req.setAttribute("surName", studentSurName);
                    String namest = "Вы не ввели имя!";
                    req.setAttribute("namest", namest);
                }
                if ("".equals(studentSurName)) {
                    req.setAttribute("name", studentName);
                    String surnamest = "Вы не ввели фамилию!";
                    req.setAttribute("surnamest", surnamest);
                }
            }
        } catch (Exception e) {

            System.out.println("Не могу сконвертирвать переменные в методе editStudent" + e.getMessage());
        }
        getServletContext().getRequestDispatcher("/edit-student.jsp").forward(req, resp);
    }

    private void editSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, PersistException, IOException, SQLException {
        String subjectId = (String) req.getParameter("subjectId");
        req.setAttribute("subjectId", subjectId);
        try {
            int intSubjectId = 0;
            if (subjectId != null) {
                intSubjectId = Integer.parseInt(subjectId);
            }
            TestMark testSubject = getStudentDao(req).getTestSubject(intSubjectId);
            checkMarkStudentSubject(0, 0, intSubjectId, req);
            String subjectName = null;
            subjectName = (String) req.getParameter("subjectName");
            String value = null;
            req.setAttribute("value", value);
            if (!"".equals(subjectName) && subjectName != null && testSubject != null) {
                getStudentDao(req).updateSubject(intSubjectId, subjectName);
                value = "not null";
                req.setAttribute("value", value);
            } else {
                if ("".equals(subjectName)) {
                    String subjectnm = "Вы не ввели название предмета!";
                    req.setAttribute("subjectnm", subjectnm);
                }
            }
        } catch (Exception e) {

            System.out.println("Не могу сконвертирвать переменные в методе editSubject" + e.getMessage());
        }
        getServletContext().getRequestDispatcher("/edit-subject.jsp").forward(req, resp);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            getStudentDao(req);
            studenSubjetList(req);
            String url = req.getServletPath();
            if ("/delete".equals(url)) {
                delete(req, resp);
            } else if ("/delete-student".equals(url)) {
                deleteStudent(req, resp);
            } else if ("/delete-subject".equals(url)) {
                deleteSubject(req, resp);
            } else if ("/edit".equals(url)) {
                edit(req, resp);
            } else if ("/edit-student".equals(url)) {
                editStudent(req, resp);
            } else if ("/edit-subject".equals(url)) {
                editSubject(req, resp);
            } else if ("/markadd".equals(url)) {
                markAdd(req, resp);
            } else if ("/marks".equals(url)) {
                studentMarks(req, resp);
            } else if ("/studentadd".equals(url)) {
                studentAdd(req, resp);
            } else if ("/subjectadd".equals(url)) {
                subjectAdd(req, resp);
            } else if ("/subjects".equals(url)) {
                getServletContext().getRequestDispatcher("/subjects.jsp").forward(req, resp);
            } else if ("/update".equals(url)) {
                update(req, resp);
            } else {
                getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка в GeneralServlet " + e.getMessage());
        }
    }
}
