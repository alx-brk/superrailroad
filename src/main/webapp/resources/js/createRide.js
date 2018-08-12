var app = new Vue({
    el: "#root",

    data: {
        alert : "",
        alertClass: "alert",
        alertSuccess: "alert-success",
        alertDanger: "alert-danger",
        success: true,

        routeId: "",
        departure: "",
        datalist: [],
        routes: []
    },

    computed: {
        alertShow(){
            return (this.alert !== "");
        }
    },

    mounted: function(){
        $.get("/admin/getAllRoutes", function (data) {
            Object.keys(data).forEach(function (key) {
                app.$data.datalist.push(data[key].routeId);
            });

            app.$data.routes = data;
            Object.keys(app.$data.routes).forEach(function (key) {
                app.$data.routes[key].chosen = false;
            });
        });
    },

    methods: {
        chooseRoute(route){
            this.routeId = route.routeId;
            Object.keys(app.$data.routes).forEach(function (key) {
                app.$data.routes[key].chosen = false;
            });
            route.chosen = true;
        },

        createRide(){
            if (this.routeId === "" || this.departure === ""){
                this.success = false;
                this.alert = "All fields are required";
            }  else {
                var ride = {route : this.routeId, departure : this.departure};

                $.ajax({
                    headers : {
                        'Accept' : 'application/json',
                        'Content-Type' : 'application/json'
                    },
                    url : "/admin/createRide",
                    contentType : 'application/json',
                    data : JSON.stringify(ride),
                    type : 'POST',
                    success : function () {
                        app.$data.success = true;
                        app.$data.alert = "Ride was created";
                    },
                    error : function (xhr, status, error) {
                        app.$data.success = false;
                        app.$data.alert = "Ride wasn't created due to error";
                    }
                });
            }
        },

        clearAlert(){
            this.alert = "";
        }
    }
});
