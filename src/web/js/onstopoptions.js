function onStopForceNoScript() {
    let list = ["", ""];
    popMenuCommand.show(true, "Stop Force No Script", list, token, "stopforcenoscript", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}

function onStopForceWithScript() {
    let list = ["", ""];
    popMenuCommand.show(true, "Stop Force With Script", list, token, "stopforcewithscript", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}

function onStopNoForceNoScript() {
    let list = ["", ""];
    popMenuCommand.show(true, "Stop No Force No Script", list, token, "stopnoforcenoscript", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}

function onStopNoForceWithScript() {
    let list = ["", ""];
    popMenuCommand.show(true, "Stop No Force With Script", list, token, "stopnoforcewithscript", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}