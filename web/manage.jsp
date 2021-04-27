<%-- 
    Document   : manage
    Created on : Jan 25, 2021, 5:41:09 PM
    Author     : SE140066
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Question Page</title>
        <!-- JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
        <!-- CSS only -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    </head>
    <body>
        <form action="SearchController">
            <input type="hidden" name="txtUserID" value="${sessionScope.USERID}"/>
            <input type="text" name="txtSearch" value="${param.txtSearch}" placeholder="Question Content*"/>
            <c:if test="${not empty requestScope.LIST_SUBJECT}">
                <select data-trigger="" name="txtSubject">
                    <option selected>${param.txtSubject}</option>
                    <option disabled>---</option>
                    <option>All</option>
                    <c:forEach var="item" varStatus="counter" items="${requestScope.LIST_SUBJECT}">
                        <option>${item}</option>
                    </c:forEach>
                </select>
            </c:if>
            <input type="radio" id="true" name="txtStatusQuest" value="true" <c:if test="${requestScope.STATUS eq 'true'}"> checked </c:if>><label for="true">Active</label>
            <input type="radio" id="false" name="txtStatusQuest" value="false" <c:if test="${requestScope.STATUS eq 'false'}"> checked </c:if>><label for="false">Not Active</label>
                <button <input class="btn-search" name="btnAction" type="submit" value="Search"/>SEARCH</button>
            </form>

            <a href="ManageController">Load</a>
            <a href="CreateController">Create Question</a>
            <a href="CreateSubController">Create Subject</a>
            <a href="HistoryAdminController">History</a>
            <a href="LogoutController">Logout</a>

            <div class="table table-hover">
                <form action="UpdateQuestController">
                <c:if test="${not empty sessionScope.LISTQUEST}">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Question Name</th>
                                <th>Answer A</th>
                                <th>Answer B</th>
                                <th>Answer C</th>
                                <th>Answer D</th>
                                <th>Answer Correct</th>
                                <th>Subject ID</th>
                                <th>Status</th>
                                <th>Update</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="quest" varStatus="counter" items="${sessionScope.LISTQUEST}">
                            <form action="UpdateQuestController">
                                <tr>
                                    <td><input type="type" name="txtQuestionID" value="${quest.questionID}" readonly/></td>
                                    <td><input type="text" name="txtQuestionName" value="${quest.questionName}" required/></td>
                                    <td><input type="text" name="txtAnswerA" value="${quest.answerA}" required/></td>
                                    <td><input type="text" name="txtAnswerB" value="${quest.answerB}" required/></td>
                                    <td><input type="text" name="txtAnswerC" value="${quest.answerC}" required/></td>
                                    <td><input type="text" name="txtAnswerD" value="${quest.answerD}" required/></td>
                                    <td>
                                        <select name="txtAnswerCorrect" required>
                                            <option value="${quest.answerA}" <c:if test="${quest.answerA eq quest.answerCorrect}"> selected</c:if>>${quest.answerA}</option>
                                            <option value="${quest.answerB}" <c:if test="${quest.answerB eq quest.answerCorrect}"> selected</c:if>>${quest.answerB}</option>
                                            <option value="${quest.answerC}" <c:if test="${quest.answerC eq quest.answerCorrect}"> selected</c:if>>${quest.answerC}</option>
                                            <option value="${quest.answerD}" <c:if test="${quest.answerD eq quest.answerCorrect}"> selected</c:if>>${quest.answerD}</option>
                                            </select>
                                        </td>
                                        <td>
                                            <select name="txtSubjectID">
                                            <c:forEach var="sub" items="${requestScope.LIST_SUBJECT}">
                                                <option value="${sub}"<c:if test="${quest.subjectID eq sub}"> selected</c:if>>${sub}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <input type="checkbox" name="txtStatus" <c:if test="${quest.status}">checked</c:if>/>
                                        </td>
                                        <td>
                                            <input type="hidden" name="txtUserID" value="${sessionScope.USERID}"/>
                                        <input type="submit" value="Update"/>
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:forEach begin="1" end="${requestScope.NUMBER_PAGE}" var="i">
                    <c:url var="paging" value="ManageController">
                        <c:param name="index" value="${i}"/>
                    </c:url>
                    <a href="${paging}" <c:if test="${requestScope.CURRENT_PAGE == i}"> style="font-weight: bold;"</c:if>>${i}</a>
                </c:forEach>

                <c:set var="message" value="${requestScope.NOTI}"/>
                <c:if test="${not empty message}">
                    <script>
                        alert("${message}");
                    </script>
                </c:if>
            </form>
        </div>
    </body>
</html>
