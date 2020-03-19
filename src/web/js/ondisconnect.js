function onDisconnect(username) {
    let list = ["", ""];
    popMenuCommand.show(true, "Disconnect to "+username, list, token, "disconnect", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}