function onRestart(elem) {
    let list = [elem.name, 
        ""
    ];
    popMenuCommand.show(true, "Restart Process "+elem.name, list, token, "restart", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}

function onRestartAll(elem) {
    let list = ["", ""];
    popMenuCommand.show(true, "Restart All Process", list, token, "restartall", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}