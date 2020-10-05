<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<style> table.meals-table, .meals-table td { border: 2px solid black; border-collapse: collapse; padding: 5px } </style>
<table class="meals-table">
    <tr>
        <td style="text-align: center;"><b>Date:</b></td>
        <td style="text-align: center;"><b>Description:</b></td>
        <td style="text-align: center;"><b>Calories:</b></td>
        <td></td>
        <td></td>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${meals}">
        <tr style="color: ${meal.isExcess() ? "red" : "green"}">
            <td>
                <javatime:format value="${meal.getDateTime()}" pattern="yyyy-MM-dd HH:mm" var="date"/>
                <c:out value="${date}"/>
            </td>
            <td><c:out value="${meal.getDescription()}"/></td>
            <td><c:out value="${meal.getCalories()}"/></td>
            <td>Update</td>
            <td>Delete</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
