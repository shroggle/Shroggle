<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.util.international.International" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.international.InternationalStorage" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%-- lightJs special parameter if it not null system show only light jses for presentation
application pages, in other case system show on page all scripts. For more details:
http://jira.web-deva.com/browse/SW-3649
--%>
<resources:natural name="presentation.js"/>

<script type="text/javascript">

    <% final InternationalStorage internationalStorageHead = ServiceLocator.getInternationStorage(); %>
    <% final International internationalHead = internationalStorageHead.get("head", Locale.US); %>
    /* Such international objects should be present and kept only in here. */
    var internationalLoginInAccountErrorTexts = new Object();
    internationalLoginInAccountErrorTexts.inputAnEmail = "<%= internationalHead.get("inputAnEmail") %>";
    internationalLoginInAccountErrorTexts.wrongEmailAddress = "<%= internationalHead.get("wrongEmailAddress") %>";
    internationalLoginInAccountErrorTexts.wrongEmailAddressConfirm = "<%= internationalHead.get("wrongEmailAddressConfirm") %>";
    internationalLoginInAccountErrorTexts.availableEmaile = "<%= internationalHead.get("availableEmaile") %>";
    internationalLoginInAccountErrorTexts.loading = "<%= internationalHead.get("loading") %>";
    internationalLoginInAccountErrorTexts.unknownLoginOrPassword = "<%= internationalHead.get("unknownLoginOrPassword") %>";
    internationalLoginInAccountErrorTexts.accountNotActive = "<%= internationalHead.get("accountNotActive") %>";
    internationalLoginInAccountErrorTexts.sessionHasExpired = "<%= internationalHead.get("sessionHasExpired") %>";
    internationalLoginInAccountErrorTexts.waitCheckInputEmail = "<%= internationalHead.get("waitCheckInputEmail") %>";

    var internationalErrorTexts = new Object();
    internationalErrorTexts.saveUnsavedChanges = "<%= internationalHead.get("saveUnsavedChanges") %>";

</script>