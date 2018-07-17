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
    <ul class="navbar-nav ml-auto">
        <li class="nav-item">
            <a class="btn btn-primary btn-my" href="/logout" role="button">Log out</a>
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
                        <a class="nav-link" href="/admin/createStation">Add station</a>
                    </li>
                    <li class="nav-item">
                        <a href="/admin/createTrain" class="active nav-link">
                            <i class="fa fa-home fa-home"></i>&nbsp;Add train</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/createRide">Schedule ride</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/trainInfo">Train info</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card-body p-5 card-my">
                    <h3 class="pb-3">Add new train</h3>
                    <form>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4 my-select">
                                    <label>Capacity</label>
                                    <input placeholder="Number of passangers" type="number" step="1" class="form-control" id="capacity">
                                </div>
                                <div class="col-md-4">
                                    <label>Price for km</label>
                                    <input placeholder="Price for km" type="number" step="0.01" class="form-control" id="price">
                                </div>
                                <div class="col-md-4 my-select">
                                    <label>Speed m/s</label>
                                    <input placeholder="Speed m/s" type="number" step="1" class="form-control" id="speed">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 my-select py-3">
                                    <label>Start station</label>
                                    <input placeholder="Station" list="listStation1" class="form-control" id="station1">
                                    <datalist id="listStation1">
                                        <c:forEach items="${stationJSPList}" var="station">
                                            <option value="${station.name}"/>
                                        </c:forEach>
                                    </datalist>
                                </div>
                            </div>
                            <div class="form-group" id="buttons">
                                <div class="py-3">
                                    <div class="py-4">
                                        <button class="btn btn-primary btn-my" type="button" onclick="addStationList()">Add station</button>
                                        <button class="btn btn-primary btn-my" type="button" onclick="createTrain()">Create train</button>
                                    </div>
                                </div>
                            </div>
                            <div class="alert alert-success alert-my" role="alert" id="alert-success">
                                Train was created
                            </div>
                            <div class="alert alert-danger alert-my" role="alert" id="alert-danger">
                                Train wasn't created due to error
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
