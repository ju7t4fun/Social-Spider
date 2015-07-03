/**
 * Created by Орест on 7/2/2015.
 */
var table;
var urlBanned;
var urlActivated;



jQuery(document).ready(function () {
    table = $('#groupTable').dataTable({

        "bSort": true,
        aaSorting: [],
        "bPaginate": true,
        "paging": true,
        "bInfo": false,
        "iDisplayStart": 0,
        "bProcessing": true,
        'iDisplayLength': 10,
        "bServerSide": true,
        "sAjaxSource": "/admin/groups?action=loadunique",
        colVis: {
            "align": "right",
            "buttonText": "columns <img src=\"/img/caaret.png\"/>",
        },

        "columnDefs": [

            {
                "targets": [0, 1, 2], "orderable": false
            },

            {
                "aTargets": [2], "createdCell": function (td, cellData, rowData, row, col) {

                urlBanned = "<img src=\"/img/banned.jpg\" " +
                    " style=\"width:37px;height:37px;\" " +
                    "  onclick=\"changeBan(\'" + rowData[0] + "\')\"  title=\"Banned\" >";

                urlActivated = "<img src=\"/img/activated.jpg\" " +
                    " style=\"width:37px;height:37px;\" " +
                    "  onclick=\"changeBan(\'" + rowData[0] + "\')\" title=\"Unbanned\" >";
                if (cellData == false) {
                    $(td).html(urlActivated);
                } else if (cellData == true) {
                    $(td).html(urlBanned);
                }


            }
            }
        ]

    });


//            $(".dataTables_filter").attr("hidden", "");
    $(".dataTables_length").attr("hidden", "");
    var dataTables_filter_input = $(".dataTables_filter").find("input");
    dataTables_filter_input.attr("class", "form-control");
    dataTables_filter_input.attr("style", "width: 500px");

});


function changeBan(vk_id) {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "/admin/groups?action=banunban", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("vk_id=" + vk_id);

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4) {
            table.fnStandingRedraw();
        }
    }
}