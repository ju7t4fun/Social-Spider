/**
 * Created by ����� on 6/27/2015.
 */
var table;

jQuery(document).ready(function () {
    table = $('#postsTable').dataTable({

        "bPaginate": true,
        paging: true,
        "bInfo": false,
        "iDisplayStart": 0,
        "bProcessing": true,
        'iDisplayLength': 10,
        "bServerSide": true,
        "sAjaxSource": "http://localhost:8080/post?action=fillpostedposts",
        colVis: {
            "align": "right",
            "buttonText": "columns <img src=\"/img/caaret.png\"/>",
        },

        "aoColumnDefs": [

            {
                "class": "dt-body-left", "targets": 0
            },

            {
                "width": "60%", "targets": 0
            },
            {
                "width": "10%", "targets": 2
            },

            {
                "class": "dt-body-left", "targets": [3, 4, 5]
            },


            {
                "bVisible": false, "aTargets": [6]
            },

            {
                "aTargets": [0], "createdCell": function (td, cellData, rowData, row, col) {

                var strCellValue =
                    "<a href=\"javascript:ShowDialog(" + rowData[6] + ")\">" +
                    cellData +
                    "</a>";
                $(td).html(strCellValue);

            }

            },


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

    });


    //$(".dataTables_filter").attr("hidden", "");


    //$(".dataTables_length").attr("hidden", "");


    $('#refreshbtn').click(function () {
        table.fnStandingRedraw();
    });

})
;