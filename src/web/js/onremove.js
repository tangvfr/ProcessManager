function onRemove(elem) {
    let list = [
        {
            "name": "name",
            "label": "",
            "type": "hidden",
            "value": "",
            "required": true
        }
    ];
    list[0]["value"] = elem.name;
    popMenuCommand.show(false, "Remove Process "+elem.name, list, "<targ=token></targ>", "remove", "POST", "/command.tweb");
}