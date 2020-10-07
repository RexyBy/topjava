<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        ${param.get("action").equalsIgnoreCase("add") ? "Add meal" : "Update meal" }
    </title>
</head>
<body>
<br>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>
    ${param.get("action").equalsIgnoreCase("add") ? "Add meal" : "Update meal" }
</h2>
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
    </form>
</body>
</html>
