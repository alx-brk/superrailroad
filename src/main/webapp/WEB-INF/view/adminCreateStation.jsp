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

<div class="py-5" id="root">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a href="/admin/createStation" class="active nav-link">
                            <i class="fa fa-home fa-home"></i>&nbsp;Add station</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/createTrain">Add train</a>
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
                    <h3 class="pb-3">Add new station</h3>
                    <form>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4 my-select">
                                    <label>Station</label>
                                    <input placeholder="Station" list="listStation" @keydown="clearAlert" class="form-control" v-model="station" required>
                                    <datalist id="listStation">
                                        <option v-for="option in datalist" :value="option"/>
                                    </datalist>
                                </div>
                                <div class="col-md-4">
                                    <label>Distance</label>
                                    <input class="form-control" type="number" @keydown="clearAlert" v-model="distance" required>
                                </div>
                                <div class="col-md-4 my-select">
                                    <label>New station</label>
                                    <input class="form-control" placeholder="New station" @keydown="clearAlert" v-model="newStation" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="py-3">
                                    <div class="py-4">
                                        <button class="btn btn-primary btn-my" type="button" @click="createStation">Add station</button>
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
                                        <th scope="col">Station ID</th>
                                        <th scope="col">Station Name</th>
                                        <th scope="col"></th>
                                        </thead>
                                        <tbody>
                                        <tr v-for="station in stations">
                                            <th scope="row" v-text="station.stationId"/>
                                            <td v-text="station.name"/>
                                            <td><button class="btn btn-primary btn-my" type="button" @click="deleteStation(station)">Delete</button></td>
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
<spring:url value="/resources/js/createStation.js" var="createStation"/>
<script src="${jQuery}"></script>
<script src="${bootstrapJS}"></script>
<script src="${vue}"></script>
<script src="${createStation}"></script>
</body>
</html>
