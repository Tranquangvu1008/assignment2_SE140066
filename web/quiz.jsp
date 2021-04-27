<%-- 
    Document   : quiz
    Created on : Jan 26, 2021, 5:06:24 PM
    Author     : SE140066
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>Quiz Page</title>
    </head>
    <body>

        <c:if test="${not empty sessionScope.LISTQUIZ}">
            <form action="QuizController">
                <c:forEach var="quest" varStatus="counter" items="${sessionScope.LISTQUIZ}">
                    <h3>${requestScope.CURRENT_PAGE}. ${quest.questionName}</h3>
                    <input type="hidden" name="txtQuestionID" value="${quest.questionID}"/>
                    <input name="txtAnswer" type="radio" value="${quest.answerA}"  <c:if test="${quest.answerA eq sessionScope.ANSWER}">checked</c:if> />${quest.answerA}<br>
                    <input name="txtAnswer" type="radio" value="${quest.answerB}"  <c:if test="${quest.answerB eq sessionScope.ANSWER}">checked</c:if>/>${quest.answerB}<br>
                    <input name="txtAnswer" type="radio" value="${quest.answerC}"  <c:if test="${quest.answerC eq sessionScope.ANSWER}">checked</c:if>/>${quest.answerC}<br>
                    <input name="txtAnswer" type="radio" value="${quest.answerD}"  <c:if test="${quest.answerD eq sessionScope.ANSWER}">checked</c:if>/>${quest.answerD}<br><br>
                </c:forEach>
                <c:forEach begin="1" end="${requestScope.NUMBER_PAGE}" var="i">
                    <button type="submit" name="index" value="${i}" <c:if test="${requestScope.CURRENT_PAGE == i}"> style="font-weight: bold;"</c:if>>${i}</button>
                </c:forEach>
                <c:if test="${requestScope.CURRENT_PAGE < requestScope.NUMBER_PAGE}">
                    <input type="hidden" name="index" value="${requestScope.CURRENT_PAGE + 1}"/>
                    <input type="submit" value="Next"/>
                </c:if>
                <c:if test="${requestScope.CURRENT_PAGE >= requestScope.NUMBER_PAGE}">
                    <input type="hidden" name="index" value="${1}"/>
                    <input type="submit" value="Next"/>
                </c:if>
            </form>
            <form action="ResultController" id="end" >
                <input type="submit" value="Finish" onclick="return confirm('Do you want to submit your quiz ?');"/>
            </form>
        </c:if>
        <div>
            <div>
                Time left: <span id="timer"></span>
            </div>
        </div>

        <script type="text/javascript">
            function cal(timer) {
                var minutes = parseInt(timer / 60, 10);
                var seconds = parseInt(timer % 60, 10);

                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                document.querySelector('#timer').textContent = minutes + ":" + seconds;

            }

            function startTimer(duration) {
                var timer = duration;
                cal(timer);
                var intervalCount = setInterval(function () {
                    cal(timer);

                    if (--timer < 0) {
                        document.getElementById('end').submit();
                        clearInterval(intervalCount);
                    }

                }, 1000);
            }

            window.onload = function () {
                var endTime = ${sessionScope.TIME.endTime.time};
                var curTime = new Date().getTime();
                var diff = Math.round((endTime - curTime) / 1000);
                var fiveMinutes = diff;
                startTimer(fiveMinutes);
            }
        </script>
    </body>
</html>
