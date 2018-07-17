<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS"/>
    <spring:url value="/resources/css/style.css" var="styleCSS"/>
    <link href="${bootstrapCSS}" rel="stylesheet"/>
    <link href="${styleCSS}" rel="stylesheet"/>
    <title>Admin Page</title>
</head>
<body>
<nav class="navbar navbar-toggleable-md sticky-top navbar-my navbar-inverse">
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="btn btn-primary btn-my" href="/" role="button">Home</a>
        </li>
    </ul>
</nav>
<div class="page-header header-my">
    <h1>Railroad</h1>
</div>
<div>
    <hr class="hr-primary"> </div>
<div class="p-3 gradient-overlay bg-secondary"> </div>

<div class="py-5">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="card-body p-5 card-my">
                    <h3 class="pb-3">Registration</h3>
                    <form:form method="post" modelAttribute="userJSP" action="/registration">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-12 my-select">
                                    <label>Login</label>
                                    <form:input path="login" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 my-select">
                                    <label>Password</label>
                                    <form:password path="password" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 my-select">
                                    <label>Repeat password</label>
                                    <form:password path="repeatPassword" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="form-group" id="buttons">
                                <div class="py-3">
                                    <div class="py-4">
                                        <form:button class="btn btn-primary btn-my">Sign Up</form:button>
                                    </div>
                                </div>
                            </div>
                            <div class="alert alert-danger alert-my" role="alert" id="alert-danger">
                                Password doesn't match
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

<spring:url value="/resources/js/jquery-3.3.1.min.js" var="jQuery"/>
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS"/>
<spring:url value="/resources/js/railroad.js" var="railroadJS"/>
<script src="${jQuery}"></script>
<script src="${bootstrapJS}"></script>
<script src="${railroadJS}"></script>
</body>
</html>
