<%-- 
    Document   : create
    Created on : Jan 26, 2021, 8:33:06 AM
    Author     : SE140066
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

        <title>Create Question Page</title>
    </head>
    <body>
        <h3>Welcome</h3>
        <p class="intro">Quiz Online NO.1 in Vietnam</p
        <h3 class="register-heading">Create question</h3>
        <form action="AddController">
            <div class="col-md-6">
                <div class="form-group">
                    <input type="text" class="form-control" name="txtQuestionName" placeholder="Question Name *" value="${param.txtQuestionName}" required/>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="txtAnswerA" placeholder="Answer A *" value="${param.txtAnswerA}" required/>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="txtAnswerB" placeholder="Answer B *" value="${param.txtAnswerB}" required/>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="txtAnswerC"  placeholder="Answer C *" value="${param.txtAnswerC}" required/>  
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <input type="text" class="form-control" name="txtAnswerD" placeholder="Answer D *" value="${param.txtAnswerD}" required/>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="txtAnswerCorrect" placeholder="Answer Correct *" value="${param.txtAnswerCorrect}" required/>
                </div>
                <c:if test="${not empty requestScope.LIST_SUBJECT}">
                    <select data-trigger="" name="txtSubject">
                        <c:forEach var="item" varStatus="counter" items="${requestScope.LIST_SUBJECT}">
                            <option>${item}</option>
                        </c:forEach>
                    </select>
                </c:if>
                <input type="hidden" text="txtUserID" value="${sessionScope.USERID}"/>
                <input type="submit" class="btnRegister" value="Add"/>
            </div>
            <c:out value="${sessionScope.ERROR}"/>
        </form>
        <c:set var="message" value="${requestScope.ERROR}"/>
        <c:if test="${not empty message}">
            <c:out value="${message}"/>
        </c:if>

        <a href="ManageController">Return</a>
        <a href="LogoutController">Logout</a>

    </body>
</html>
