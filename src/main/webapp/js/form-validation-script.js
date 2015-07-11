var Script = function () {
    $().ready(function() {
        $("#register_form").validate({
            rules: {
                name: {
                    required: true,
                    minlength: 1,
                   // pattern: /^[a-zA-Z\u0400-\u04ff]+$/
                },
                address: {
                    required: true,
                    minlength: 10
                },
                surname: {
                    required: true,
                    minlength: 1
                },
                password: {
                    required: true,
                    minlength: 5
                },
                confirm_password: {
                    required: true,
                    minlength: 5,
                    equalTo: "#password"
                },
                email: {
                    required: true,
                    email: true
                },
                topic: {
                    required: "#newsletter:checked",
                    minlength: 2
                },
                agree: "required"
            },
            messages: {
                name: {
                    required: "Please enter your Name.",
                    minlength: "Your Name is too short.",
                    pattern: "Please enter correct Name."
                },
                address: {
                    required: "Please enter your Address.",
                    minlength: "Your Address must consist of at least 10 characters long."
                },
                surname: {
                    required: "Please enter your Surname.",
                    minlength: "Your surname is too short.",
                    pattern: "Please enter correct Surname."
                },
                password: {
                    required: "Please provide a password.",
                    minlength: "Your password must be at least 5 characters long."
                },
                confirm_password: {
                    required: "Please provide a password.",
                    minlength: "Your password must be at least 5 characters long.",
                    equalTo: "Please enter the same password as above."
                },
                email: "Please enter a valid email address.",
                agree: "Please accept our terms & condition."
            },
            errorPlacement: function (error, element) {
                element.attr('title', error.text());
                $(".error").tooltip(
                    {
                        tooltipClass: "mytooltip",
                        placement:'top',
                        html:true
                    });
            }
        });
    });
}();