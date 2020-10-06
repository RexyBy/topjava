<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add meal</title>
</head>
<body>
<br>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add/Update meal</h2>
<form method="POST" action='meals?action=<%= request.getParameter("action")%>&id=<%= request.getParameter("id")%>' name="addMeal">
    <p><b>DateTime :</b> <input type="datetime-local" name="mealDateTime"/></p>
    <p><b>Description :</b> <input type="text" name="mealDescription"/></p>
    <p><b>Calories :</b> <input type="number" name="mealCalories"/></p>
    <p><input type="submit" value="Save"/> &nbsp; <input type="reset" value="Cancel"/></p>
</form>
</body>
</html>
