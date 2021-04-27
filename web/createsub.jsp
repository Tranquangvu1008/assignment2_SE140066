<%-- 
    Document   : createsub
    Created on : Feb 2, 2021, 8:20:13 AM
    Author     : SE140066
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>Create subject Page</title>
    </head>
    <body>
        <h3>Welcome</h3>
        <p class="intro">Quiz Online NO.1 in Vietnam</p
        <h3 class="register-heading">Create subject</h3>
        <form action="CreateSubController">
            <div class="col-md-6">
                <div class="form-group">
                    <input type="text" class="form-control" name="txtSubjectID" placeholder="Subject ID *" value="${param.txtSubjectID}" required/>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="txtSubjectName" placeholder="Subject Name *" value="${param.txtSubjectName}" required/>
                </div>
            </div>
            <input type="submit" class="btnRegister" value="Create"/>
        </form>
        <c:set var="message" value="${requestScope.MESSAGE}"/>
        <c:if test="${not empty message}">
            <script>
                alert("${message}");
            </script>
        </c:if>
        <a href="ManageController">Return</a>
        <a href="LogoutController">Logout</a>   
    </body>
</html>
