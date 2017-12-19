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

 <h2>Добавить предмет</h2><br>
 <br><br>
 <form action="subjectadd" method="GET" >
 <table border = 1 >
 
 </table>
 <p>Введите предмет: <input type = "text" value ="" name = "subjectName"></p>
 <input type = "submit" value = "Добавить предмет" /></form>
<c:if test="${value != null}">
		<c:redirect url = "http://localhost:8080/TestGradle/subjectadd"/>
	</c:if>
	<c:if test = "${subjectnm != null}">
         <p><c:out value = "${subjectnm}"/><p>
      </c:if>
</div>
<div class="footer">&copy; STUDENTS.WEB</div>
</div>
</body>
</html>