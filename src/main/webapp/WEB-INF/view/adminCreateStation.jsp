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
            <a class="btn btn-primary btn-my" href="#" role="button">Home</a>
        </li>
    </ul>
    <ul class="navbar-nav ml-auto">
        <li class="nav-item">
            <a class="nav-link" href="#">Sign In</a>
        </li>
        <li class="nav-item">
            <a class="btn btn-primary btn-my" href="#" role="button">Sign Up</a>
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
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a href="/adminCreateStation" class="active nav-link">
                            <i class="fa fa-home fa-home"></i>&nbsp;Add station</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/adminCreateTrain">Add train</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Schedule ride</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Train info</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card-body p-5 card-my">
                    <h3 class="pb-3">Add new station</h3>
                    <form>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4 my-select">
                                    <label>Station</label>
                                    <input placeholder="Station" list="listStation" class="form-control" id="station">
                                    <datalist id="listStation">
                                        <c:forEach items="${stationJSPList}" var="station">
                                            <option value="${station.name}"/>
                                        </c:forEach>
                                    </datalist>
                                </div>
                                <div class="col-md-4">
                                    <label>Distance</label>
                                    <input class="form-control" type="number" id="distance">
                                </div>
                                <div class="col-md-4 my-select">
                                    <label>New station</label>
                                    <input class="form-control" placeholder="New station" id="newStation">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="py-3">
                                    <div class="py-4">
                                        <button class="btn btn-primary btn-my" type="button" onclick="createStation()">Add station</button>
                                    </div>
                                </div>
                            </div>
                            <div class="alert alert-success alert-my" role="alert" id="alert-success">
                                Station was created
                            </div>
                            <div class="alert alert-danger alert-my" role="alert" id="alert-danger">
                                Station wasn't created due to error
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
<spring:url value="/resources/js/railroad.js" var="railroadJS"/>
<script src="${jQuery}"></script>
<script src="${bootstrapJS}"></script>
<script src="${railroadJS}"></script>
</body>
</html>
