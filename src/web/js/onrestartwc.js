function onRestartWC(elem) {
    let list = [elem.name, 
        ""
    ];
    popMenuCommand.show(true, "Restart With Command Process "+elem.name, list, token, "restartwc", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}