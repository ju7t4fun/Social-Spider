var table;

jQuery(document).ready(function () {


    table = $('#postBindingTable').dataTable({

        "bPaginate": true,
        "order": [0, 'asc'],
        "bInfo": true,
        "iDisplayStart": 0,
        "bProcessing": true,
        'iDisplayLength': 5,
        "bServerSide": true,
        "sAjaxSource": path + "/PostBindingFillingTableServlet",
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
        "dom": 'Cf<"toolbar"">rtip',
        "columnDefs": [{"targets": 2, "orderable": false}, {"targets": 3, "orderable": false}],


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
    $("#postBindingTable_length").hide();
//$(".dataTables_filter").css({ "display" :"none" });


    $(".dataTables_filter").css({"position": "auto"});
    $(".dataTables_filter").css({"left": "30%"});
    $(".dataTables_filter").css({"right": "30%"});
    $(".dataTables_filter").css({"top": "2%"});
    $(".dataTables_filter").css({"width": "350px"});
    $("div.toolbar").append(
        $("dataTables_filter")).append('<div class="btn-group" style="padding:5px "><button class="btn btn-default" id="refreshbtn" style="background:none;border:1px solid #ccc;height:30px" type="button"><span class="glyphicon glyphicon-refresh" style="padding:3px"></span></button></div>'
    );
    $("div.toolbar").css("float", "right").css({"position": "relative"});


    $('#refreshbtn').click(function () {
        table.fnStandingRedraw();
    });

    $('#addbtn').click(addrow
        //function () {
        //    table.row.add( [
        //        "efeg", "degrgrg"
        //    ] ).draw();
        //
        //}

    );


});

function addrow() {

    ShowDialog(true);
    //$('#postBindingTable').dataTable().fnAddData( [
    // "defe", "defe" ] );

}