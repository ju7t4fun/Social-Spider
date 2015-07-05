function deleteConfirmCreatedPost(id) {
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

function deleteConfirmQueuedPost(id) {
    $('#mod-delete').addClass('mod-show');
    $('#delete_butt').click(function () {
        $('#mod-delete').removeClass('mod-show');
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", "http://localhost:8080/post?action=deletenewpost", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("newPostId=" + id);

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                toastrNotification(response.status, response.msg);
                if (response.status === 'success') {
                    table.fnStandingRedraw();
                }
            }
        }
    });
    $('#cancel_butt').click(function () {
        $('#mod-delete').removeClass('mod-show');
    });
}

function deleteConfirmOwner(id) {
    $('#mod-delete').addClass('mod-show');
    $('#delete_butt').click(function () {
        $('#mod-delete').removeClass('mod-show');
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', '/owner?action=remove&id=' + id, true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                toastrNotification(response.status, response.msg);
                if (response.status === 'success') {
                    $('#ownersTable').DataTable().row($(this).parents('tr'))
                        .remove().draw(false);
                }
            }
        };
        xmlhttp.send(null);
    });
    $('#cancel_butt').click(function () {
        $('#mod-delete').removeClass('mod-show');
    });
}

function deleteConfirmPosted(id) {
    $('#mod-delete').addClass('mod-show');
    $('#delete_butt').click(function () {
        $('#mod-delete').removeClass('mod-show');
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", "http://localhost:8080//post?action=deletenewpost", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("newPostId=" + id);

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                toastrNotification(response.status, response.msg);
                if (response.status === 'success') {
                    table.fnStandingRedraw();
                }
            }
        }
    });
    $('#cancel_butt').click(function () {
        $('#mod-delete').removeClass('mod-show');
    });
}