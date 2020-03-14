function onFolder(elem) {
    let list = [
        {
            "name": "name",
            "label": "",
            "type": "hidden",
            "value": "",
            "required": true
        }, {
            "name": "folder",
            "label": "",
            "type": "text",
            "value": "",
            "required": false
        }
    ];
    list[0]["value"] = elem.name;
    //get set folder actual
    popMenuCommand.show(false, "Change Folder Process "+elem.name, list, "<targ=token></targ>", "folder", "POST", "/command.tweb");
}