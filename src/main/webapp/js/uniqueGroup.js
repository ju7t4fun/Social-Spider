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

        "sDom": '<"top"<"toolbar">f>t<"bottom"lp><"clear">',

        "columnDefs": [{
            "targets": [0, 1, 2], "orderable": false
        }, {
            "aTargets": [0], "createdCell": function (td, cellData, rowData, row, col) {
                $(td).html(Math.abs(cellData))
            }
        }, {
            "aTargets": [1], "createdCell": function (td, cellData, rowData, row, col) {
                if (rowData[0] > 0) {
                    $(td).html('<a href="http://vk.com/id' + rowData[0] + '" target="_blank">' + cellData + '</a>');
                } else {
                    $(td).html('<a href="http://vk.com/public' + Math.abs(rowData[0]) + '" target="_blank">' +
                        cellData + '</a>');
                }
            }
        }, {
            "aTargets": [2], "createdCell": function (td, cellData, rowData, row, col) {

                urlBanned = "<img src=\"/img/groupBanned.png\" " +
                    " style=\"width:32px;height:32px;\" " +
                    "  onclick=\"changeBan(\'" + rowData[0] + "\')\"  title=\"Banned\" >";

                urlActivated = "<img src=\"/img/groupApproved.jpg\" " +
                    " style=\"width:32px; height:32px;\" " +
                    "  onclick=\"changeBan(\'" + rowData[0] + "\')\" title=\"Unbanned\" >";
                if (cellData == false) {
                    $(td).html(urlActivated);
                } else if (cellData == true) {
                    $(td).html(urlBanned);
                }
            }
        }]

    });


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