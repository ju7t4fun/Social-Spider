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
        colVis: {
            "align": "right",
            "buttonText": "columns <img src=\"/img/caaret.png\"/>",
        },
        "aoColumnDefs":  [

            {
                "targets": 2, "orderable": false
            },
            {
                "targets": 3, "orderable": false
            },

            {
                "aTargets": [2], "createdCell": function (td, cellData, rowData, row, col) {

                var strCellValue ="<a href=\"javascript:PopUpShow(" + cellData + ")\">" +
                    "<button style=\"border-radius: 4px;border-color:#424D5F;background-color:#424D5F;color:#ffffff;margin:5px;padding:10px;\">" +
                    "Bind Accounts..." +
                    "</button> </a>";
                $(td).html(strCellValue);

            }
            },

            {
                "aTargets": [3], "createdCell": function (td, cellData, rowData, row, col) {

                var strCellValue = "<button style=\"border-radius: 4px;border-color:#424D5F;background-color:#424D5F;" +
                    "color:#ffffff;margin:5px;padding:10px;\">" +
                    "Show Statistic" +
                    "</button>";
                $(td).html(strCellValue);

            }
            }


        ],
    });


    //$(".dataTables_filter").attr("hidden", "");


    //$(".dataTables_length").attr("hidden", "");

    $('#refreshbtn').click(function () {
        table.fnStandingRedraw();
    });

    $('#addbtn').click(addrow);


});

function addrow() {
    ShowDialog(true);

}