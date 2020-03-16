function onChangeTimeRestart(elem) {
    let list = [
        {
            "name": "time",
            "label": "Temps",
            "type": "time",
            "value": "",
            "required": true
        }, 
        {
            "name": "horaire",
            "label": "Horaire",
            "type": "checkbox",
            "value": "true",
            "checked": false,
            "required": false
        }
    ];
    list[0]["value"] = (timenoforce == "∞") ? "00:00" : timenoforce;
    list[1]["checked"] = timestop < 0;
    popMenuCommand.show(false, "Change Time Restart", list, token, "timerestart", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}