var app = new Vue({
    el: "#root",

    data: {
        alert : "",
        alertClass: "alert",
        alertSuccess: "alert-success",
        alertDanger: "alert-danger",
        success: true,

        rideId: "",
        routeId: "",
        departure: ""
    },

    computed: {
        alertShow(){
            return (this.alert !== "");
        }
    },

    mounted: function(){
        this.rideId = $("#rideId").val();
        this.routeId = $("#routeId").val();
    },

    methods: {
        changeRide(){
            if (this.rideId === "" || this.routeId === "" || this.departure === ""){
                this.success = false;
                this.alert = "Set new date and time";
            }  else {
                var rideDto = {rideId : this.rideId, route: this.routeId, departure: this.departure};

                $.ajax({
                    headers : {
                        'Accept' : 'application/json',
                        'Content-Type' : 'application/json'
                    },
                    url : "/admin/confirmChangeRide",
                    contentType : 'application/json',
                    data : JSON.stringify(rideDto),
                    type : 'POST',
                    success : function () {
                        app.$data.success = true;
                        app.$data.alert = "Ride was changed";
                    },
                    error : function (xhr, status, error) {
                        app.$data.success = false;
                        app.$data.alert = "Ride wasn't changed due to error";
                    }
                });
            }
        },

        clearAlert(){
            this.alert = "";
        }
    }
});
