<%-- 
    Document   : createuser
    Created on : Jan 25, 2021, 8:23:46 AM
    Author     : SE140066
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    </head>
    <body>
        <form action="MainController" method="POST">
            <div class="row register-form">
                <div class="col-md-6">
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtUserID" placeholder="User ID *" value="${param.txtUserID}" required/>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtFullName" placeholder="Full Name *" value="${param.txtFullName}" required/>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtRoleID" value="Us" readonly/>
                    </div>
                    <div class="form-group">
                        <input type="text" minlength="10" maxlength="15" name="txtPhoneNumber" class="form-control" placeholder="Your Phone *" value=""/>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtAddress"  placeholder="Your Address *" value=""/>
                    </div>
                    <div class="form-group">
                        <input type="email" class="form-control" name="txtEmail" placeholder="Your Email *" value=""/>
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="txtPassword" placeholder="Password *" value="" required/>
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="txtConfirm" placeholder="Confirm Password *" value="" required/>
                    </div>
                    <c:set var="message" value="${sessionScope.ERROR}"/>
                    
                    <c:if test="${not empty message}">
                        <c:out value="${message}" />
                    </c:if>
                    <input type="submit" name="btnAction" class="btnRegister" value="Register"/>
                </div>
            </div>
        </form>
    </body>
</html>
