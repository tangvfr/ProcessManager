var popMenu;
var popMenuValid;

function initPopMenu() {
    popMenu = document.createElement("div");
    document.body.append(popMenu);
    popMenuValid = false;
}

function newPopMenu(title, list, token, nameCmd, methode, action) {
    let code = `
    <form method="`+methode+`" action="`+action+`" class="formpopmenu">
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
            <input type="button" value="Cancel" onclick="resetPopMenu();" class="cancelpopmenu fontpopmenu">
        </div>
    </form>`;
    popMenu.innerHTML = code;
}

function resetPopMenu() {
    popMenu.innerHTML = "";
}