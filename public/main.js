function login() {
    var data = {
        "name": $("#username").val(),
        "password": $("#password").val()
    };

    $.ajax(
    {
        "type": "POST",
        "data": JSON.stringify(data),
        "contentType": "application/json",
        "url": "/login",
        "success": function()
        {
            $("#notLoggedIn").hide();
            $("#loggedIn").show();
        }
    });
}

$("#loggedIn").hide();