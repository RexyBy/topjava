<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:if test='${param.get("action").equalsIgnoreCase("add")}'>
        <title>Add meal</title>
    </c:if>
    <c:if test='${param.get("action").equalsIgnoreCase("update")}'>
        <title>Update meal</title>
    </c:if>
</head>
<body>
<br>
<h3><a href="index.html">Home</a></h3>
<hr>
<c:if test='${param.get("action").equalsIgnoreCase("add")}'>
    <h2>Add meal</h2>
<form method="POST" action='meals' name="addMeal">
    <p><b>DateTime :</b> <label>
        <input type="datetime-local" name="mealDateTime"/>
    </label></p>
    <p><b>Description :</b> <label>
        <input type="text" name="mealDescription"/>
    </label></p>
    <p><b>Calories :</b> <label>
        <input type="number" name="mealCalories"/>
    </label></p>
    <p><input type="submit" value="Save"/> &nbsp; <input type="reset" value="Cancel"/></p>
</c:if>
<c:if test='${param.get("action").equalsIgnoreCase("update")}'>
    <h2>Update meal</h2>
    <form method="POST" action='meals?id=${param.get("id")}' name="updateMeal">
        <p><b>DateTime :</b> <label>
            <input type="datetime-local" name="mealDateTime" value="${param.get("dateTime")}"/>
        </label></p>
        <p><b>Description :</b> <label>
            <input type="text" name="mealDescription" value="${param.get("description")}"/>
        </label></p>
        <p><b>Calories :</b> <label>
            <input type="number" name="mealCalories" value="${param.get("calories")}"/>
        </label></p>
        <p><input type="submit" value="Save"/> &nbsp; <input type="reset" value="Cancel"/></p>
</c:if>
</form>
</body>
</html>
