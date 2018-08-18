var app = new Vue({
    el: "#root",

    data: {
        alert : "",
        alertClass: "alert",
        alertSuccess: "alert-success",
        alertDanger: "alert-danger",
        success: true,
        station: "",
        distance: "",
        newStation: "",
        datalist: [],
        stations: []
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
            app.$data.stations = data;
        });
    },

    methods: {
        createStation(){
            if (this.station === "" || this.distance === "" || this.newStation === ""){
                this.success = false;
                this.alert = "All fields are required";
            } else {
                var stationGraphDto = {station : this.station, distance : this.distance, newStation : this.newStation};

                $.ajax({
                    headers : {
                        'Accept' : 'application/json',
                        'Content-Type' : 'application/json'
                    },
                    url : "/admin/createStation",
                    contentType : 'application/json',
                    data : JSON.stringify(stationGraphDto),
                    type : 'POST',
                    success : function () {
                        app.$data.success = true;
                        app.$data.alert = "Station was created";

                        $.get("/admin/getAllStations", function (data) {
                            Object.keys(data).forEach(function (key) {
                                app.$data.datalist.push(data[key].name);
                            });
                            app.$data.stations = data;
                        });
                    },
                    error : function (xhr, status, error) {
                        app.$data.success = false;
                        app.$data.alert = "Station wasn't created due to error";
                    }
                });
            }
        },

        deleteStation(station){
            $.ajax({
                headers : {
                    'Accept' : 'application/json',
                    'Content-Type' : 'application/json'
                },
                url : "/admin/deleteStation",
                contentType : 'application/json',
                data : JSON.stringify(station),
                type : 'POST',
                success : function () {
                    app.$data.success = true;
                    app.$data.alert = "Station was deleted";
                    var index = app.$data.stations.indexOf(station);
                    app.$data.stations.splice(index, 1);
                },
                error : function (xhr, status, error) {
                    app.$data.success = false;
                    app.$data.alert = "Station wasn't deleted because it already used in route";
                }
            });
        },

        clearAlert(){
            this.alert = "";
        }
    }
});
