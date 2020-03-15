function onStopCmd(elem) {
    let list = [
        {
            "name": "name",
            "label": "",
            "type": "hidden",
            "value": "",
            "required": true
        }, 
        {
            "name": "newstopcmd",
            "label": "New Stop Command",
            "type": "text",
            "value": "",
            "required": false
        }
    ];
    list[0]["value"] = elem.name;
    list[1]["value"] = elem.nextElementSibling.nextElementSibling.innerText;
    popMenuCommand.show(false, "Change Stop Command Process "+elem.name, list, token, "stopcmd", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}