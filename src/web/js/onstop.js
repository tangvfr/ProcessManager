function onStop(elem) {
    let list = [elem.name, 
        ""
    ];
    popMenuCommand.show(true, "Stop Process "+elem.name, list, token, "stop", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}

function onStopAll(elem) {
    let list = ["", ""];
    popMenuCommand.show(true, "Stop All Process", list, token, "stopall", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}