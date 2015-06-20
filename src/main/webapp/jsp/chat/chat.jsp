<!DOCTYPE html>

<html>
<head>
  <title>Echo Chamber</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width">
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>
<body>

${user.name}

<div>
  <input type="text" id="messageinput"/>
</div>
<div>
  <button type="button" onclick="openSocket();" >Open</button>
  <button type="button" onclick="send();" >Send</button>
  <button type="button" onclick="closeSocket();" >Close</button>
</div>
<!-- Server responses get written here -->
<div id="messages"></div>


<div id="userList">Users: </div>

<!-- Script to utilise the WebSocket -->
<script type="text/javascript">

  var webSocket;
  var messages = document.getElementById("messages");
  var userList = document.getElementById("userList");

  function openSocket(){
    // Ensures only one connection is open at a time
    if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
      writeResponse("WebSocket is already opened.");
      return;
    }
    // Create a new instance of the websocket
    webSocket = new WebSocket("ws://localhost:8080/echo");

    /**
     * Binds functions to the listeners for the websocket.
     */
    webSocket.onopen = function(event){
        writeMessage("Successfully entered!");
    };

    webSocket.onmessage = function(event) {
      var partsArray = event.data.split('|');
      if (partsArray.length > 1) {
        if (partsArray[0].valueOf() == "user".valueOf()) {
          writeUser(partsArray[1]);
        }
        if (partsArray[0].valueOf() == "remove".valueOf()) {
          removeUser(partsArray[1]);
        }
      } else {
        writeMessage(event.data);
      }
    };

    webSocket.onclose = function(event){
//      writeResponse("Connection closed");
      $("#userList div").empty();
    }

  }

  /**
   * Sends the value of the text input to the server
   */
  function send(){
    var text = document.getElementById("messageinput").value;
    webSocket.send(text);
  }

  function closeSocket(){
    webSocket.close();
  }

  function writeMessage(text){
    messages.innerHTML += "<br/>" + text;
  }
  function writeUser(text){
    alert("from " + text);

    $('#userList').append("<div id='"+text+"'>" +text+"</div>");

   /* var node = userList.createElement('div');
    node.id = text;
    var textNode = node.createTextNode(text);
    //textNode.innerHTML = text;
    node.appendChild(textNode);
    userList.appendChild(node);
    /*userList.innerHTML += "<br/><div id='" + text + "'  >" + text + "</div>" ;*/
  }
  function removeUser(text) {
    messages.innerHTML += "<br/>" + text +" deleted!!!!";
    document.getElementById(text).remove();
  }

</script>

</body>
</html>