function onMenu() {
    let menu = document.getElementById("menu");
    if (menu.style.display === "block") {
        menu.style.display = "none";
        let box = document.getElementsByClassName("boxglobalhidden");
        for (i = 0; i < box.length; i++) {
            box[i].setAttribute("class", "boxglobal");
        }
    } else {
        menu.style.display = "block";
        let box = document.getElementsByClassName("boxglobal");
        for (i = 0; i < box.length; i++) {
            box[i].setAttribute("class", "boxglobalhidden");
        }
    }
}