var table;


var urlBanned;

var urlCreated;
var urlActivated;


jQuery(document).ready(function () {
    table = $('#personTable').dataTable({

        "bPaginate": true,
        "order": [0, 'asc'],
        "bInfo": true,
        "bLengthChange": true,
        "iDisplayStart": 0,
        'iDisplayLength': 5,
        "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": path + "/UserFillingTableServlet",
        "dom": 'C<"clear">lfrtip',
        colVis: {
            "align": "right",
            restore: "Restore",
            showAll: "Show all",
            showNone: "Show none",
            order: 'alpha',
            "buttonText": "columns <img src=\"/img/caaret.png\"/>"
        },
        "language": {
            "infoFiltered": ""
        },
        "dom": 'Cf<"toolbar"">rtip',

        "columnDefs": [ {


            "targets": 4,

            "createdCell": function (td, cellData, rowData, row, col) {

                urlBanned = "<img src=\"/img/banned.jpg\" " +
                    " style=\"width:37px;height:37px;\" " +
                    " id=\"" + rowData[3] + "\" onclick=\"changeImage(this)\" title=\"banned\" >";

                urlCreated = "<img src=\"/img/created.jpg\" " +
                    " style=\"width:37px;height:37px;\" " +
                    " id=\"" + rowData[3] + "\" onclick=\"changeImage(this)\" title=\"created\" >";

                urlActivated = "<img src=\"/img/activated.jpg\" " +
                    " style=\"width:37px;height:37px;\" " +
                    " id=\"" + rowData[3] + "\" onclick=\"changeImage(this)\" title=\"activated\" >";


                if (cellData == "ACTIVATED") {
                    $(td).html(urlActivated);
                } else
                if (cellData == 'BANNED') {
                    $(td).html(urlBanned);
                } else
                if (cellData == 'CREATED') {
                    alert(rowData[3]);
                    $(td).html(urlCreated);
                }


            }
        } ]

    })
        .columnFilter({
            aoColumns: [
                //{ type: "number"},
                //{ type: "text" },
                //{ type: "text" },
                //{ type: "text" },
                //{ type: "text" },
                //{ type: "text" },
            ],
            bUseColVis: true
        }).fnSetFilteringDelay();
    $("#personTable_length").hide();
    //$(".dataTables_filter").css({ "display" :"none" });

    $('th').css('backgroundColor', 'blue');

    $(".dataTables_filter").css({"position": "auto"});
    $(".dataTables_filter").css({"left": "30%"});
    $(".dataTables_filter").css({"right": "30%"});
    $(".dataTables_filter").css({"top": "2%"});
    $(".dataTables_filter").css({"width": "350px"});
    $("div.toolbar").append($("dataTables_filter")).append('<div class="btn-group" style="padding:5px "><button class="btn btn-default" id="refreshbtn" style="background:none;border:1px solid #ccc;height:30px" type="button"><span class="glyphicon glyphicon-refresh" style="padding:3px"></span></button></div>');
    $("div.toolbar").css("float", "right").css({"position": "relative"});


    $('#refreshbtn').click(function () {
        table.fnStandingRedraw();
    });

});