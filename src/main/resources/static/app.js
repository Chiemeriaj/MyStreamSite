
let name;

console.log("it worked??");


function onSignIn(googleUser) {
    var id_token = googleUser.getAuthResponse().id_token;
    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
   name = profile.getName();



    if (name!= null){
        connect();
    }

}
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
    });
    name = null;
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

        stompClient.subscribe('/topic/greetings', function (greeting) {
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

    console.log("name sent")
}
function sendMessage() {
    stompClient.send("/app/chatbox", {}, JSON.stringify({'content':$("#chat").val(),'name': name
    }));

    console.log("message sent");
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>"+ message + "</td></tr>");
    console.log("look here!" + name);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#submit" ).click(function() { sendMessage(); });
});

