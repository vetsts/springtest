package com.students.daopattern.mysql;

import com.students.daopattern.dao.StudentDao;
import com.students.daopattern.dto.Mark;
import com.students.daopattern.dto.TestMark;
import com.students.web.GeneralServlet;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDao {

    private StudentDao studentDao;

    @Before
    public void connect() throws Exception {

        studentDao = new MySqlStudentDao("test");
    }

    @After
    public void closeDao() throws Exception {
        studentDao.close();
    }

    @Test
    public void testDaoSpringConnection() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        GeneralServlet con = (GeneralServlet) context.getBean("daocon");
        studentDao = con.getDao();
        Assert.assertNotNull(studentDao);
    }

    @Test
    public void testDaoConection() throws Exception {

        Assert.assertNotNull(studentDao);
    }

    @Test
    public void testCreate() throws Exception {
        List list = studentDao.getStudSubjMarkAll();
        int oldListSize = list.size();
        Assert.assertTrue("Размер должен быть > 0", oldListSize > 0);
        studentDao.createMark(new Mark(5, 5, 5));
        list = studentDao.getStudSubjMarkAll();
        int newListSize = list.size();
        Assert.assertNotNull("Новый список не должен быть пустым", list);
        Assert.assertEquals("Размер списка должен быть равен 1", 1, newListSize - oldListSize);
    }

    @Test
    public void testUpdateSubject() throws Exception {
        studentDao.updateSubject(5, "Информатика");
        TestMark tm = studentDao.getTestSubject(5);
        String subject = tm.getSubject();
        Assert.assertEquals("Название предмета должно быть Информатика", "Информатика", subject);
    }

    @Test
    public void testSelectStudents() throws Exception {
        List list = studentDao.getStudents();
        Assert.assertNotNull("Список студентов не должен быть пустым", list);
    }

    @Test
    public void testDeleteMark() throws Exception {
        List list = studentDao.getStudSubjMarkAll();
        int oldListSize = list.size();
        Assert.assertTrue("Размер должен быть > 0", oldListSize > 0);
        studentDao.delete(new Mark(22, 5, 5, 5));
        list = studentDao.getStudSubjMarkAll();
        int newListSize = list.size();
        Assert.assertNotNull("Новый список не должен быть пустым", list);
        Assert.assertEquals("Размер списка должен быть равен 1", 1, oldListSize - newListSize);
    }

}
