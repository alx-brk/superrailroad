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
        <div class="row" id="root">
            <div class="col-md-12">
                <div class="card-body p-5 card-my">
                    <h3 class="pb-3">Add new train</h3>
                    <form>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4 my-select">
                                    <label>Capacity</label>
                                    <input placeholder="Number of passangers" type="number" step="1" @keydown="clearAlert" class="form-control" v-model="capacity" required>
                                </div>
                                <div class="col-md-4">
                                    <label>Price for km</label>
                                    <input placeholder="Price for km" type="number" step="0.01" @keydown="clearAlert" class="form-control" v-model="price" required>
                                </div>
                                <div class="col-md-4 my-select">
                                    <label>Speed m/s</label>
                                    <input placeholder="Speed m/s" type="number" step="1" @keydown="clearAlert" class="form-control" v-model="speed" required>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 my-select py-3">
                                    <label>Start station</label>
                                    <input placeholder="Station" list="listStation" @keydown="clearAlert" class="form-control" v-model="station" required>
                                    <datalist id="listStation">
                                        <option v-for="option in datalist" :value="option"/>
                                    </datalist>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 py-3">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item" v-for="station in stations" v-text="station"/>
                                    </ul>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="py-3">
                                    <div class="py-4">
                                        <button class="btn btn-primary btn-my" type="button" @click="addStation">Add station</button>
                                        <button class="btn btn-primary btn-my" type="button" @click="createTrain">Create train</button>
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
                                        <th></th>
                                        </thead>
                                        <tbody>
                                        <tr v-for="route in routes">
                                            <th scope="row" v-text="route.routeId"/>
                                            <td v-text="route.trainDto.capacity"/>
                                            <td v-text="route.trainDto.priceForKm"/>
                                            <td v-text="route.trainDto.speed"/>
                                            <td>
                                                <ul v-for="station in route.routeHasStationDtoList">
                                                    <li v-text="station.stationDto.name"/>
                                                </ul>
                                            </td>
                                            <td><button class="btn btn-primary btn-my" type="button" @click="deleteRoute(route)">Delete</button></td>
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
<spring:url value="/resources/js/createTrain.js" var="createTrain"/>
<script src="${jQuery}"></script>
<script src="${bootstrapJS}"></script>
<script src="${vue}"></script>
<script src="${createTrain}"></script>
</body>
</html>
