var URL = "http://sciencemj-post.kro.kr:8080/";
var webSocket;
var stompClient = null;
var user = 0;
var id = 0;
var turn = 0;
var turn_ = 0;
var win = -1;
var colors = ["white", "black"];
var player_t;
var turn_t;
var omokMap;
function connect(){
    if(!webSocket){
      webSocket = new SockJS(URL+"ws");
      var msg = {
        sender: 0,
        msg: "test",
        type: 0,
        id: 0
      }
      stompClient = Stomp.over(webSocket);
      stompClient.connect({}, function(){
        stompClient.subscribe('/topic/message',getMap);
        var chatMessage = {
            sender: user,
            msg: "",
            type: 2,
            id: id
        };
        stompClient.send("/app/message",{}, JSON.stringify(chatMessage));
      }, function(){console.log("error connecting")});
    }
}
function draw(map){
    var canvas = document.getElementById('map')
    if (canvas.getContext){
        $(function(){
            var ctx = canvas.getContext('2d');
            ctx.clearRect(0, 0, window.innerWidth,window.innerHeight);
            ctx.fillStyle = 'rgba(148,70,0,1)';
            ctx.fillRect(0,0,window.innerWidth,window.innerHeight);
            ctx.fillStyle = "black";
            for(let i = 0;i < 21;i++){
              ctx.beginPath();
              ctx.moveTo(135 + (i * 30),15);
              ctx.lineTo(135 + (i * 30),615);
              ctx.moveTo(135,15 + (i * 30));
              ctx.lineTo(735,15 + (i * 30));
              ctx.stroke();
            }
            for(let i = 0;i < 21;i++){
              for(let j = 0;j < 21;j++){
                if(map[j][i] != 0){
                  ctx.beginPath();
                  ctx.fillStyle = colors[map[j][i] - 1];
                  ctx.arc(135 + (i * 30), 15 + (j * 30), 15, 0, 2 * Math.PI);
                  ctx.fill();
                }
              }
            }
        })
    }
}
function init(){
    var canvas = document.getElementById('map');
    id = prompt("room?"+"");
    user = prompt("player number?(1 or 2)"+"")-1;
    player_t = document.getElementById('PLAYER');
    player_t.innerHTML = "PLAYER: " + (user + 1);
    if (canvas.getContext){
      $(function(){
        canvas.addEventListener("click", function(e){
          //alert("x:" + e.layerX + " y:" + e.layerY);
          sendMessage(e.layerX, e.layerY);
        })
      })
    }
}
function sendMessage(x,y) {
    let _x = Math.round((x-135)/30);
    let _y = Math.round((y-15)/30);
    if(_x >= 0 && _y >= 0 && _x <= 20 && _y <= 20 && turn == user && omokMap[_y][_x] == 0){
      if(turn_ == 0){
        turn = 1;
      }else{
        turn = 0;
      }
      var chatMessage = {
          sender: user,
          msg: _x + " " + _y,
          type: 0,
          id: id
      };
      stompClient.send("/app/message",{}, JSON.stringify(chatMessage));
    }
}
function getMap(payload){
  var message = JSON.parse(payload.body);
  if(id == message.id){
      omokMap = message.map;
      turn = message.turn;
      win = message.win;
      turn_t = document.getElementById('TURN')
      turn_t.innerHTML = "TURN: " + (turn + 1);
      console.log("turn: " + turn);
      console.log("win: " + win);
      draw(message.map);
      if(win == (user + 1)){
        alert("You WIN!!!");
        window.location.replace(URL);
      }else if(win != -1){
        alert("opps win(-_-)");
        window.location.replace(URL);
      }
  }
}

window.onload = function(){
    init();
    connect();
}
