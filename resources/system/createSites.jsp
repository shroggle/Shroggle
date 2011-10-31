<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<html>
    <head>
        <title>Create sites</title>
        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type="text/javascript">

            $(document).ready(function () {
                var update = function (data) {
                    $.ajax({
                        url: "/system/createLotOfSitesData.action",
                        cache: false,
                        dataType: "json",
                        data: data,
                        success: function (info) {
                            $("#info").html(info);
                        }
                    });
                };

                $("[type=button]").click(function () {
                    var data = {
                        blueprintId: $("#blueprintId").val(),
                        count: $("#count").val(),
                        registartionId: $("#registartionId").val(),
                        userId: $("#userId").val(),
                        filledFormId: $("#filledFormId").val(),
                        urlPrefix: $("#urlPrefix").val(),
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
        <h1>Create sites</h1>

        <table width="80%">
            <tr>
                <td>
                    <h4>Settings</h4>
                    <p>
                        <label for="userId">User id:</label><br>
                        <input type="text" id="userId" maxlength="255">
                    </p>

                    <p>
                        <label for="urlPrefix">Url prefix:</label><br>
                        <input type="text" id="urlPrefix" maxlength="255">
                    </p>

                    <p>
                        <label for="count">How many:</label><br>
                        <input type="text" id="count" maxlength="255">
                    </p>

                    <p>
                        <label for="blueprintId">Blueprint or copy site id:</label><br>
                        <input type="text" id="blueprintId" maxlength="255">
                    </p>

                    <p>
                        <label for="filledFormId">Filled form id:</label><br>
                        <input type="text" id="filledFormId" maxlength="255">
                    </p>

                </td>

                <td valign="top">
                    <h4>Allowed operations</h4>
                    <input type="button" value="Create simple sites" name="simple"><br>
                    <input type="button" value="Copy site" name="copy"><br>
                    <input type="button" value="Create on blueprint" name="blueprint"><br>
                    <input type="button" value="Create network child sites" name="network">
                </td>

                <td valign="top">
                    <h4>Current operation:</h4>
                    <span id="info">No info, wait refresh...</span>
                </td>
            </tr>
        </table>

    </body>
</html>