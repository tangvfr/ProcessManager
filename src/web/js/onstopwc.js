function onStopWC(elem) {
    let list = [elem.name, 
        ""
    ];
    popMenuCommand.show(true, "Stop With Command Process "+elem.name, list, token, "stopwc", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}

function onStopWCAll(elem) {
    let list = ["", ""];
    popMenuCommand.show(true, "Stop With Command All Process", list, token, "stopwcall", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}