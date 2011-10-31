// This file is need for compatibility if dispatched items (blog, forum) are shown from
// admin iface.
var ajaxDispatcher = {};

ajaxDispatcher.execute = function (element){
    return true;
};