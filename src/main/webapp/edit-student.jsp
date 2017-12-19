<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html; charset=UTF-8" %>


<!DOCTYPE html>
<html>
 <head>
 <meta charset="utf-8">
  <title>Двухколоночный макет  на BlogGood.ru</title>
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
	<p><a href="subjects">Список предметов</a></p>
    <p><a href="studentadd">Добавить студента</a></p>
    <p><a href="subjectadd">Добавить предмет</a></p>
	<p><a href="markadd">Добавить оценку</a></p>
   </div>
   <div class="content">
   
 <h2>Обновить студента</h2><br>
 <b>Список студентов</b><br><br>
 <form action="edit-student" method="GET" >
 <table border = 1 >
 
 </table>
 <input type="hidden" name="studentId" value="${studentId}">
 <p>Введите имя: <input type="text" value = "${name}" name = "studentName"></p>
 <p>Введите фамилию: <input type="text" value = "${surName}" name = "studentSurName"></p>
 <input type ="submit" value ="Обновить студента" /></form>

      <c:if test="${value != null}">
		<c:redirect url = "http://localhost:8080/TestGradle/edit-student"/>
	</c:if>
  <c:if test = "${namest != null}">
         <p><c:out value = "${namest}"/><p>
      </c:if>
	  <c:if test = "${surnamest != null}">
         <p><c:out value = "${surnamest}"/><p>
      </c:if>
	  	  <c:if test = "${studentDeleted != null }">
         <p><c:out value = "${studentDeleted}"/><p>
      </c:if>
</div>
<div class="footer">&copy; STUDENTS.WEB</div>
</div>
</body>
</html>