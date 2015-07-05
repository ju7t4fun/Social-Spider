/**
 * Created by ����� on 6/28/2015.
 */
/**
 * Created by ����� on 6/27/2015.
 */
var table;


function reloadData(grName) {

    document.getElementById("showAllBtnId").style.visibility = "visible";
    var newUrl = path + "/post?action=fillqueuededposts&groupNameToGroup="+grName;
    table.api().ajax.url(newUrl).load();

    document.getElementById("showAllBtnId").style.visibility = "visible";
};

jQuery(document).ready(function () {
    table = $('#queuedTable').dataTable({

        "bPaginate": true,
        aaSorting:[],
        paging: true,
        "bInfo": false,
        "iDisplayStart": 0,
        "bProcessing": true,
        'iDisplayLength': 10,
        "bServerSide": true,
        "sAjaxSource": path + "/post?action=fillqueuededposts",
        colVis: {
            "align": "right",
            "buttonText": "columns <img src=\"/img/caaret.png\"/>",
        },

        "aoColumnDefs": [

            {
                "targets": [0,1,2,4,5], "orderable": false
            },

            {
                "class": "dt-body-left", "targets": [0,1,3]
            },

            {
                "class": "dt-body-right", "targets": 5,
            },

            {
                "width": "80%", "targets": 0
            },

            {
                "bVisible": false, "aTargets": [4]
            },


            {
                "aTargets": [0], "createdCell": function (td, cellData, rowData, row, col) {

                $(td).html('<a href="#" onclick="viewPost(' + rowData[4] + ')" data-toggle="modal" data-target="#myModal">' +
                    cellData + '</a>');

            }
            },

            {
                "aTargets": [1], "createdCell": function (td, cellData, rowData, row, col) {

                var strCellValue ='<a href=\"javascript:reloadData(\''  + cellData.toString() + '\')\" >' + cellData + "</a>";
                $(td).html(strCellValue);

            }

            },

            {
                "aTargets": [2], "createdCell": function (td, cellData, rowData, row, col) {
                $(td).html(parseAttachment(cellData));
            }
            },

            {
                "aTargets": [5], "createdCell": function (td, cellData, rowData, row, col) {

                $(td).html('<div class="btn-group"><a class="btn btn-success" href="#"><i class="icon_check_alt2"></i></a><a class="btn btn-danger" onclick="removePost('  + cellData + ')"><i class="icon_close_alt2"></i></a></div>');

            }
            }

        ]

    });

    //$(".dataTables_filter").attr("hidden", "");


    //$(".dataTables_length").attr("hidden", "");

    //            $(".dataTables_filter").attr("hidden", "");
    $(".dataTables_length").attr("hidden", "");
    var dataTables_filter_input = $(".dataTables_filter").find("input");
    dataTables_filter_input.attr("class", "form-control");
    dataTables_filter_input.attr("style", "width: 500px")

    $('#refreshbtn').click(function () {
        table.fnStandingRedraw();
    });

});


function removePost(i) {
    deleteConfirmQueuedPost(i);
}


function parseAttachment(arg) {
    var args = arg.split("!");
    var cell = "";
    for (var i = 0; i < args.length; i++) {
        cell = cell + " " + parseDoc(args[i]);
    }
    return cell;
}

function parseDoc(arg) {
    var args = arg.split("|");
    switch (args[0]) {
        case "PHOTO":
            return '<img src=\"/img/icons/jpg-icon.png" style="width: 30px; height: 30px"><span class="badge bg-important">{count}</span>'.replace("{count}", args[1]);
        case "VIDEO":
            return '<img src=\"/img/icons/mpg-icon.png" style="width: 30px; height: 30px"><span class="badge bg-important">{count}</span>'.replace("{count}", args[1]);
        case "AUDIO":
            return '<img src=\"/img/icons/mp3-icon.png" style="width: 30px; height: 30px"><span class="badge bg-important">{count}</span>'.replace("{count}", args[1]);
        case "DOC":
            return '<img src=\"/img/icons/txt-icon.png" style="width: 30px; height: 30px"><span class="badge bg-important">{count}</span>'.replace("{count}", args[1]);
    }
    return "";
}
