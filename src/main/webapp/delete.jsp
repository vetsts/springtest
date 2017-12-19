<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%@ page contentType="text/html; charset=UTF-8" %>





<!DOCTYPE html>
<html>
 <head>
 <meta charset="utf-8">
  <title>Students.web - Удалить</title>
   <link rel="stylesheet" type="text/css" href="style.css">
 </head>
 <body>
  <div class="container">
   <div class="header">STUDENTS.WEB
   <div class="menu">
   <a href="index">Главная</a> |
   <a href="#">О проекте</a> |
   <a href="#">Контакты</a> |
   <a href="#">Вопросы</a>
   <div style="float: right"><a href="exit">Выход</a> &nbsp;</div>
   </div>
   </div>
   <div class="sidebar">
    <p><a href="marks">Список студентов</a></p>
	<p><a href="subject-list">Список предметов</a></p>
    <p><a href="studentadd">Добавить студента</a></p>
    <p><a href="subjectadd">Добавить предмет</a></p>
	<p><a href="markadd">Добавить оценку</a></p>
    
	
   </div>
   <div class="content">
    <h2>Удалить оценку студента</h2>
 <a href="update?studentId=${studentId}"><<< Вернуться назад</a>
		<c:if test = "${message != null }">
         <p><c:out value = "${message}"/><p>
		 <a href="delete?markId=${markId}&studentId=${studentId}&value=1">Удалить</a>
      </c:if>	
	  	  <c:if test = "${markDeleted != null }">
         <p><c:out value = "${markDeleted}"/><p>
      </c:if>
	   <c:if test="${studentIdRedirect != null}">
		<c:redirect url = "http://localhost:8080/TestGradle/update?studentId=${studentIdRedirect}"/>
	</c:if> 
<br>
 
</div>
<div class="footer">&copy; STUDENTS.WEB</div>
</div>
</body>
</html>