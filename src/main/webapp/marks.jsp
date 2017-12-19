<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
 <head>
 <meta charset="utf-8">
  <title>Students.web - Оценки студента</title>
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
   <h2>Список студентов</h2>
  
  	<c:set var="value" value = "0"/>


 
 <table border="1" cellpadding="5" cellspacing="5" >	
		<c:forEach var="student" items="${studentList}">
			<tr>
				
			   <td><a href="update?studentId=${student.id}">${student.name} ${student.surname}</a> </td>
			   <td><a href="edit-student?studentId=${student.id}">Редактировать</a> </td>
			   <td><a href="delete-student?studentId=${student.id}">Удалить</a> </td>
		</c:forEach>
		
	</table>
	
 <br>
 <br>
 <%--Для отображения предыдущей ссылки, кроме первой страницы --%>
 
	<c:if test="${currentPage != 1}">
		<td><a href="marks?page=${currentPage - 1}"> <<< </a></td>
	</c:if>

	<%--Для отображения номеров страниц.--%>
	
	<table border="1" cellpadding="5" cellspacing="5" >
		<tr>
			<c:forEach begin="1" end="${allPages}" var="i">
				<c:choose>
					<c:when test="${currentPage eq i}">
						<td>${i}</td>
					</c:when>
					<c:otherwise>
						<td><a href="marks?page=${i}">${i}</a></td>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tr>
	</table>
	
	<%--Для показа следующей ссылки --%>
<c:if test="${currentPage != allPages }">
		<td> &nbsp <a href="marks?page=${currentPage + 1}"> >>> </a></td>
</c:if>
	<br><br>
  <c:if test="${studentMarks != null}">
        <table border="1" cellpadding="5" cellspacing="5" >
		<c:forEach var="studentM" items="${studentMarks}">
			<tr>
				<td>${studentM.name}
				    ${studentM.surname}</td>
				<td>${studentM.subject}</td>
				<td>${studentM.mark}</td>
			   </tr>
		</c:forEach>
		</table>
  </c:if>
  
  <c:if test = "${valUpdate != null }">
         <p><c:out value = "${studName}"/><p>
      </c:if>
	  	  <c:if test = "${studentDeleted != null }">
         <p><c:out value = "${studentDeleted}"/><p>
      </c:if>
</div>

<div class="footer">&copy; STUDENTS.WEB</div>
</div>
</body>
</html>