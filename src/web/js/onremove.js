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
    popMenuCommand.show(false, "Remove Process "+elem.name, list, token, "remove", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}

function onRemoveAll(elem) {
    let list = ["", ""];
    popMenuCommand.show(false, "Remove All Process", list, token, "removeall", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}