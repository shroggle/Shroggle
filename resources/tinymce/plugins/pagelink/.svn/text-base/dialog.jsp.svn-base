<%@ page import="com.shroggle.presentation.TinyMcePageLinkAction" %>
<%@ page import="com.shroggle.entity.SiteTitlePageName" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final TinyMcePageLinkAction action = (TinyMcePageLinkAction) request.getAttribute("actionBean"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Insert page as link</title>
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript" src="../../tiny_mce_popup.js"></script>
    <script type="text/javascript" src="/dwr/engine.js"></script>
    <script type="text/javascript" src="/js/util.js"></script>
    <script type="text/javascript" src="/js/serviceCall.js"></script>
    <script type="text/javascript" src="/account/loginInAccount.js"></script>
    <script type="text/javascript">

        function tinyMcePageLinkSave() {
            var pageIds = document.getElementById("tinyMcePageLinkIds");
            if (pageIds.selectedIndex < 0) {
                alert("Select page or click cancel!");
                return;
            }

            var pageId = pageIds.options[pageIds.selectedIndex].value;
            var name = tinyMCEPopup.editor.selection.getContent();
            if (name.length == 0) {
                name = pageIds.options[pageIds.selectedIndex].innerHTML;
            }
            var text = "<a class=\"shroggleTag\" href=\"#\" pageId=\"" + pageId + "\">" + name + "</a>";
            tinyMCEPopup.editor.execCommand("mceReplaceContent", false, text);
            tinyMCEPopup.close();
        }

        function tinyMcePageLinkSitesSelect() {
            createLoadingArea({element:$("#tinyMcePageLinkIdsHolder")[0], text: "Loading data...", color: "green"});

            var serviceCall = new ServiceCall();

            serviceCall.addExceptionHandler(
                    LoginInAccount.EXCEPTION_CLASS,
                    LoginInAccount.EXCEPTION_ACTION);

            var siteId = $("#tinyMcePageLinkThisSite").attr("checked") ? window.parent.siteId : null;

            serviceCall.executeViaDwr("TinyMcePageLinkService", "execute", siteId, function (links) {
                var select = "<select id=\"tinyMcePageLinkIds\" size=\"25\"  style=\"width: 100%; height: 100%;\">";
                for (var i = 0; i < links.length; i++) {
                    var link = links[i];
                    select += ("<option value=\"" + link.pageId + "\">" + link.siteTitle + " - " + link.pageName + "</option>");
                }
                select += "</select>";
                $("#tinyMcePageLinkIdsHolder").html(select);
                removeLoadingArea();
            });
        }

    </script>
</head>

<body>
<label for="tinyMcePageLinkIds">Pages for insert:</label><br>

<table>
    <tr>
        <td>
            <input type="radio" id="tinyMcePageLinkThisSite"
                   name="tinyMcePageLinkSites" checked="checked" onclick="tinyMcePageLinkSitesSelect(this)">
            <label for="tinyMcePageLinkThisSite">From current site</label>
        </td>

        <td>
            <input type="radio" id="tinyMcePageLinkAllSites" name="tinyMcePageLinkSites"
                   onclick="tinyMcePageLinkSitesSelect(this);">
            <label for="tinyMcePageLinkAllSites">From all available sites</label>
        </td>
    </tr>
</table>
<div id="tinyMcePageLinkIdsHolder" style="width: 335px; height: 240px;">
    <select id="tinyMcePageLinkIds" size="25" style="width: 100%; height: 100%;">
        <% for (final SiteTitlePageName siteTitlePageName : action.getSiteTitlePageNames()) { %>
        <option value="<%= siteTitlePageName.getPageId() %>"><%= siteTitlePageName.getSiteTitle() %>
            - <%= siteTitlePageName.getPageName() %>
        </option>
        <% } %>
    </select>
</div>
<div class="mceActionPanel">
    <div style="float: left">
        <input type="button" onclick="tinyMcePageLinkSave();" id="insert" name="insert" value="Insert"/>
    </div>

    <div style="float: right">
        <input type="button" id="cancel" name="cancel" value="Cancel" onclick="tinyMCEPopup.close();"/>
    </div>
</div>
</body>
</html>