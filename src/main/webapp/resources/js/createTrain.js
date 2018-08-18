var app = new Vue({
    el: "#root",

    data: {
        alert : "",
        alertClass: "alert",
        alertSuccess: "alert-success",
        alertDanger: "alert-danger",
        success: true,

        capacity: "",
        price: "",
        speed: "",
        station: "",
        datalist: [],
        stations: [],
        routes: []
    },

    computed: {
        alertShow(){
            return (this.alert !== "");
        }
    },

    mounted: function(){
        $.get("/admin/getAllStations", function (data) {
           Object.keys(data).forEach(function (key) {
               app.$data.datalist.push(data[key].name);
           });
        });

        $.get("/admin/getAllRoutes", function (data) {
            app.$data.routes = data;
        });
    },

    methods: {
        deleteRoute(route){
            $.ajax({
                headers : {
                    'Accept' : 'application/json',
                    'Content-Type' : 'application/json'
                },
                url : "/admin/deleteRoute",
                contentType : 'application/json',
                data : JSON.stringify(route),
                type : 'POST',
                success : function () {
                    app.$data.success = true;
                    app.$data.alert = "Route was deleted";
                    var index = app.$data.routes.indexOf(route);
                    app.$data.routes.splice(index, 1);
                },
                error : function (xhr, status, error) {
                    app.$data.success = false;
                    app.$data.alert = "Route wasn't deleted because it already used in ride";
                }
            });
        },

        addStation(){
            if (this.station === ""){
                app.$data.success = false;
                app.$data.alert = "Choose some station";
            } else {
                this.stations.push(this.station);

                var stationDto = {name : this.station};
                this.station = "";

                $.ajax({
                    headers : {
                        'Accept' : 'application/json',
                        'Content-Type' : 'application/json'
                    },
                    url : "/admin/getConnectedStations",
                    contentType : 'application/json',
                    data : JSON.stringify(stationDto),
                    type : 'POST',
                    success : function (data) {
                        app.$data.datalist = [];
                        Object.keys(data).forEach(function (key) {
                            if (!app.$data.stations.includes(data[key].name)) {
                                app.$data.datalist.push(data[key].name);
                            }
                        });
                    }
                });
            }
        },

        createTrain(){
            if (this.capacity === "" || this.price === "" || this.speed === ""){
                this.success = false;
                this.alert = "All fields are required";
            } else if (this.stations.length < 2){
                this.success = false;
                this.alert = "Route must have at least two stations";
            } else {
                var train = {capacity : this.capacity, priceForKm : this.price, speed : this.speed};
                var routeHasStationList = [];

                Object.keys(this.stations).forEach(function (index) {
                    var routeHasStation = {stationOrder : index + 1, stationDto: {name: app.$data.stations[index]}};
                    routeHasStationList.push(routeHasStation);
                });
                var route = {trainDto : train, routeHasStationDtoList : routeHasStationList};

                $.ajax({
                    headers : {
                        'Accept' : 'application/json',
                        'Content-Type' : 'application/json'
                    },
                    url : "/admin/createTrain",
                    contentType : 'application/json',
                    data : JSON.stringify(route),
                    type : 'POST',
                    success : function () {
                        app.$data.success = true;
                        app.$data.alert = "Train was created";
                        app.$data.stations = [];

                        $.get("/admin/getAllStations", function (data) {
                            Object.keys(data).forEach(function (key) {
                                app.$data.datalist.push(data[key].name);
                            });
                        });

                        $.get("/admin/getAllRoutes", function (data) {
                            app.$data.routes = data;
                        });
                    },
                    error : function (xhr, status, error) {
                        app.$data.success = false;
                        app.$data.alert = "Train wasn't created due to error";
                    }
                });
            }
        },

        clearAlert(){
            this.alert = "";
        }
    }
});
