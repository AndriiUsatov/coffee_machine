<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n" var="loc"/>
<html lang="en">
<head>
    <style>
        <%@include file="stylesheets/reg.css" %>
    </style>
    <title><fmt:message key="registration" bundle="${loc}"/></title>
</head>
<body>
<fieldset>
    <form method="POST" accept-charset="UTF-8">
        <label for="login"><fmt:message key="login" bundle="${loc}"/></label>
        <input type="text" id="login" class="txt" name="login" accesskey="l"
               placeholder="<fmt:message key="login" bundle="${loc}"/>" pattern="^[-a-zA-Z0-9_]{6,25}$" required
               value="${loginValue}"/>
        <c:if test="${not empty loginMessage}">
            <span><fmt:message key="${loginMessage}" bundle="${loc}"/></span></c:if><br/>
        <p><fmt:message key="login_message" bundle="${loc}"/></p>
        <label for="password"><fmt:message key="password" bundle="${loc}"/></label>
        <input type="password" id="password" class="txt" name="password" accesskey="p"
               placeholder="<fmt:message key="password" bundle="${loc}"/>" pattern="^[a-zA-Z0-9]{6,25}$" required/>
        <c:if test="${not empty passwordMessage}">
            <span><fmt:message key="${passwordMessage}" bundle="${loc}"/></span></c:if><br/>
        <p><fmt:message key="password_message" bundle="${loc}"/></p>
        <label for="confirmPassword"><fmt:message key="confirm_password" bundle="${loc}"/></label>
        <input type="password" id="confirmPassword" class="txt" name="confirmPassword" accesskey="c"
               placeholder="<fmt:message key="confirm_password" bundle="${loc}"/>" pattern="^[a-zA-Z0-9]{6,25}$"
               required/><br/>
        <label for="firstName"><fmt:message key="first_name" bundle="${loc}"/></label>
        <input type="text" id="firstName" class="txt" name="firstName"
               placeholder="<fmt:message key="first_name" bundle="${loc}"/>" accesskey="f"
               pattern="^[a-zA-Zа-яА-ЯїЇіІєЄёЁ][- a-zA-Zа-яА-ЯїЇіІєЄёЁ']{1,100}$" required
               value="${firstNameValue}"/><br/>
        <p><fmt:message key="first_name_message" bundle="${loc}"/></p>
        <label for="lastName"><fmt:message key="last_name" bundle="${loc}"/></label>
        <input type="text" id="lastName" class="txt" name="lastName"
               placeholder="<fmt:message key="last_name" bundle="${loc}"/>" accesskey="l"
               pattern="^[a-zA-Zа-яА-ЯїЇіІєЄёЁ][- a-zA-Zа-яА-ЯїЇіІєЄёЁ']{1,100}$" required
               value="${lastNameValue}"/><br/>
        <p><fmt:message key="last_name_message" bundle="${loc}"/></p>
        <label for="middleName"><fmt:message key="middle_name" bundle="${loc}"/></label>
        <input type="text" id="middleName" class="txt" name="middleName"
               placeholder="<fmt:message key="middle_name" bundle="${loc}"/>" accesskey="m"
               pattern="^[a-zA-Zа-яА-ЯїЇіІєЄёЁ][- a-zA-Zа-яА-ЯїЇіІєЄёЁ']{1,100}$" required
               value="${middleNameValue}"/><br/>
        <p><fmt:message key="middle_name_message" bundle="${loc}"/></p>
        <input type="submit" formaction="/machine/registration" class="registrationFormButtons" name="submit"
               value="<fmt:message bundle="${loc}" key="submit"/>">
        <input type="submit" formnovalidate="formnovalidate" formaction="/machine/index" class="registrationFormButtons"
               value="<fmt:message bundle="${loc}" key="back_to_home_page"/>"/>
    </form>
</fieldset>
<%@include file="footer.jsp" %>
</body>
</html>