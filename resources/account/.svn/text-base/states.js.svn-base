function showStatesForCountry(country, widgetId, state, disable, newClassName) {

    new ServiceCall().executeViaDwr("UploadStatesService", "getStates", country, widgetId, function(statesHTML) {
        document.getElementById("statesHolder" + widgetId).innerHTML = statesHTML;
        if (state) {
            document.getElementById("state" + widgetId).value = state;
        }
        if (disable) {
            document.getElementById("state" + widgetId).disabled = true;
        }
        if (newClassName) {
            document.getElementById("state" + widgetId).className = newClassName;
        }
    });
}

function getStateValue(widgetId) {
    return document.getElementById("state" + widgetId).value;
}