<%@page contentType="text/javascript" %>
var table;

var urlBanned;

var urlCreated;
var urlActivated;

jQuery(document).ready(function () {
    table = $('#personTable').dataTable({

        "bPaginate": true,
        "order": [0, 'asc'],
        "paging": true,
        "bInfo": false,
        "iDisplayStart": 0,
        'iDisplayLength': 10,
        "bProcessing": false,
        "bServerSide": true,
        "sAjaxSource": "${pageContext.request.contextPath}/admin/users?action=get",
        colVis: {
            "align": "right",
            "buttonText": "columns <img src=\"${pageContext.request.contextPath}/img/caaret.png\"/>"
        },

        "sDom": '<"top"<"toolbar">f>t<"bottom"lp><"clear">',

        "columnDefs": [{
            "bVisible": false, "aTargets": [0]
        }, {
            "aTargets": [3], "createdCell": function (td, cellData, rowData, row, col) {
                $(td).html('<a href="mailto:' + cellData + '">' + cellData + '</a>');
            }
        }, {
            "aTargets": [5], "createdCell": function (td, cellData, rowData, row, col) {
                var role;
                switch (cellData) {
                    case 'ADMIN':
                        role = '<img src="${pageContext.request.contextPath}/img/admin_role.png" width="37" height="37" onclick="changeRole(' + rowData[0] + ')">';
                        break;
                    case 'USER':
                        role = '<img src="/img/user_role.png" width="37" height="37" onclick="changeRole(' + rowData[0] + ')">';
                }
                $(td).html(role);
            }
        }, {
            "targets": 4,
            "createdCell": function (td, cellData, rowData, row, col) {

                urlBanned = "<img src=\"${pageContext.request.contextPath}/img/banned.jpg\" " +
                    " style=\"width:37px;height:37px;\" " +
                    " id=\"" + rowData[3] + "\" onclick=\"changeImage(this)\" title=\"banned\" >";

                urlCreated = "<img src=\"${pageContext.request.contextPath}/img/created.jpg\" " +
                    " style=\"width:37px;height:37px;\" " +
                    " id=\"" + rowData[3] + "\" onclick=\"changeImage(this)\" title=\"created\" >";

                urlActivated = "<img src=\"${pageContext.request.contextPath}/img/activated.jpg\" " +
                    " style=\"width:37px;height:37px;\" " +
                    " id=\"" + rowData[3] + "\" onclick=\"changeImage(this)\" title=\"activated\" >";

                if (cellData == "ACTIVATED") {
                    $(td).html(urlActivated);
                } else if (cellData == 'BANNED') {
                    $(td).html(urlBanned);
                } else if (cellData == 'CREATED') {
                    $(td).html(urlCreated);
                }
            }
        }]
    });

    var dataTables_filter_input = $(".dataTables_filter").find("input");
    dataTables_filter_input.attr("class", "form-control");
    dataTables_filter_input.attr("style", "width: 500px")

});

function changeRole(id) {
    $.post("${pageContext.request.contextPath}/admin/users",
        {
            action: 'role',
            id: id
        },
        onAjaxSuccess
    );
    function onAjaxSuccess(data) {
        toastrNotification(data.status, data.msg);
        $('#personTable').DataTable().draw(false);
    }
}