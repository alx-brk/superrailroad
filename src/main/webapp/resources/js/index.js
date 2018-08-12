var app = new Vue({
    el: "#root",

    data: {
        error : "",
        stationFrom: "",
        stationTo: "",
        date: "",
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
            if (this.stationFrom === "" || this.stationTo === "" || this.date === ""){
                this.error = "All fields are required";
            } else {
                var searchDto = {stationFrom : this.stationFrom, stationTo : this.stationTo, date : this.date};

                $.ajax({
                    headers : {
                        'Accept' : 'application/json',
                        'Content-Type' : 'application/json'
                    },
                    url : "/search",
                    contentType : 'application/json',
                    data : JSON.stringify(searchDto),
                    type : 'POST',
                    success : function (data) {
                        app.$data.entries = data;
                    }
                });
            }
        },

        clearError(){
            this.error = "";
        }
    }
});
