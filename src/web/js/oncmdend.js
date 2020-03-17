function onCmdEnd(elem) {
    let list = [
        {
            "name": "newcmdend",
            "label": "New Command End",
            "type": "text",
            "value": "",
            "required": false
        }
    ];
    list[0]["value"] = cmdend;
    popMenuCommand.show(false, "Change Command End", list, token, "cmdend", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}