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
                        <a href="/admin/createRide" class="active nav-link">
                            <i class="fa fa-home fa-home"></i>&nbsp;Schedule ride</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/trainInfo">Train info</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="row" id="root">
            <div class="col-md-12">
                <div class="card-body p-5 card-my">
                    <h3 class="pb-3">Add new train</h3>
                    <form>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-5 my-select">
                                    <label>Route</label>
                                    <input placeholder="Route" list="listRoute" class="form-control" v-model="routeId">
                                    <datalist id="listRoute">
                                        <option v-for="option in datalist" :value="option"/>
                                    </datalist>
                                </div>
                                <div class="col-md-2">
                                </div>
                                <div class="col-md-5 my-select">
                                    <label>Departure Date and Time</label>
                                    <input type='datetime-local' class="form-control" v-model="departure" />
                                </div>
                            </div>
                            <div class="form-group" id="buttons">
                                <div class="py-3">
                                    <div class="py-4">
                                        <button class="btn btn-primary btn-my" type="button" @click="createRide">Schedule</button>
                                    </div>
                                </div>
                            </div>
                            <div :class="[alertClass, success ? alertSuccess : alertDanger ]" role="alert" v-show="alertShow">
                                <span v-text="alert"/>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <table class="table table-hover">
                                        <thead>
                                        <th scope="col">ID</th>
                                        <th scope="col">Capacity</th>
                                        <th scope="col">Price for km</th>
                                        <th scope="col">Speed m/s</th>
                                        <th scope="col">Station</th>
                                        </thead>
                                        <tbody>
                                            <tr v-for="route in routes" @click="chooseRoute(route)" :class="{'tr-chosen': route.chosen}">
                                                <th scope="row" v-text="route.routeId"/>
                                                <td v-text="route.trainDto.capacity"/>
                                                <td v-text="route.trainDto.priceForKm"/>
                                                <td v-text="route.trainDto.speed"/>
                                                <td>
                                                    <ul v-for="station in route.routeHasStationDtoList">
                                                        <li v-text="station.stationDto.name"/>
                                                    </ul>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
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
<spring:url value="/resources/js/vue.js" var="vue"/>
<spring:url value="/resources/js/createRide.js" var="createRide"/>
<script src="${jQuery}"></script>
<script src="${bootstrapJS}"></script>
<script src="${vue}"></script>
<script src="${createRide}"></script>
</body>
</html>
