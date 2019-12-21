function closePopMenu() {
    popMenu.setAttribute("class", "");
    popMenu.setAttribute("numberinput", 0);
    popMenu.innerHTML = "";
    closeAction();
}

function cancelPopMenu() {
    closePopMenu();
    cancelAction();
}

function getValuePopMenu(popmenuinput) {
    switch (popmenuinput.getAttribute("type")) {
        case "text":
            return popmenuinput.value;
        case "password":
            return popmenuinput.value;
        case "checkbox":
            return popmenuinput.checked;
        default:
            return popmenuinput.value;
    }
}

function okPopMenu() {
    let numberinput = parseInt(popMenu.getAttribute("numberinput"));
    let result = "{";
    for (let i = 0; i < numberinput; i++) {
        let popmenuinput = document.getElementById("popmenuinput"+i);
        result += "\""+popmenuinput.getAttribute("name")+"\": \""+getValuePopMenu(popmenuinput)+(i == numberinput-1 ? "\"}" : "\",");
    }
    if (numberinput <= 0) result += "}";
    closePopMenu();
    okAction(JSON.parse(result));
}

function openPopMenu(title, message, type, option, actionOk, actionCancel, actionClose) {
    popMenu.setAttribute("class", "popmenubackground");
    popMenu.setAttribute("numberinput", (option != undefined ? option.length : 0));
    okAction = actionOk != null ? actionOk : function(result) {};
    cancelAction = actionCancel != null ? actionCancel : function(result) {};
    closeAction = actionClose != null ? actionClose : function(result) {};
    let codeBase = `<div class="popmenutitle"><button class="popmenuexit" onclick="cancelPopMenu();">X</button>`+title+`</div>`;
    if (message != null && message != "") {
        codeBase += `<div class="popmenulabelgris">`+message+`</div>`
    }
    if (option != undefined) {
	    for (let i = 0; i < option.length; i++) {
	        codeBase += `<span class="`+((i+1)%2 ?"popmenulabel":"popmenulabelgris")+`">`+option[i]["label"]+`<input class="popmenuinput" name="`+option[i]["name"]+`" id="popmenuinput`+i+`" type="`+option[i]["type"]+`"></span>`;
	    }
    }
    if (type == 0) {
        codeBase += `<button class="popmenubtn popmenucolorchange" onclick="okPopMenu();">Ok</button>`;
    } else if (type == 1) {
        codeBase += `<button class="popmenubtn popmenucolorchange" onclick="okPopMenu();">Ok</button><button class="popmenubtn" onclick="cancelPopMenu();">Cancel</button>`;
    } else if (type == 2) {
        codeBase += `<button class="popmenubtn popmenucolorchange" onclick="okPopMenu();">Yes</button><button class="popmenubtn" onclick="cancelPopMenu();">No</button>`;
    }
    //-------------------------------------------
    popMenu.innerHTML = codeBase;
}

document.head.innerHTML += `<link href="/PopMenu.css" rel="stylesheet">`;
var okAction = function(result) {};
var cancelAction = function() {};
var closeAction = function() {};
document.body.innerHTML += `<div id="popmenu" class="popmenubackground"></div>`;
var popMenu = document.getElementById("popmenu");
closePopMenu();