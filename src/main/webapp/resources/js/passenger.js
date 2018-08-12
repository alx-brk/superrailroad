var app = new Vue({
    el: "#root",

    data: {
        alert : "",
        alertClass: "alert",
        alertSuccess: "alert-success",
        alertDanger: "alert-danger",
        success: true,
        firstName: "",
        lastName: "",
        birthDate: "",
        rideId: ""
    },

    computed: {
        alertShow(){
            return (this.alert !== "");
        }
    },

    mounted: function(){
      this.rideId = $("#rideId").val();
    },

    methods: {
        buyTicket(){
            if (this.firstName === "" || this.lastName === "" || this.birthDate === "" || this.rideId === ""){
                this.success = false;
                this.alert = "All fields are required";
            } else {
                var passengerDto = {firstName : this.firstName, lastName : this.lastName, birthDate : this.birthDate, rideId : this.rideId};

                $.ajax({
                    headers : {
                        'Accept' : 'application/json',
                        'Content-Type' : 'application/json'
                    },
                    url : "/buy",
                    contentType : 'application/json',
                    data : JSON.stringify(passengerDto),
                    type : 'POST',
                    success : function () {
                        app.$data.success = true;
                        app.$data.alert = "You bought the ticket";
                    },
                    error : function (xhr, status, error) {
                        app.$data.success = false;
                        app.$data.alert = "You can't buy ticket because such passenger already bought ticket to this train";
                    }
                });
            }
        },

        clearAlert(){
            this.alert = "";
        }
    }
});
