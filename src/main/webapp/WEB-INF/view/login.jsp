<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
                    <h3 class="pb-3">Login</h3>
                    <form name="f" action="/perform_login" method="post">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-12 my-select">
                                    <label>Login</label>
                                    <input class="form-control" placeholder="Login" name="login" type="text">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 my-select">
                                    <label>Password</label>
                                    <input class="form-control" placeholder="Password" name="password" type="password">
                                </div>
                            </div>
                            <div class="form-group" id="buttons">
                                <div class="py-3">
                                    <div class="py-4">
                                        <button type="submit" class="btn btn-primary btn-my" name="submit">Sign In</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<spring:url value="/resources/js/jquery-3.3.1.min.js" var="jQuery"/>
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS"/>
<script src="${jQuery}"></script>
<script src="${bootstrapJS}"></script>
</body>
</html>
