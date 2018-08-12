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
        newStation: ""
    },

    computed: {
        alertShow(){
            return (this.alert !== "");
        }
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
                    },
                    error : function (xhr, status, error) {
                        app.$data.success = false;
                        app.$data.alert = "Station wasn't created due to error";
                    }
                });
            }
        },

        clearAlert(){
            this.alert = "";
        }
    }
});
