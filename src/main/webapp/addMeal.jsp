<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="action" scope="request" value="${param.get(\"action\").equalsIgnoreCase(\"add\") ? \"Add meal\" : \"Update meal\" }"/>
<html>
<head>
    <title>
        ${action}
    </title>
</head>
<body>
<br>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>
    ${action}
</h2>
    <form method="POST" action='meals?id=${meal.id}' name="updateMeal">
        <p><b>DateTime :</b> <label>
            <input type="datetime-local" name="mealDateTime" value="${meal.dateTime}"/>
        </label></p>
        <p><b>Description :</b> <label>
            <input type="text" name="mealDescription" value="${meal.description}"/>
        </label></p>
        <p><b>Calories :</b> <label>
            <input type="number" name="mealCalories" value="${meal.calories}"/>
        </label></p>
        <p><input type="submit" value="Save"/> &nbsp; <input type="reset" value="Cancel"/></p>
    </form>
</body>
</html>
