<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <style><%@include file="stylesheets/reg.css"%></style>
    <title>Registration</title>
</head>
<body>
<fieldset>
    <form action="registration" method="POST" accept-charset="UTF-8">
        <label for="login">Login</label> <input type="text" id="login" class="txt" name="login" accesskey="l" placeholder="Login" pattern="[a-zA-Z0-9_.]{6,25}"  required><br/>
        <label for="password">Password</label> <input type="password" id="password" class="txt" name="password" accesskey="p" placeholder="Password" pattern="[a-zA-Z0-9]{6,25}" required><br/>
        <label for="confirmPassword">Confirm password</label> <input type="password" id="confirmPassword" class="txt" name="confirmPassword" accesskey="c" placeholder="Password" pattern="[a-zA-Z0-9]{6,25}" required><br/>
        <label for="firstName">First name</label> <input type="text" id="firstName" class="txt" name="firstName" placeholder="First name" accesskey="f" pattern="[a-zA-Zа-яА-ЯїЇіІєЄёЁ]{3,100}" required><br/>
        <label for="lastName">Last name</label> <input type="text" id="lastName" class="txt" name="lastName" placeholder="Last name" accesskey="l" pattern="[a-zA-Zа-яА-ЯїЇіІєЄёЁ]{3,100}" required><br/>
        <label for="middleName">Middle name</label> <input type="text" id="middleName" class="txt" name="middleName" placeholder="Middle name" accesskey="m" pattern="[a-zA-Zа-яА-ЯїЇіІєЄёЁ]{3,100}" required><br/>
        <label></label><input type="submit" id="submit" name="submit" value="Submit">
    </form>
</fieldset>
<%@include file="footer.jsp"%>
</body>
</html>