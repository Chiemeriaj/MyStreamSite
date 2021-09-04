
function signOut() {

    disconnect();
}



var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
        sendName();
    }
    else {
        $("#conversation").hide();

    }
    $("#greetings").html("");
}



function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/ greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/chat',function (message){
            showGreeting(JSON.parse(message.body).name +": " + JSON.parse(message.body).content );



        })
    });

}




function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': name}));

    console.log("name sent");
}
function sendMessage() {
    stompClient.send("/app/chatbox", {}, JSON.stringify({'content':$("#chat").val(),'name': name
    }));

    console.log("message sent");
}

function showGreeting(message) {
    $("#greetings").append("<tr id = message><td>" + message + "</td></tr>");
    console.log("look here!" + name);
}

if (name!= null){
    connect();
}
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $("#submit").click(function () {
        sendMessage();
    });
    $("#connect").click(function () {
        connect();
    });
});

