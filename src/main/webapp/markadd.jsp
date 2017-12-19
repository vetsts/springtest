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
    <h2>Добавить студента, предмет, оценку</h2>

			<form action="markadd" method="GET">
			<b>Студент</b><br>
            <select name ="studentId" >
			<option value="0">Выберите студента</option>
            <c:forEach var="student" items="${studentList}">
				
	            <c:set var="idStud" value = "${student.id}"/>
	 	    <c:choose>
		        <c:when test = "${selectStudent == idStud }">
	            <c:set var="selectStud">selected</c:set>
	        </c:when>
	            <c:otherwise>
			    <c:set var="selectStud"></c:set>
			    </c:otherwise>
            </c:choose>
      <option value="${student.id}" ${selectStud} >${student.name} ${student.surname}</option> 
	
    </c:forEach>
	</select><br><br><br>
	
			<b>Предмет</b><br>
            <select name ="subjectId">
			
			<option value="0">Выберите предмет</option>
 
	<c:forEach var="subject" items="${subjectList}">
	  <c:set var="idSubj" value = "${subject.id}"/>
		<c:choose>
		 <c:when test = "${selectSubject == idSubj }">
	      <c:set var="selectSubj">selected</c:set>
	       </c:when>
	        <c:otherwise>
			 <c:set var="selectSubj"></c:set>
			 </c:otherwise>
            </c:choose>
	     <option value = "${subject.id}" ${selectSubj} >${subject.subject}</option>
	</c:forEach>

	</select>

     <br><br><br>
	 
	  <c:if test = "${selectMark == 1}">
	 <c:set var="selectMark_1">selected</c:set>
	 </c:if>
	 	  <c:if test = "${selectMark == 2}">
	 <c:set var="selectMark_2">selected</c:set>
	 </c:if>
	  <c:if test = "${selectMark == 3}">
	 <c:set var="selectMark_3">selected</c:set>
	 </c:if>
	  <c:if test = "${selectMark == 4}">
	 <c:set var="selectMark_4">selected</c:set>
	 </c:if>
	  <c:if test = "${selectMark == 5}">
	 <c:set var="selectMark_5">selected</c:set>
	 </c:if>
	 
	          <b>Оценка</b><br>
	    <select name="mark">
        <option value="0">Выберите оценку</option>
        <option value="1" ${selectMark_1} >1</option>
        <option value="2" ${selectMark_2} >2</option>
        <option value="3" ${selectMark_3} >3</option>
        <option value="4" ${selectMark_4} >4</option>
        <option value="5" ${selectMark_5} >5</option>
        </select> <br><br><br><br><br><br>
	 
    <input type="submit" value="Добавить оценку" /></form>

      <c:if test = "${stId != null}">
         <p><c:out value = "${stId}"/><p>
      </c:if>
	  <c:if test = "${sbId != null}">
         <p><c:out value = "${sbId}"/><p>
      </c:if>
	  <c:if test = "${studMark != null}">
         <p><c:out value = "${studMark}"/><p>
      </c:if>
	  
	  <c:if test = "${studentDeleted != null }">
         <p><c:out value = "${studentDeleted}"/><p>
      </c:if>
	  <c:if test = "${subjectDeleted != null }">
         <p><c:out value = "${subjectDeleted}"/><p>
      </c:if>
	  
</div>
<div class="footer">&copy; STUDENTS.WEB</div>
</div>
</body>
</html>