/**
 * Created by ����� on 6/28/2015.
 */
/**
 * Created by ����� on 6/27/2015.
 */
var table;

jQuery(document).ready(function () {
    table = $('#queuedTable').dataTable({

        "bPaginate": true,
        paging: true,
        "bInfo": false,
        "iDisplayStart": 0,
        "bProcessing": true,
        'iDisplayLength': 10,
        "bServerSide": true,
        "sAjaxSource": "http://localhost:8080/post?action=fillqueuededposts",
        colVis: {
            "align": "right",
            "buttonText": "columns <img src=\"/img/caaret.png\"/>",
        },

        "aoColumnDefs": [

            {
                "class": "dt-body-left", "targets": [0,1,2]
            },

            {
                "width": "80%", "targets": 0
            },

            {
                "bVisible": false, "aTargets": [3]
            },


            {
                "aTargets": [0], "createdCell": function (td, cellData, rowData, row, col) {

                var strCellValue =
                    "<a href=\"javascript:ShowDialog(" + rowData[6] + ")\">" +
                    cellData +
                    "</a>";
                $(td).html(strCellValue);

            }
            }

        ]

    });

    //$(".dataTables_filter").attr("hidden", "");


    //$(".dataTables_length").attr("hidden", "");

    $('#refreshbtn').click(function () {
        table.fnStandingRedraw();
    });

})
;