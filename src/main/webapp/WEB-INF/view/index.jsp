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
    <title>Home Page</title>
</head>
<body>
    <nav class="navbar navbar-toggleable-md sticky-top navbar-my navbar-inverse">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="btn btn-primary btn-my" href="/admin/createStation" role="button">Admin</a>
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
                            <a href="#" class="active nav-link">
                                <i class="fa fa-home fa-home"></i>&nbsp;Find train</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Find Station</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="card-body p-5 card-my">
                        <h3 class="pb-3">Find your train</h3>
                        <form>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-5 my-select">
                                        <label>From</label>
                                        <input placeholder="From" list="listFrom" class="form-control">
                                        <datalist id="listFrom">
                                            <c:forEach items="${stationJSPList}" var="station">
                                                <option value="${station.name}"/>
                                            </c:forEach>
                                        </datalist>
                                    </div>
                                    <div class="col-md-2"> </div>
                                    <div class="col-md-5 my-select">
                                        <label>To</label>
                                        <input placeholder="To" list="listTo" class="form-control">
                                        <datalist id="listTo">
                                            <c:forEach items="${stationJSPList}" var="station">
                                                <option value="${station.name}"/>
                                            </c:forEach>
                                        </datalist>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="py-3">
                                        <label>Date and Time</label>
                                        <input type='datetime-local' class="form-control" />
                                        <div class="py-4">
                                            <button class="btn btn-primary btn-my" type="button">Search</button>
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
    <spring:url value="/resources/js/railroad.js" var="railroadJS"/>
    <script src="${jQuery}"></script>
    <script src="${bootstrapJS}"></script>
    <script src="${railroadJS}"></script>
</body>
</html>
