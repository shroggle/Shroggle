<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<html>
    <head>
        <title>Test speed</title>
        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type="text/javascript">

            $(document).ready(function () {
                var update = function (data) {
                    $.ajax({
                        url: "/system/checkPerformanceData.action",
                        cache: false,
                        dataType: "html",
                        data: data,
                        success: function (info) {
                            $("#info").html(info);
                        }
                    });
                };

                $("[type=button]").click(function () {
                    var data = {
                        requestDelay: $("#requestDelay").val(),
                        threadCount: $("#threadCount").val(),
                        execute: true
                    };

                    data[this.name] = true;

                    update(data);
                });

                window.setInterval(function () {
                    update({});
                }, 5000);
            });

        </script>
    </head>

    <body>
        <h1>Test speed</h1>

        <h4>Control:</h4>
        <label for="requestDelay">Request delay, msec:</label> <input maxlength="5" type="text" id="requestDelay" value="10">
        <label for="threadCount">Thread count:</label> <input type="text" maxlength="4" id="threadCount" value="16">
        <input type="button" value="Start" name="start">
        <input type="button" value="Stop" name="stop">

        <h4>Info:</h4>
        <span id="info">No info, wait refresh...</span>

    </body>
</html>