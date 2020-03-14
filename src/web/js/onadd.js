function onAdd() {
    let list = [
        {
            "name": "name",
            "label": "Name",
            "type": "text",
            "value": "",
            "required": true
        },
        {
            "name": "cmd",
            "label": "Command",
            "type": "text",
            "value": "",
            "required": false
        },
        {
            "name": "folder",
            "label": "Folder",
            "type": "text",
            "value": "",
            "required": false
        },
        {
            "name": "launch",
            "label": "Launch",
            "type": "checkbox",
            "value": "false",
            "required": false
        },
        {
            "name": "cmdstop",
            "label": "CommandStop",
            "type": "text",
            "value": "",
            "required": false
        }
    ];
    popMenuCommand.show(false, "Add New Process", list, "<targ=token></targ>", "add", "POST", "/command.tweb");
}