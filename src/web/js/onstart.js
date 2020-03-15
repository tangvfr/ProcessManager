function onStart(elem) {
    let list = [elem.name, 
        ""
    ];
    popMenuCommand.show(true, "Start Process "+elem.name, list, token, "start", "POST", "/command.tweb"
    , () => {this.sumbit();}, () => {popMenuCommand.hide();});
}