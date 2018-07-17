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
                        <a class="nav-link" href="/admin/createTrain">Add train</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/createRide">Schedule ride</a>
                    </li>
                    <li class="nav-item">
                        <a href="/admin/trainInfo" class="active nav-link">
                            <i class="fa fa-home fa-home"></i>&nbsp;Train info</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card-body p-5 card-my">
                    <h3 class="pb-3">Train Information</h3>
                    <div class="row">
                        <div class="col-md-12">
                            <table class="table table-hover">
                                <thead>
                                <th scope="col">Ride ID</th>
                                <th scope="col">Departure station</th>
                                <th scope="col">Departure Date and Time</th>
                                <th scope="col">Arrival station</th>
                                <th scope="col">Arrival Date and Time</th>
                                <th scope="col">Passengers</th>
                                </thead>
                                <tbody>
                                <c:forEach items="${rideInfoJSP}" var="ride">
                                    <tr>
                                        <th scope="row">${ride.rideId}</th>
                                        <td>${ride.departureStation}</td>
                                        <td>${ride.departureDate}</td>
                                        <td>${ride.arrivalStation}</td>
                                        <td>${ride.arrivalDate}</td>
                                        <td>
                                            <ol>
                                                <c:forEach items="${ride.passengers}" var="passenger">
                                                    <li>${passenger.firstName} ${passenger.lastName} ${passenger.birthDate}</li>
                                                </c:forEach>
                                            </ol>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<spring:url value="/resources/js/jquery-3.3.1.min.js" var="jQuery"/>
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS"/>
<spring:url value="/resources/js/railroad.js" var="railroadJS"/>
<spring:url value="/resources/js/create_ride_extra.js" var="railroadExtraJS"/>
<script src="${jQuery}"></script>
<script src="${bootstrapJS}"></script>
<script src="${railroadJS}"></script>
<script src="${railroadExtraJS}"></script>
</body>
</html>
