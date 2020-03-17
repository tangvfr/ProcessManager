function onInfo() {
    let swap = document.createElement("form");
    swap.method = "GET";
    swap.action = "/infoall.tweb";
    swap.innerHTML = `
    <input type="hidden" name="token" value="`+token+`">
    <input type="hidden" name="link" value="`+window.location+`">`;
    document.body.append(swap);
    swap.submit();
}