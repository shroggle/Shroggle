<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <script type="text/javascript">

            function showAfterLoad() {
                window.parent.document.getElementById("site").style.display = "block";
                window.parent.document.getElementById("mainLoadingMessageDiv").style.display = "none";
                disableHTMLlink();
            }

            function showLayouts() {
                window.parent.configurePageSettings.show({isEdit:true,
                    tab:window.parent.configurePageSettings.layoutTab});
            }

            function disableHTMLlink(){
                window.parent.disableControl($.find("[editHtml=editHtml]", window.parent.document));
            }

        </script>
    </head>
    <body onload="showAfterLoad()">
        <table width="100%" height="100%">
            <tbody>
                <tr>
                    <td align="center" valign="middle">
                        Please, change <a href="javascript:showLayouts()">layout</a> for this page.
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>