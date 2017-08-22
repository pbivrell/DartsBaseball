var request = new XMLHttpRequest();
request.onload = function(){
    var status = request.status;
    var data = request.responseText;
}

     
function send(command){
    console.log(command);
    request.open("POST","http://192.168.2.4:8080", true);
    request.setRequestHeader("Content-Type","text/plain;charset=UTF-8");
    request.send(command);
    setTimeout(window.location.reload(),5000);
    window.location.reload();
}
