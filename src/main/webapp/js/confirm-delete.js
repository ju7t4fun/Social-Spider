function deleteConfirmCreatedPost(id) {

    constructor(confirm, cancel);

    function confirm() {
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
                destroy(confirm, cancel);
            }
        };
        xmlhttp.send(null);
    }

    function cancel() {
        destroy(confirm, cancel);
    }

}

function deleteConfirmQueuedPost(id) {

    constructor(confirm, cancel);

    function confirm() {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", "http://localhost:8080/post?action=deletenewpost", true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                toastrNotification(response.status, response.msg);
                if (response.status === 'success') {
                    table.fnStandingRedraw();
                }
                destroy(confirm, cancel);
            }
        };
        xmlhttp.send("newPostId=" + id);
    }

    function cancel() {
        destroy(confirm, cancel);
    }
}

function deleteConfirmOwner(id) {

    constructor(confirm, cancel);

    function confirm() {
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
                destroy(confirm, cancel);
            }
        };
        xmlhttp.send(null);
    }

    function cancel() {
        destroy(confirm, cancel);
    }

}

function deleteConfirmPosted(id) {

    constructor(confirm, cancel);

    function confirm() {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", "http://localhost:8080//post?action=deleteNewPost&post_id=" + id, true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                toastrNotification(response.status, response.msg);
                if (response.status === 'success') {
                    $('#postsTable').DataTable().row($(this).parents('tr')).remove().draw(false);
                }
                destroy(confirm, cancel);
            }
        };
        xmlhttp.send();
    }

    function cancel() {
        destroy(confirm, cancel);
    }

}

function deleteConfirmQueued(id) {

    constructor(confirm, cancel);

    function confirm() {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", "http://localhost:8080//post?action=deleteNewPost&post_id=" + id, true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                toastrNotification(response.status, response.msg);
                if (response.status === 'success') {
                    $('#queuedTable').DataTable().row($(this).parents('tr')).remove().draw(false);
                }
                destroy(confirm, cancel);
            }
        };
        xmlhttp.send();
    }

    function cancel() {
        destroy(confirm, cancel);
    }
}

function deleteConfirmProfile(id) {

    constructor(confirm, cancel);

    function confirm() {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', '/accounts?action=remove&id=' + id, true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                toastrNotification(response.status, response.msg);
                if (response.status === 'success') {
                    $('#accountsTable').DataTable().row($(this).parents('tr'))
                        .remove().draw(false);
                }
                destroy(confirm, cancel);
            }
        };
        xmlhttp.send();
    }

    function cancel() {
        destroy(confirm, cancel);
    }
}

function deleteConfirmCategory(id) {
    constructor(confirm, cancel);

    function confirm() {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', 'http://localhost:8080/admin/categories?action=removecategory&id=' + id, true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                toastrNotification(response.status, response.msg);
                if (response.status === 'success') {
                    $('#categoryTable').DataTable().row($(this).parents('tr')).remove().draw(false);
                }
                destroy(confirm, cancel);
            }
        };
        xmlhttp.send();
    }

    function cancel() {
        destroy(confirm, cancel);
    }

}


// Додаємо привязку та показуємо вікно
function constructor(confirm, cancel) {
    $('#mod-delete').addClass('mod-show');
    $('#delete_butt').bind("click", confirm);
    $('#cancel_butt').bind("click", cancel);
}

// Видаляємо привязку та скриваємо вікно
function destroy(confirm, cancel) {
    $('#mod-delete').removeClass('mod-show');
    $('#delete_butt').unbind("click", confirm);
    $('#cancel_butt').unbind("click", cancel);
}