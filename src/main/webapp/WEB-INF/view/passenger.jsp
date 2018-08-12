<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS"/>
    <spring:url value="/resources/css/style.css" var="styleCSS"/>
    <link href="${bootstrapCSS}" rel="stylesheet"/>
    <link href="${styleCSS}" rel="stylesheet"/>
    <title>Passenger</title>
</head>
<body>
<nav class="navbar navbar-toggleable-md sticky-top navbar-my navbar-inverse">


    <ul class="navbar-nav mr-auto">
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li class="nav-item">
                <a class="btn btn-primary btn-my" href="/admin/createStation" role="button">Admin</a>
            </li>
        </sec:authorize>

        <li class="nav-item">
            <a class="btn btn-primary btn-my" href="/" role="button">Home</a>
        </li>
    </ul>

    <ul class="navbar-nav ml-auto">
        <sec:authorize access="!isAuthenticated()">
            <li class="nav-item">
                <a class="nav-link" href="login">Sign In</a>
            </li>
            <li class="nav-item">
                <a class="btn btn-primary btn-my" href="/registration" role="button">Sign Up</a>
            </li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <li class="nav-item">
                <a class="btn btn-primary btn-my" href="/logout" role="button">Log out</a>
            </li>
        </sec:authorize>
    </ul>
</nav>
<div class="page-header header-my">
    <h1>Railroad</h1>
</div>
<div>
    <hr class="hr-primary"> </div>
<div class="p-3 gradient-overlay bg-secondary"> </div>

<div class="py-5" id="root">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="card-body p-5 card-my">
                    <h3 class="pb-3">Enter your detail</h3>
                    <form>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-12 my-select">
                                    <label>First name</label>
                                    <input class="form-control" placeholder="First name" @keydown="clearAlert" name="firstName" v-model="firstName" type="text" required>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 my-select">
                                    <label>Last name</label>
                                    <input class="form-control" placeholder="Last name" @keydown="clearAlert" name="lastName" v-model="lastName" type="text" required>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 my-select">
                                    <label>Birth Date</label>
                                    <input type='date' class="form-control" @keydown="clearAlert" v-model="birthDate" required/>
                                </div>
                            </div>
                            <div class="form-group" id="buttons">
                                <div class="py-3">
                                    <div class="py-4">
                                        <input id="rideId" value="${rideIdJSP}" hidden>
                                        <button  class="btn btn-primary btn-my" @click="buyTicket" type="button">Confirm</button>
                                    </div>
                                </div>
                            </div>
                            <div :class="[alertClass, success ? alertSuccess : alertDanger ]" role="alert" v-show="alertShow">
                                <span v-text="alert"/>
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
<spring:url value="/resources/js/vue.js" var="vue"/>
<spring:url value="/resources/js/passenger.js" var="passenger"/>
<script src="${jQuery}"></script>
<script src="${bootstrapJS}"></script>
<script src="${vue}"></script>
<script src="${passenger}"></script>
</body>
</html>
