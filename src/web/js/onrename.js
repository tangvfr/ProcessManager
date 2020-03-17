function onRename(elem) {
    let list = [
        {
            "name": "name",
            "label": "",
            "type": "hidden",
            "value": "",
            "required": true
        },
        {
            "name": "newname",
            "label": "New Name",
            "type": "text",
            "value": "",
            "required": true
        }
    ];
    list[0]["value"] = elem.name;
    list[1]["value"] = elem.name;
    popMenuCommand.show(false, "Rename Process "+elem.name, list, token, "rename", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}