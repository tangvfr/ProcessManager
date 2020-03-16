function convertText(number, normal) {
    if (number == 0) return "∞";
    if (normal) {
        let date = new Date(number);
           const options = {year: '2-digit', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit'};
        return date.toLocaleDateString('fr-FR', options);
    } else {
        let time = Math.floor((number < 0 ? -number : number)/1000);
        let heures = Math.floor(time/3600);
        let minutesI = Math.floor(time%3600);
        let minutes = Math.floor(minutesI/60);
        let secondes = Math.floor(minutesI%60);
        return heures+":"+minutes+":"+secondes;
    }
}