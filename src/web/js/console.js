function sendCommand() {
    let text = document.getElementById("commandsend");
    let command = text.value;
    let xhttpSend = new XMLHttpRequest();
    xhttpSend.open("POST", "/command.tweb");
    let cmd = "&namecmd=sendconsole"+"&command="+encodeURIComponent(command);
    xhttpSend.send(args+cmd);
    text.value = "";
    document.getElementById("lastsend").innerText = command;
}

var timeConsole;
function readConsole() {
    let lines = "&lines="+document.getElementById("lines").innerText;
    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/command.tweb");
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("console").innerText = xhttp.responseText;
        }
        clearTimeout(timeConsole);
        timeConsole = setTimeout(readConsole, 1000);
    };
    let cmd = "&namecmd=readconsole";
    xhttp.send(args+lines+cmd);
}

var timeError;
function readError() {
    let lines = "&lines="+document.getElementById("lines").innerText;
    let xhttp1 = new XMLHttpRequest();
    xhttp1.open("POST", "/command.tweb");
    xhttp1.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("error").innerText = xhttp1.responseText;
        }
        clearTimeout(timeError);
        timeError = setTimeout(readError, 1000);
    };
    let cmd = "&namecmd=readerror";
    xhttp1.send(args+lines+cmd);
}