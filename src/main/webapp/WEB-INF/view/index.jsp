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
    <title>Home Page</title>
</head>
<body>
    <nav class="navbar navbar-toggleable-md sticky-top navbar-my navbar-inverse">

        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="btn btn-primary btn-my" href="/admin/createStation" role="button">Admin</a>
                </li>
            </ul>
        </sec:authorize>

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
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                            <a href="/" class="active nav-link">
                                <i class="fa fa-home fa-home"></i>&nbsp;Find Train</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/stationSearch">Find Station</a>
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
                                        <input placeholder="From" list="listFrom" class="form-control" @keydown="clearError" v-model="stationFrom" required>
                                        <datalist id="listFrom">
                                            <c:forEach items="${stationJSPList}" var="station">
                                                <option value="${station.name}"/>
                                            </c:forEach>
                                        </datalist>
                                    </div>
                                    <div class="col-md-2"> </div>
                                    <div class="col-md-5 my-select">
                                        <label>To</label>
                                        <input placeholder="To" list="listTo" class="form-control" @keydown="clearError"  v-model="stationTo" required>
                                        <datalist id="listTo">
                                            <c:forEach items="${stationJSPList}" var="station">
                                                <option value="${station.name}"/>
                                            </c:forEach>
                                        </datalist>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="py-3">
                                        <label>Date</label>
                                        <input type='date' class="form-control" @keydown="clearError"  v-model="date" required/>
                                        <div class="py-4">
                                            <button  class="btn btn-primary btn-my" @click="search" type="button">Search</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="alert alert-danger" role="alert" v-show="errorShow">
                                    <span v-text="error"/>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="row" v-show="visible">
                <div class="col-md-12">
                    <div class="card-body p-5 card-my">
                        <table class="table table-hover">
                            <thead>
                                <th scope="col">Train number</th>
                                <th scope="col">Departure</th>
                                <th scope="col">Arrival</th>
                                <th scope="col">Capacity</th>
                                <th scope="col">Tickets left</th>
                                <th scope="col">Price</th>
                                <th scope="col"></th>
                            </thead>
                            <tbody>
                                <tr v-for="entry in entries">
                                    <th scope="row" v-text="entry.trainId"/>
                                    <td v-text="entry.departure"/>
                                    <td v-text="entry.arrival"/>
                                    <td v-text="entry.capacity"/>
                                    <td v-text="entry.ticketsLeft"/>
                                    <td v-text="entry.price"/>
                                    <td><a class="btn btn-primary btn-my" :href="'/search/' + entry.rideId" role="button">Buy</a></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <spring:url value="/resources/js/jquery-3.3.1.min.js" var="jQuery"/>
    <spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS"/>
    <spring:url value="/resources/js/vue.js" var="vue"/>
    <spring:url value="/resources/js/index.js" var="index"/>
    <script src="${jQuery}"></script>
    <script src="${bootstrapJS}"></script>
    <script src="${vue}"></script>
    <script src="${index}"></script>
</body>
</html>
