<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Drink is ready</title>
</head>
<body>
<img src="img/coffee.png"/>
<p>${drink.getName()} is ready.</p>
<form method="post" action="login">
    <input type="submit" value="Back to menu" >
    <input type="submit" value="Exit" formaction="exit">
</form>
<%@include file="footer.jsp"%>
</body>
</html>
