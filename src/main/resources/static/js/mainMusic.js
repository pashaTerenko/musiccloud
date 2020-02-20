var audioTrack = document.getElementById("audiotrack");
var PLPageId = 1;
var PLPageSize = 0;
var AllPageId = 0;
var ALLPageSize = 0;
function GenerateTable(data) {

    var customers = new Array();
    customers.push(["Name", "Action", "Time"]);

    for (var i = 0; i < data.length; i++) {
        customers.push([data[i].name, data[i].uuid, data[i].aLong]);
    }

    var table = document.createElement("TABLE");
    table.className = " table table-dark";
    table.border = "1";

    //Get the count of columns.
    var columnCount = customers[0].length;


    //Add the header row.
    var row = table.insertRow(-1);
    for (var i = 0; i < columnCount; i++) {
        var headerCell = document.createElement("TH");
        headerCell.innerHTML = customers[0][i];
        row.appendChild(headerCell);
    }

    //Add the data rows.
    for (var i = 1; i < customers.length; i++) {
        row = table.insertRow(-1);
        for (var j = 0; j < columnCount; j++) {
            var cell = row.insertCell(-1);
            if (j != 1) {
                cell.innerHTML = customers[i][j];
            } else {

                cell.innerHTML = '<i  type="button"  id="' + i + ' " class="fas fa-play fa-lg" value="1" ></i> ' + '<i onclick="addTopl(this.id)"class="fas fa-plus fa-lg" type="button" id="' + customers[i][1] + ' " value="add to playlist" ></i>';


            }

        }
    }
    playButton.addEventListener("click", player, false);

    var dvTable = document.getElementById("dvTable");

    dvTable.innerHTML = "";
    dvTable.appendChild(table);
    loadPagesB();
    $("#dvTable").prop("click", ".fas.fa-play.fa-lg", null).off("click");
    $('#dvTable').on("click", ".fas.fa-play.fa-lg", function (event) {
        var i = event.target.id - 1 > data.length ? 0 : event.target.id - 1;
        playB(data, i);

    });

}

var selectPL = null;
var PAGE = 15;
function GenerateTablePL(data) {

    //Build an array containing Customer records.
    var customers = new Array();
    customers.push(["Name", "Action", "Time"]);

    for (var i = PLPageId * PAGE - PAGE; i < (PLPageId * PAGE >= data.length ? data.length : PAGE * PLPageId); i++) {
        customers.push([data[i].name, data[i].uuid, data[i].aLong]);
    }

    var table = document.createElement("TABLE");
    table.className = " table table-dark";
    table.border = "1";

    //Get the count of columns.
    var columnCount = customers[0].length;


    //Add the header row.
    var row = table.insertRow(-1);
    for (var i = 0; i < columnCount; i++) {

        var headerCell = document.createElement("TH");

        headerCell.innerHTML = customers[0][i];
        row.appendChild(headerCell);
    }

    //Add the data rows.
    for (var i = 1; i < customers.length; i++) {
        row = table.insertRow(-1);
        for (var j = 0; j < columnCount; j++) {
            var cell = row.insertCell(-1);
            if (j != 1) {
                cell.innerHTML = customers[i][j];
            } else {

                cell.innerHTML = '<i  type="button"  id="' + i + ' " class="fas fa-play fa-lg" ></i> ' + '<i onclick="delFrompl(this.id)" class="fas fa-minus fa-lg" type="button" id="' + customers[i][1] + ' " value="add to playlist" ></i>';

            }

        }
    }
    playButton.addEventListener("click", player, false);

    var dvTable = document.getElementById("dvTablePL");

    dvTable.innerHTML = "";
    dvTable.appendChild(table);

    loadPagesC();
    $("#dvTablePL").prop("click", ".fas.fa-play.fa-lg", null).off("click");
    $('#dvTablePL').on("click", ".fas.fa-play.fa-lg", function (event) {
        var i = event.target.id - 1 > data.length ? 0 : event.target.id - 1;
        playB(data, i);

    });
}

