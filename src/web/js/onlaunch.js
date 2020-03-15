function onLaunch(elem) {
    let list = [
        {
            "name": "name",
            "label": "",
            "type": "hidden",
            "value": "",
            "required": true
        }, 
        {
            "name": "launch",
            "label": "Launch",
            "type": "checkbox",
            "value": "true",
            "checked": false,
            "required": false
        }
    ];
    list[0]["value"] = elem.nextElementSibling.name;
    list[1]["checked"] = elem.className.startsWith("on");
    popMenuCommand.show(false, "Change Launch Process "+list[0]["value"], list, token, "launch", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}