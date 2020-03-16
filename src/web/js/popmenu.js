class PopMenu {

    constructor () {
        this.popMenu = document.createElement("form");
        document.body.append(this.popMenu);
    }

    show(noarg, title, list, token, nameCmd, method, action, functionok, functioncancel) {
        this.popMenu.className = "formpopmenu";
        let code = "";
        if (noarg) {
            this.popMenu.method = method;
            this.popMenu.action = action;
            code = `
                <input type="hidden" name="token" value="`+token+`">
                <input type="hidden" name="link" value="`+document.URL+`">
                <input type="hidden" name="namecmd" value="`+nameCmd+`">
                <input type="hidden" name="name" value="`+list[0]+`">
                <div class="titlepopmenu fontpopmenu">`+title+`</div>
                <div class="divpopmenu"><div class="fontpopmenu textpopmenu">`+list[1]+`</div></div>`;
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
                if (list[i]["checked"] != undefined) {
                    code += list[i]["checked"] ? " checked" : "";
                }
                if (list[i]["pattern"] != undefined) {
                    code += " pattern=\""+list[i]["pattern"]+"\"";
                }
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
        btnok.className = "btnpmright btnpmcolorgreen fontpopmenu";
        btnok.type = "submit";
        btnok.value = "Ok";
        div.append(btnok);
        btnok.onclick = functionok;
        let btnca = document.createElement("input");
        btnca.className = "btnpmleft btnpmcolorred fontpopmenu";
        btnca.type = "button";
        btnca.value = "Cancel";
        div.append(btnca);
        btnca.onclick = functioncancel;
        let top = (document.body.clientHeight/2)-(this.popMenu.clientHeight/2);
        this.popMenu.style = "top: "+Math.round(top)+";";
    }

    hide() {
        this.popMenu.className = "";
        this.popMenu.innerHTML = "";
        this.popMenu.method = "";
        this.popMenu.action = "";
    }

    remove() {
        this.popMenu.remove();
    }

}