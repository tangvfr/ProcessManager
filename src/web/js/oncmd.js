function onCmd(elem) {
    let list = [
        {
            "name": "name",
            "label": "",
            "type": "hidden",
            "value": "",
            "required": true
        }, 
        {
            "name": "newcmd",
            "label": "New Command",
            "type": "text",
            "value": "",
            "required": false
        }
    ];
    list[0]["value"] = elem.name;
    list[1]["value"] = elem.nextElementSibling.nextElementSibling.innerText;
    popMenuCommand.show(false, "Change Command Process "+elem.name, list, token, "cmd", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}