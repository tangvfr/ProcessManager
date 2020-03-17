function onConsole(name) {
    let swap = document.createElement("form");
    swap.method = "GET";
    swap.action = "/console.tweb";
    swap.innerHTML = `
    <input type="hidden" name="token" value="`+token+`">
    <input type="hidden" name="link" value="`+window.location+`">
    <input type="hidden" name="name" value="`+name+`">`;
    document.body.append(swap);
    swap.submit();
}