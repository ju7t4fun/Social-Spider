/**
 * Created by Орест on 6/27/2015.
 */
var table;

jQuery(document).ready(function () {
    table = $('#postsTable').dataTable({

        "bSort": false,
        "bPaginate": true,
        paging: true,
        "bInfo": true,
        "iDisplayStart": 0,
        "bProcessing": true,
        'iDisplayLength': 3,
        "bServerSide": true,
        "sAjaxSource": path + "/FillingPostsServlet",
        "dom": 'C<"clear">lfrtip',
        colVis: {
            "align": "right",
            restore: "Restore",
            showAll: "Show all",
            showNone: "Show none",
            order: 'alpha',
            "buttonText": "columns <img src=\"/img/caaret.png\"/>",
        },

        "language": {
            "infoFiltered": ""
        }
        ,
        "dom": 'Cf<"toolbar"">rtip',//, "columnDefs": [ { "targets": 2,"orderable": false }, { "targets": 3,"orderable": false } ]//,

        "columnDefs": [

            //{
            //"targets": [3,4,5],
            //
            //"createdCell": function (td, cellData, rowData, row, col) {
            //
            //    $(td).html('<img src=\"/img/like.png" width="23" height="23">' + "  " + cellData);
            //
            //}

            {
                "aTargets": [3], "createdCell": function (td, cellData, rowData, row, col) {

                $(td).html('<img src=\"/img/like.png" width="23" height="23">' + "  " + cellData);

            }
            },

            {
                "aTargets": [4], "createdCell": function (td, cellData, rowData, row, col) {

                $(td).html('<img src=\"/img/speaker.png" width="23" height="23">' + "  " + cellData);

            }
            },

            {
                "aTargets": [5], "createdCell": function (td, cellData, rowData, row, col) {

                $(td).html('<img src=\"/img/comment.png" width="23" height="23">' + "  " + cellData);

            }
            }

        ]

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

})
;