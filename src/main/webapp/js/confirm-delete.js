function deleteConfirm(id) {
    $('#mod-delete').addClass('mod-show');
    $('#delete_butt').click(function () {
        $('#mod-delete').removeClass('mod-show');
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', '/post?action=remove&id=' + id, true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                toastrNotification(response.status, response.msg);
                if (response.status === 'success') {
                    $('#postsTable').DataTable().row($(this).parents('tr'))
                        .remove()
                        .draw();
                }
            }
        };
        xmlhttp.send(null);
    });
    $('#cancel_butt').click(function () {
        $('#mod-delete').removeClass('mod-show');
    });
}
