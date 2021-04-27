<%-- 
    Document   : welcome
    Created on : Jan 25, 2021, 10:35:09 AM
    Author     : SE140066
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>Welcome Page</title>
    </head>
    <body>
        <h4>HELLO <c:out value="${sessionScope.FULLNAME}"/></h4>
        <a href="LogoutController">Logout</a>
        <p>Choose the subject you want to do on the quiz</p>
        <form action="AcceptController">
            <input type="hidden" name="txtUserID" value="${sessionScope.USERID}"/>
            <c:if test="${not empty requestScope.LIST_SUBJECT}">
                <select data-trigger="" name="txtSubject">
                    <c:forEach var="item" varStatus="counter" items="${requestScope.LIST_SUBJECT}">
                        <option>${item}</option>
                    </c:forEach>
                </select>
            </c:if>
            <br>
            <button <input class="btn-search" type="submit" value="Accept"/>Accept</button>
        </form>
        <c:set var="message" value="${requestScope.MESSAGE}"/>
        <c:if test="${not empty message}">
            <script>
                alert("${message}");
            </script>
        </c:if>
    </body>
</html>
