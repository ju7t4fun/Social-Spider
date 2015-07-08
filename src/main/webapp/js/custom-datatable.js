var table;


var urlBanned;

var urlCreated;
var urlActivated;


jQuery(document).ready(function () {
    table = $('#personTable').dataTable({

        "bPaginate": true,
        "order": [0, 'asc'],
        "bInfo": false,
        "bLengthChange": true,
        "iDisplayStart": 0,
        'iDisplayLength': 5,
        "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource":  "http://localhost:8080/UserFillingTableServlet",
        colVis: {
            "align": "right",
            //restore: "Restore",
            //showAll: "Show all",
            //showNone: "Show none",
            //order: 'alpha',
            "buttonText": "columns <img src=\"/img/caaret.png\"/>"
        },
        "language": {
            "infoFiltered": ""
        },

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

    });


    var dataTables_filter_input = $(".dataTables_filter").find("input");
    dataTables_filter_input.attr("class", "form-control");
    dataTables_filter_input.attr("style", "width: 500px")

    $('#refreshbtn').click(function () {
        table.fnStandingRedraw();
    });

});