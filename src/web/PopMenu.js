class PopMenu {

    constructor (info) {
        this.info = info;
        this.popMenu = document.createElement(info ? "div" : "form");
        document.body.append(this.popMenu);
        this.id = "IdOfPopMenu";
    }

    show(title, list, token, nameCmd, method, action) {
        this.popMenu.method = method;
        this.popMenu.action = action;
        let code = `
            <input type="hidden" name="token" value="`+token+`">
            <input type="hidden" name="link" value="`+document.URL+`">
            <input type="hidden" name="namecmd" value="`+nameCmd+`">
            <div class="titlepopmenu fontpopmenu">`+title+`</div>`;
        //-------------------------------------
        for (let i = 0; i < list.length; i++) {
            if (list[i]["label"] !== "") {
                code += `<div class="divpopmenu"><label for="`+list[i]["name"]+`" class="fontpopmenu labelpopmenu">`+list[i]["label"]+`: </label>`;
            }
            code += `<input name="`+list[i]["name"]+`" type="`+list[i]["type"]+`" value="`+list[i]["value"]+`" class="inputpopmenu fontpopmenu"`;
            if (list[i]["required"]) {
                code += ` required>`;
            } else {
                code += `>`;
            }
            if (list[i]["label"] !== "") {
                code += "</div>";
            }
        }
        //-------------------------------------
        code += `
            <div class="divpopmenu">
                <input type="submit" value="Ok" class="okpopmenu fontpopmenu">
                <input type="button" value="Cancel" id="`+this.id+`" class="cancelpopmenu fontpopmenu">
            </div>`;
        this.popMenu.innerHTML = code;
        let btn = document.getElementById(this.id);
        btn.onclick = this.hide;
        btn.id = "";
    }

    hide() {
        this.popMenu.innerHTML = "";
        this.popMenu.method = "";
        this.popMenu.action = "";
    }

    delete() {
        this.popMenu.removeChild();
    }

}

export {PopMenu};