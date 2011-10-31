<%@ page import="com.shroggle.presentation.site.ShowAdminInterfaceAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="showAdminInterface"/>
<html>
<head>
<title><international:get name="adminInterface"/></title>
<script type="text/javascript">
var store;



Ext.onReady(function() {

// create the data store
    var url = document.getElementById("url").value;
    var title = document.getElementById("title").value;
    var login = document.getElementById("login").value;

    store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '/account/search.action?url=' + url + '&title=' + title + "&login=" + login
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
            fields: [
                {name: 'title'},
                {name: 'url'},
                {name: 'login'},
                {name: 'Date', type: 'date', dateFormat: 'Y-m-d h:i:s'},
                {name: 'visitors', type: 'float'},
                {name: 'views', type: 'float'},
                {name: 'ident', type: 'float'}
            ]
        }),
        remoteSort: true
    });
    store.setDefaultSort('title', 'desc');

    store.load({params:{start:0, limit:20}});

    // create the Grid
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
            {id:'title',header: "Site title", width: 160, sortable: true, renderer : function(v, params, record) {
                return '<a href="/account/siteDetails.action?siteId=' + record.data.ident + '">' + record.data.title + '</a>';
            }, dataIndex: 'title'},
            {header: "Site url", width: 160, sortable: true, dataIndex: 'url'},
            {header: "User login", width: 160, sortable: true, dataIndex: 'login'},
            {header: "Date of creation", width: 160, sortable: true, renderer: Ext.util.Format.dateRenderer('Y/M/d h:i:s'), dataIndex: 'Date'},
            {header: "Unique visitors", width: 80, sortable: true, dataIndex: 'visitors'},
            {header: "Page views", width: 80, sortable: true, dataIndex: 'views'},
        ],
        height:510,
        width:815,
        trackMouseOver:false,
        loadMask: true,
        title:'Statistic information',
        sm: new Ext.grid.RowSelectionModel({selectRow:Ext.emptyFn}),        
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: store,
            displayInfo: true,
            displayMsg: 'Displaying sites {0} - {1} of {2}',
            emptyMsg: "No Sites to display"
        })
    });

    grid.render('table');
})

</script>
</head>
<body>
<table width="100%" bgcolor="gainsboro">
    <tr>
        <td>
            <stripes:link
                    beanclass="com.shroggle.presentation.site.LogoutFromUserAction">Logout</stripes:link>
        </td>
    </tr>
</table>
<br>

<b> <international:get name="searchBySites"/> </b>

<stripes:form beanclass="com.shroggle.presentation.site.ShowAdminInterfaceAction" method="show">
    <table>
        <tr>
            <td>
                <international:get name="siteTitle"/>
            </td>
            <td>
                <stripes:text id="title" name="title" value=""/>
            </td>
        </tr>
        <tr>
            <td>
                <international:get name="siteUrl"/>
            </td>
            <td>
                <stripes:text id="url" name="url" value=""/>
            </td>
        </tr>
        <tr>
            <td>
                <international:get name="userLogin"/>
            </td>
            <td>
                <stripes:text id="login" name="login" value=""/>
            </td>
        </tr>
    </table>
    <table>
        <tr>
            <td>
                <stripes:submit name="show" value="Search"/>
            </td>
            <td>
                <stripes:button name="reset" value="Reset"/>
            </td>
        </tr>
    </table>
</stripes:form>
<br>

<div id="table">
</div>
</body>
</html>