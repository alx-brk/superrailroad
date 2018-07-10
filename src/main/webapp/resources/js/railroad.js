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
                    $("#listStation"+lastId).append('<option value="' + data[key].name + '"/>');
                });
            }
        });
    }
}

function createTrain() {
    var capacity = $("#capacity").val().trim();
    var price = $("#price").val().trim();
    var speed = $("#speed").val().trim();

    if (capacity != '' && price != '' && speed != ''){
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