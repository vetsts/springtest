<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html; charset=UTF-8" %>


<!DOCTYPE html>
<html>
 <head>
 <meta charset="utf-8">
  <title>Students.web - Обновить</title>
   <link rel="stylesheet" type="text/css" href="style.css">
 </head>
 <body>
  <div class="container">
   <div class="header">STUDENTS.WEB
   <div class="menu">
   <a href="index">Главная</a> |
   <a href="#">О проекте</a> |
   <a href="#">Контакты</a> |
   <a href="#">Вопросы</a> |
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
    <h2>Оценки судента</h2>
	
	<c:set var="value" value = "0"/>
	
			
            <table  border = 1>
            <c:forEach var="mark" items="${studentMarks}">
			<tr>
	      
              <td>${mark.name} ${mark.surname}</td>
              <td>${mark.subject}</td>
              <td>${mark.mark}</td>
			  <td><a href="edit?markId=${mark.id}">Редактировать</a> </td>
			   <td><a href="delete?markId=${mark.id}&studentId=${studentId}">Удалить</a> </td>
		</c:forEach>
		</tr>
	</table><br>
	
    
	<c:if test="${markId != null && markDeleted == null}">
		<c:redirect url = "http://localhost:8080/TestGradle/edit?markId=${markId}"/>
	</c:if>
	<c:if test = "${valUpdate != null }">
         <p><c:out value = "${studMark}"/><p>
      </c:if>
	  	<c:if test = "${studentDeleted != null }">
         <p><c:out value = "${studentDeleted}"/><p>
      </c:if>
</div>
<div class="footer">&copy; STUDENTS.WEB</div>
</div>
</body>
</html>