/**
 * http://jira.web-deva.com/browse/SW-3739
 */

window.pageParameters = {

    /**
     * For remove parameter use value(name, null), for get
     * value use value(name)
     * @param name
     * @param value
     */
    value: function (name, value) {
        if (value == undefined) {
            return this[name];
        }
        this[name] = value;
    },

    /**
     * return all values as string: a=1&v=213
     */
    asUrl: function () {
        var url = "";
        for (var value in this) {
            if (this[value] && typeof(this[value]) != "function") {
                if (url != "") url += "&";
                url += value + "=" + this[value];
            }
        }

        if (url.length == 0) {
            var questionIndex = window.location.href.indexOf("?");
            if (questionIndex > -1) {
                url = window.location.href.substring(questionIndex + 1, window.location.href.length);
            }
        }

        return url;
    }

};