function sendSound(files) {
    var x = document.getElementById("musicloadspan");
    x.style.display = "block";
    var fileList = files;
    var fileReader = new FileReader();
    if (fileReader && fileList && fileList.length) {
        fileReader.readAsArrayBuffer(fileList[0]);
        fileReader.onload = function (e) {
            var buff = fileReader.result;
            var bytes = new Uint8Array(buff);
            var arr = Array.from(bytes);
            var data = JSON.stringify({"name": fileList[0].name, "data": arr});
            var xhr = new XMLHttpRequest();
            var url = "/add";
            xhr.open("POST", url, true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.send(data);
            x.style.display = "none";
            $("#loadMessageSpan").text("file upload")

        };
    }


}
function handleFileSelect(evt) {
    evt.stopPropagation();
    evt.preventDefault();

    files = evt.dataTransfer.files;
    console.log(files[0].type)
    if (files[0] != null && files[0].type == "audio/mp3")
        sendSound(files);
    else $("#loadMessageSpan").text("error occurred");


}
function handleDragOver(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    evt.dataTransfer.dropEffect = 'copy'; // Explicitly show this is a copy.
}
var dropZone = document.getElementById('drop_zone');
dropZone.addEventListener('dragover', handleDragOver, false);
dropZone.addEventListener('drop', handleFileSelect, false);


function monitor() {
    uploadAllTable(false);
    uploadPLTable(false);
    setTimeout(arguments.callee, 1000);
}

function loadPagesC() {


    $.getJSON('/count?uuid=' + selectPL, function (data) {
        var pageCount = (data.count / PAGE) +
            (data.count % PAGE > 0 ? 1 : 0);
        var i;
        if (pageCount != PLPageSize) {
            PLPageSize = pageCount;
            $("#pagesC").empty();
        for (i = 1; i <= pageCount; i++) {
            $('#pagesC').append(
                $('<li>').attr('class', 'page-item').append(
                    $('<a>').attr('class', 'page-link').attr('id', i)
                        .append(i))
            );
        }
        }
    });

    $("#pagesC").prop("click", ".page-link", null).off("click");
    $("#pagesC").on("click", ".page-link", function (event) {
        if (PLPageId != event.target.id) {
            PLPageId = event.target.id;
            uploadPLTable(true);
        }
    });
}

function loadPagesB() {


    var pageCount = (lastDataA / PAGE) +
        (lastDataA % PAGE > 0 ? 1 : 0);
        var i;
        if (pageCount != ALLPageSize) {
            ALLPageSize = pageCount;
            $("#pagesB").empty();
            for (i = 1; i <= pageCount; i++) {
                $('#pagesB').append(
                    $('<li>').attr('class', 'page-item').append(
                        $('<a>').attr('class', 'page-link').attr('id', i)
                            .append(i))
                );
            }
        }


    $("#pagesB").prop("click", ".page-link", null).off("click");
    $("#pagesB").on("click", ".page-link", function (event) {
        if (AllPageId != event.target.id - 1) {
            AllPageId = event.target.id - 1;
            uploadAllTable(true);
        }
    });
}


function PlaylistAdd() {
    $("#playlistNameSumbit").click(function () {

        var val = $("#playlistNameInput").val();
        if (val != "") {
            console.log(val);
            $.post("/addpl?plName=" + val, function () {
                dropdownPL();
            });

            $("#PLdateSpan").text("playlist have added");

        }

    })
}
function PLDelete() {
    if (selectPL != null) {
        $.post("/deletepl?uuid=" + selectPL, function () {
            dropdownPL();
        });
        selectPL = null;
    }
}

var lastDataA = 0;
var lastDataB = 0;

function uploadAllTable(isPageRequest) {
    if (!isPageRequest) {
        $.getJSON('/count?l=1', function (dataP) {
            if (dataP.count != lastDataA) {
                lastDataA = dataP.count;
                $.getJSON('/all?page=' + AllPageId + '&pageSize=' + PAGE, function (data) {
                    GenerateTable(data);
                });
            }
        });
    } else {
        $.getJSON('/all?page=' + AllPageId + '&pageSize=' + PAGE, function (data) {
            GenerateTable(data);
        });
    }
}

function uploadPLTable(isPageRequest) {

    if (selectPL != null)
        if (!isPageRequest) {
            $.getJSON('/count?uuid=' + selectPL, function (dataP) {
                if (dataP.count != lastDataB) {
                    lastDataB = dataP.count;
                    $.getJSON('/getplaylistbyuuid?uuid=' + selectPL, function (data) {
                        console.log(data);

                        GenerateTablePL(data.soundList);
                    });

                }
            });
        } else {
            $.getJSON('/getplaylistbyuuid?uuid=' + selectPL, function (data) {
                console.log(data);

                GenerateTablePL(data.soundList);
            });
        }
}
function dropdownPL() {
    var dropdown = document.getElementById("PL-select");
    dropdown.options.length = 0;
    $.getJSON('/getplaylist', function (data) {
        console.log(data);
        dropdown.options[dropdown.options.length] = new Option("Select Playlist", null);
        for (var i = 0; i < data.length; i++) {
            var opt = new Option(data[i].name, data[i].uuid, i + 1);

            dropdown.options[dropdown.options.length] = opt;
        }
    });


}

$("#PL-select").change(function () {
        selectPL = $(this).val();
    PLPageId = 1;
    }
);

$("#addSoundSubmit").click(function () {
    var input = document.getElementById("addSoundF")
    if (input.files != null) {
        sendSound(input.files);
    }
});

function addTopl(id) {
    $.post("/addstp?plUUID=" + selectPL + "&soundUUID=" + id, function (xhr, status, error) {
        var jsonError = jQuery.parseJSON(xhr.responseText);
        if (jsonError.code != 200) {
            $("#messageSpan").text(jsonError.error);

        } else {
            $("#messageSpan").text("Added successfully");

        }


    });


}

function delFrompl(id) {
    $.post("/delsound?plUUID=" + selectPL + "&soundUUID=" + id, function (xhr, status, error) {
        var jsonError = jQuery.parseJSON(xhr.responseText);
        if (jsonError.code != 200) {
            $("#messageSpan").text(jsonError.error);

        } else {
            $("#messageSpan").text("delete successfully");

        }


    });
}
$(document).ready(function (e) {
    PlaylistAdd();
    monitor();
    dropdownPL();


});

function tooglePlayer() {
    var x = document.getElementById("playerdiv");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}