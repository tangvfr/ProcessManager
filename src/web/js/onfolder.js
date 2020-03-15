function onFolder(elem) {
    let list = [
        {
            "name": "name",
            "label": "",
            "type": "hidden",
            "value": "",
            "required": true
        }, 
        {
            "name": "newfolder",
            "label": "New Folder",
            "type": "text",
            "value": "",
            "required": false
        }
    ];
    list[0]["value"] = elem.name;
    list[1]["value"] = elem.nextElementSibling.nextElementSibling.innerText;
    popMenuCommand.show(false, "Change Folder Process "+elem.name, list, token, "folder", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}