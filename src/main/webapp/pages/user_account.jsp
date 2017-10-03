<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n" var="loc"/>
<html>
<head>
    <style>
        <%@ include file="stylesheets/user_account.css" %>
    </style>
    <title>Personal cabinet</title>
</head>
<body>
<fieldset>
    <form action="replenishAccount" method="POST">
        <label for="cardNumber"><fmt:message key="card_number" bundle="${loc}"/></label><input type="text" class="txt" id="cardNumber" name="cardNumber"
                                                           pattern="[0-9]{16,19}" required><br/>
        <label for="secureCode"><fmt:message key="secure_code" bundle="${loc}"/> (CVV2)</label><input type="text" class="txt" name="secureCode" id="secureCode"
                                                                 pattern="[0-9]{3,3}" required><br/>
        <label for="amount"><fmt:message key="amount" bundle="${loc}"/></label><input type="text" class="txt" name="amount" id="amount" pattern="[0-9]{1,5}"
                                                 required><br/>
        <label></label><input type="submit" name="submit" class="buttons" value="<fmt:message key="confirm" bundle="${loc}"/>">
    </form>
    <form method="GET">
        <label></label><input type="submit" name="backToMenu" class="buttons" value="<fmt:message key="back_to_menu" bundle="${loc}"/>" formaction="login">
    </form>
</fieldset>
<%@include file="footer.jsp"%>
</body>
</html>
