class PopMenu {

    constructor () {
        this.popMenu = document.createElement("form");
        document.body.append(this.popMenu);
    }

    show(info, title, list, token, nameCmd, method, action) {
        this.popMenu.className = "formpopmenu";
        let code = "";
        if (info) {
            this.popMenu.method = "";
            this.popMenu.action = "";
            code = `<div class="divpopmenu"><div class="fontpopmenu textpopmenu">`+list+`</div></div>`;
        }  else {
            this.popMenu.method = method;
            this.popMenu.action = action;
            code = `
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
        }
        //-------------------------------------
        this.popMenu.innerHTML = code;
        let div = document.createElement("div");
        div.className = "divpopmenu";
        this.popMenu.append(div);
        let btnok = document.createElement("input");
        btnok.className = "okpopmenu fontpopmenu";
        btnok.type = "submit";
        btnok.value = "Ok";
        div.append(btnok);
        btnok.onclick = this.buttonOk;
        let btnca = document.createElement("input");
        btnca.className = "cancelpopmenu fontpopmenu";
        btnca.type = "button";
        btnca.value = "Cancel";
        div.append(btnca);
        btnca.onclick = this.buttonCancel;
    }

    buttonOk(obj, event) {
        alert("Button OK pressed !");
    }

    buttonCancel(obj, event) {
        alert("Button Cancel pressed !");
    }

    hide() {
        this.popMenu.className = "";
        this.popMenu.innerHTML = "";
        this.popMenu.method = "";
        this.popMenu.action = "";
    }

    delete() {
        this.popMenu.removeChild();
    }

}