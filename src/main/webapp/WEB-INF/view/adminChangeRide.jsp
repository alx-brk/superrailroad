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
                <div class="card-body p-5 card-my">
                    <h3 class="pb-3">Change departure date</h3>
                    <form>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-3 my-select">
                                    <label>Ride</label>
                                    <input class="form-control" id="rideId" type="text" value="${rideIdJSP.rideId}" disabled>
                                </div>
                                <div class="col-md-3">
                                    <label>Route</label>
                                    <input class="form-control" id="routeId" type="text" value="${rideIdJSP.route}" disabled>
                                </div>
                                <div class="col-md-6 my-select">
                                    <label>Departure Date and Time</label>
                                    <input type='datetime-local' class="form-control" id="departure" value="${rideIdJSP.departure}"/>
                                </div>
                            </div>
                            <div class="form-group" id="buttons">
                                <div class="py-3">
                                    <div class="py-4">
                                        <button class="btn btn-primary btn-my" type="button" onclick="changeRide()">Change Date</button>
                                    </div>
                                </div>
                            </div>
                            <div class="alert alert-success alert-my" role="alert" id="alert-success">
                                Ride was changed
                            </div>
                            <div class="alert alert-danger alert-my" role="alert" id="alert-danger">
                                Ride wasn't changed due to error
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
