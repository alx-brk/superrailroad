function createStation() {
    if ($("#station").val().trim() != '' && $("#distance").val().trim() != '' && $("#newStation").val().trim() != ''){
        var stationGraphDto = {
            station : $("#station").val(),
            distance : $("#distance").val(),
            newStation : $("#newStation").val()
        };

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
                $("#alert-success").fadeIn().delay(1000).fadeOut;
            },
            error : function (xhr, status, error) {
                $("#alert-danger").append("\n" + error)
                $("#alert-danger").fadeIn().delay(1000).fadeOut;
            }
        });
    }

}

function addStationList() {
    var station = $("input[id^='station']").last();
    var lastId = parseInt(station.attr('id').substring(7)) + 1;
    station.prop('disabled', true);
    if (station != ''){
        var stationDto = {
            name : station.val().trim()
        };

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
                var html = '<div class="row">' +
                    '<div class="col-md-12 my-select py-3">' +
                    '<input placeholder="Station" list="listStation' + lastId + '" class="form-control" id="station' + lastId + '">' +
                    '<datalist id="listStation'+ lastId +'">' +
                    '</datalist>' +
                    '</div>' +
                    '</div>';

                $("#buttons").before(html);
                Object.keys(data).forEach(function (key) {
                    if (data[key].name != station.val()) {
                        $("#listStation" + lastId).append('<option value="' + data[key].name + '"/>');
                    }
                });
            }
        });
    }
}

function performSearch() {
    var stationFrom = $("#stationFrom").val().trim();
    var stationTo = $("#stationTo").val().trim();
    var date = $("#date").val().trim();

    if (stationFrom != '' && stationTo != '' && date != ''){
        var searchDto = {stationFrom : stationFrom, stationTo : stationTo, date : date};

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
                var resultContainer = $("#resultContainer");
                var tbody = resultContainer.find("tbody");
                tbody.empty();

                Object.keys(data).forEach(function (key) {
                    var html = '<tr>' +
                            '<th scope="row">'+ data[key].trainId +'</th>' +
                            '<td>'+ data[key].departure +'</td>' +
                            '<td>'+ data[key].arrival +'</td>' +
                            '<td>'+ data[key].capacity +'</td>' +
                            '<td>'+ data[key].ticketsLeft +'</td>' +
                            '<td>'+ data[key].price +'</td>' +
                            '<td><a class="btn btn-primary btn-my" href="/search/'+ data[key].rideId +'" role="button">Buy</a></td>' +
                            '</tr>';
                    tbody.append(html);
                });

                resultContainer.fadeIn();
            }
        });
    }
}

function performSearchByStation() {
    var station = $("#station").val().trim();

    if (station != ''){
        var stationDto = {name : station};

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
                var resultContainer = $("#resultContainer");
                var tbody = resultContainer.find("tbody");
                tbody.empty();

                Object.keys(data).forEach(function (key) {
                    var html = '<tr>' +
                        '<th scope="row">'+ data[key].rideId +'</th>' +
                        '<td>'+ data[key].datetime +'</td>' +
                        '<td>'+
                        '<table class="table">' +
                        '<thead>' +
                        '<th scope="col">Station</th>' +
                        '<th scope="col">Time</th>' +
                        '</thead>' +
                        '<tbody>'

                    Object.keys(data[key].stations).forEach(function (value) {
                        var innerTable = '<tr>' +
                                        '<td>'+ data[key].stations[value].station +'</td>' +
                                        '<td>'+ data[key].stations[value].dateTime +'</td>' +
                                        '</tr>';
                        html += innerTable;
                    })

                    html += '</tbody>' +
                            '</table>' +
                            '</td>' +
                            '</tr>'

                    tbody.append(html);
                });

                resultContainer.fadeIn();
            }
        });
    }
}

function createTrain() {
    var capacity = $("#capacity").val().trim();
    var price = $("#price").val().trim();
    var speed = $("#speed").val().trim();

    if (capacity != '' && price != '' && speed != '' && $("#station1").val().trim() != '' && $("#station2").val().trim() != ''){
        var train = {capacity : capacity, priceForKm : price, speed : speed};
        var routeHasStationList = [];

        var stationElems = $("input[id^='station']");
        stationElems.each(function (index) {
           var stationName = $(this).val().trim();
           var order = index + 1;

           var routeHasStation = {stationOrder : order,
                                stationDto : {name : stationName}};
           routeHasStationList.push(routeHasStation);
        });

        var route = {trainDto : train, routeHasStationDtoList : routeHasStationList}

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
                $("#alert-success").fadeIn().delay(1000).fadeOut;
            },
            error : function (xhr, status, error) {
                $("#alert-danger").append("\n" + error)
                $("#alert-danger").fadeIn().delay(1000).fadeOut;
            }
        });
    }
}

function createRide(){
    var route = $("#route").val().trim();
    var departure = $("#departure").val().trim();

    if (route != '' && departure != ''){
        var ride = {route : route, departure : departure};

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
                $("#alert-success").fadeIn().delay(1000).fadeOut;
            },
            error : function (xhr, status, error) {
                $("#alert-danger").append("\n" + error)
                $("#alert-danger").fadeIn().delay(1000).fadeOut;
            }
        });
    }
}

function buyTicket() {
    var firstName = $("#firstName").val().trim();
    var lastName = $("#lastName").val().trim();
    var birthDate = $("#birthDate").val().trim();
    var rideId = $("#rideId").val().trim();

    if (firstName != '' && lastName != '' && birthDate != '' && rideId != '') {
        var passengerDto = {firstName : firstName, lastName : lastName, birthDate : birthDate, rideId : rideId};

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
                $("#alert-success").fadeIn().delay(1000).fadeOut;
            },
            error : function (xhr, status, error) {
                $("#alert-danger").append("\n" + error)
                $("#alert-danger").fadeIn().delay(1000).fadeOut;
            }
        });
    }
}

function changeRide() {
    var rideId = $("#rideId").val().trim();
    var departure = $("#departure").val().trim();
    var route = $("#routeId").val().trim();

    if (rideId != '' && departure != '' && route != ''){
        var rideDto = {rideId : rideId, route: route, departure: departure};

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
                $("#alert-success").fadeIn().delay(1000).fadeOut;
            },
            error : function (xhr, status, error) {
                $("#alert-danger").append("\n" + error)
                $("#alert-danger").fadeIn().delay(1000).fadeOut;
            }
        });
    }
}