<%--
  Created by IntelliJ IDEA.
  User: Орест
  Date: 18.06.2015
  Time: 12:27
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Chat Tutorial</title>
  <script type="text/javascript">
//    var socket = new WebSocket("ws://localhost:8080/chat?username=ddddd");
//    socket.onopen = function () {
//      alert("Соединение открылось");
//    };
//    socket.onclose = function () {
//      alert("Соединение закрылось");
//    };
//    socket.onerror = function(name) {
//      alert(name);
//    }
//    socket.onmessage = function (event) {
//      alert ("Пришло сообщение с содержанием:", event.data);
//    };
//DOM Element

var usernameInputEl;
var connectBtnEl;
var disconnectBtnEl;
var usernameListEl ;
var articleEl;
var messageBoardEl;
var messageInputEl;
var sendBtnEl;
var chatToLabelEl;


window.onload = function () {

  usernameInputEl = document.getElementById("username");
  connectBtnEl = document.getElementById("connect");
  disconnectBtnEl = document.getElementById('disconnect');
  usernameListEl = document.getElementById("userList");
  messageBoardEl = document.getElementById('message-board');
  messageInputEl = document.getElementById('message');
  sendBtnEl = document.getElementById('send');
  chatToLabelEl = document.getElementById('destination');

// All btn, to chat to all people in the room
  chatToAllEl = document.getElementById('all');

  connectBtnEl.onclick = connect;
  disconnectBtnEl.onclick = disconnect;

  sendBtnEl.onclick = sendMessage;
  chatToAllEl.onclick = chatToFn('all');

};
// All btn, to chat to all people in the room
var chatToAllEl;

// current chat destination
var destination;

//Chat room that holds every conversation
var chatRoom

//socket object.
var socket ;



function connect() {
  //ws is a websocket protocol
  //location.host + location.pathname is the current url
  //new WebSocket(url) will immediately open a websocket connection

  alert("efefefefe");
  socket = new WebSocket("ws://localhost:8080/chat?username=" + usernameInputEl.value);

  //add the event listener for the socket object
  socket.onopen = socketOnOpen;
  socket.onmessage = socketOnMessage;
  socket.onclose = socketOnClose;
  window.onclose = socketOnClose;
}

function disconnect() {
  //close the connection and the reset the socket object
  socket.close();
  socket = undefined;
}

function socketOnOpen(e) {
  usernameInputEl.disabled = true;
  connectBtnEl.disabled = true;
  disconnectBtnEl.disabled = false;
}

function socketOnMessage(e) {
  var eventName = e.data.substr(0, e.data.indexOf("|"));
  var data = e.data.substr(e.data.indexOf("|") + 1);

  var fn;
  if(eventName == 'newUser') fn = newUser;
  else if(eventName == 'removeUser') fn = removeUser;
  else if(eventName == 'message') fn = getMessage;

  fn.apply(null, data.split('|'));
}

function socketOnClose(e) {
  usernameInputEl.disabled = false;
  connectBtnEl.disabled = false;
  disconnectBtnEl.disabled = true;
  usernameInputEl.value = '';
  messageBoardEl.innerHTML = '';
  chatToLabelEl.innerHTML = 'All';
  usernameListEl.innerHTML = '';
}

function newUser() {
  //if there is no users, return from the function
  if(arguments.length == 1 && arguments[0] == "") return;
  var usernameList = arguments;

  //Loop through all online users
  //foreach user, create a list with username as it's id and content
  //when the list is clicked, change the chat target to that user
  var documentFragment = document.createDocumentFragment();
  for(var i = 0; i < usernameList.length; i++) {
    var username = usernameList[i];
    var liEl = document.createElement("li");
    liEl.id = username;
    liEl.textContent = username;
    if(username != usernameInputEl.value) {
      //we can chat to everyone in the chat room
      //except our self
      liEl.onclick = chatToFn(username);
      liEl.classList.add('hoverable');
    }
    documentFragment.appendChild(liEl);
  }
  usernameListEl.appendChild(documentFragment);
}

function getMessage(sender, message, destination) {
  //destination
  destination = destination || sender;

  //if the current chat is the same as the incoming message destination
  //then add it to the conversation
  //else notify the user that there is an incoming message
  if(destination == destination) {
    var newChatEl = createNewChat(sender, message);
    messageBoardEl.appendChild(newChatEl);
  } else {
    var toEl = usernameListEl.querySelector('#' + destination);
    addCountMessage(toEl);
  }

  //push the incoming message to the conversation
  if(chatRoom[destination]) chatRoom[destination].push(newChatEl);
  else chatRoom[destination] = [newChatEl];
}

function removeUser(removedUsername) {
  //remove the user from the username list
  usernameListEl.querySelector('#' + removedUsername).remove();
}

function createNewChat(sender, message) {
  var newChatDivEl = document.createElement('div');
  var senderEl = document.createElement('span');
  var messageEl = document.createElement('span');

  if(sender == usernameInputEl.value) sender = 'me';

  senderEl.textContent = sender;
  messageEl.textContent = message;

  newChatDivEl.appendChild(senderEl);
  newChatDivEl.appendChild(messageEl);
  return newChatDivEl;
}

function addCountMessage(toEl) {
  var countEl = toEl.querySelector('.count');
  if(countEl) {
    var count = countEl.textContent;
    count = +count;
    countEl.textContent = count + 1;
  } else {
    var countEl = document.createElement('span');
    countEl.classList.add('count');
    countEl.textContent = '1';
    toEl.appendChild(countEl);
  }
}


function sendMessage() {
  var message = messageInputEl.value;
  if(message == '') return;

  //send a socket message with the following format
  //destination|message, e.g. Andi|Hello, world
  socket.send(destination + '|' + message );
  messageInputEl.value = '';


  var sender = usernameInputEl.value;
  //also put our conversation in the message board
  getMessage(sender, message, destination);
  //scroll to the bottom to see the newest message
  messageBoardEl.scrollTop = messageBoardEl.scrollHeight;
}

function chatToFn(username) {
  return function(e) {
    //remove the notification of how many new messages
    var countEl = usernameListEl.querySelector('#' + username + '>.count');
    if(countEl) countEl.remove();

    chatToLabelEl.textContent = username;
    destination = username;
    messageBoardEl.innerHTML = '';

    //Show all conversation from the username we are chatting to
    var conversationList = chatRoom[destination];
    if(!conversationList) return;
    var df = document.createDocumentFragment();
    conversationList.forEach(function (conversation) {
      df.appendChild(conversation);
    });
    messageBoardEl.appendChild(df);
  }

}
  </script>
  <%--<script src="${pageContext.request.contextPath}/js/testChat.js"  type="text/javascript" ></script>--%>
</head>
<body>
<header>
  <input id="username" class="username"  placeholder="Username..." autofocus>
  <button id="connect" class="connect">Connect</button>
  <button id="disconnect" disabled>Disconnect</button>
</header>
<aside>
  <h5>Online User(s)</h5>
  <ul id="userList">
    <li id="all" class="hoverable">All</li>
  </ul>
</aside>
<article>
  <div id="dialog">
    <span>Chat to <span id="chatTo">All</span></span>
    <div id="message-board"></div>
    <hr>
    <textarea id="message" placeholder="message.."></textarea>
    <button id="send">Send</button>
  </div>
</article>
</body>
</html>