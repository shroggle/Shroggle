function showApplicationHelpWindow() {
    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.CannotShowHelpWindowException",
            function (exception) {
                alert(exception.message);
            });

    serviceCall.executeViaDwr("ShowHelpWindowService", "show", function (response) {

        //  This will open the help screen in a new window
        window.open(response);

     //  This code opens the help screen as a modal window    
     //   var contentsWindow = createConfigureWidgetIframe({width:700, height:600, makeNotModal:true, id:"help_window"});
     //   contentsWindow.src = response;
     //   getActiveWindow().resize();

    });


}