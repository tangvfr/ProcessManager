function onRestart(elem) {
    let list = [elem.name, 
        ""
    ];
    popMenuCommand.show(true, "Restart Process "+elem.name, list, token, "restart", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}