function onRestartWC(elem) {
    let list = [elem.name, 
        ""
    ];
    popMenuCommand.show(true, "Restart With Command Process "+elem.name, list, token, "restartwc", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}

function onRestartWCAll(elem) {
    let list = ["", ""];
    popMenuCommand.show(true, "Restart With Command All Process", list, token, "restartwcall", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}
