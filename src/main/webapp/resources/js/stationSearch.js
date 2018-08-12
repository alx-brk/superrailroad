var app = new Vue({
    el: "#root",

    data: {
        error : "",
        station: "",
        entries: []
    },

    computed: {
        visible(){
            return (this.entries.length > 0);
        },

        errorShow(){
            return (this.error !== "");
        }
    },

    methods: {
        search(){
            if (this.station === ""){
                this.error = "Choose station first";
            } else {
                var stationDto = {name : this.station};

                $.ajax({
                    headers : {
                        'Accept' : 'application/json',
                        'Content-Type' : 'application/json'
                    },
                    url : "/stationInfo",
                    contentType : 'application/json',
                    data : JSON.stringify(stationDto),
                    type : 'POST',
                    success : function (data) {
                        if (data.length > 0){
                            app.$data.entries = data;
                        } else {
                            app.$data.error = "No rides found";
                        }
                    }
                });
            }
        },

        clearError(){
            this.error = "";
        }
    }
})
