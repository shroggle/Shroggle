var tableWithSort = {

    changeSortArrowType : function (element) {
        var sort_arrow_span = this.getCurrentSortArrowSpan();
        $(sort_arrow_span).hide();//removeChild(sort_arrow_span);
        sort_arrow_span = $(element).find("[name=sort_arrow_span]");
        sort_arrow_span.show();//.appendChild(sort_arrow_span);

        if ($(sort_arrow_span).attr("descending") == "true") {
            $(sort_arrow_span).attr("descending", "false");
            $(sort_arrow_span).find("img")[0].src = "/images/up.gif";
        } else {
            $(sort_arrow_span).attr("descending", "true");
            $(sort_arrow_span).find("img")[0].src = "/images/down.gif";
        }
        paginator.setFirstPage();
    },

    getSortProperties : function() {
        var span = this.getCurrentSortArrowSpan();
        return {
            sortFieldType : $(span).attr("sortFieldType"),
            descending : ($(span).attr("descending") == "true"),
            itemName : $(span).parent().attr("itemName")
        }
    },

    getCurrentSortArrowSpan : function() {
        return $("[name=sort_arrow_span]:visible");
    },

    addOnclickForParent : function(element) {
        var td = $(element).parent().parent()[0];
        var oldOnclick = td.onclick;
        td.onclick = function() {
            tableWithSort.changeSortArrowType(td);
            oldOnclick();
        }
    }

};

