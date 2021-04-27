<%-- 
    Document   : history
    Created on : Jan 30, 2021, 12:07:58 AM
    Author     : SE140066
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>History Page</title>
    </head>
    <body>
        <div class="table table-hover">
            <a href="ManageController">Return</a>
            <a href="LogoutController">Logout</a>  
            <form action="HistoryAdminController">
                <c:if test="${not empty sessionScope.LISTQUEST}">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>User ID</th>
                                <th>Content</th>
                                <th>Create Date</th>
                                <th>Question ID</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="quest" varStatus="counter" items="${requestScope.LIST_HISTORY}">
                            <form action="HistoryAdminController">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>${quest.userID}</td>
                                    <td>${quest.content}</td>
                                    <td>${quest.createDate}</td>
                                    <td>${quest.questionID}</td>
                                </tr>
                            </form>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
        </div>
    </body>
</html>
