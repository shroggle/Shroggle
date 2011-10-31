var quicklyCreatePages = {};

quicklyCreatePages.setDescription = function (pageType){
    $("#quicklyPageDescTd").children().hide();
    $("#" + pageType + "Desc").show();
};