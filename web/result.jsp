<%-- 
    Document   : result
    Created on : Jan 27, 2021, 5:12:10 PM
    Author     : SE140066
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>Result Page</title>
    </head>
    <body>
        <h5>Your <c:out value="${requestScope.SUBJECTID}"/> test score is <c:out value="${requestScope.TOTALSCORE}"/></h5>
        <a href="WelcomeController">Return</a>
        <a href="LogoutController">Logout</a>
        
        <form action="ResultController">
            <input type="text" name="txtQuestionContent" value="${param.txtQuestionContent}" placeholder="Question Content*"/>
            <button <input type="submit" value="Search"/>SEARCH</button>
        </form>

        <div>
            <c:if test="${not empty sessionScope.LISTMUL}">

                <c:forEach var="quest" varStatus="counter" items="${sessionScope.LISTMUL}">
                    <h3>${counter.count}. ${quest.questionContent}</h3>
                    <div <c:if test="${quest.answerA == quest.answerCorrect}"> style="color: green" </c:if>> <input disabled="true" type="radio" <c:if test="${quest.answerA == quest.answer}">checked</c:if>>${quest.answerA}</div>
                    <div <c:if test="${quest.answerB == quest.answerCorrect}"> style="color: green" </c:if>> <input disabled="true" type="radio" <c:if test="${quest.answerB == quest.answer}">checked</c:if>>${quest.answerB}</div>
                    <div <c:if test="${quest.answerC == quest.answerCorrect}"> style="color: green" </c:if>> <input disabled="true" type="radio" <c:if test="${quest.answerC == quest.answer}">checked</c:if>>${quest.answerC}</div>
                    <div <c:if test="${quest.answerD == quest.answerCorrect}"> style="color: green" </c:if>> <input disabled="true" type="radio" <c:if test="${quest.answerD == quest.answer}">checked</c:if>>${quest.answerD}</div><br>
                </c:forEach>
            </c:if>
        </div>
    </body>
</html>
