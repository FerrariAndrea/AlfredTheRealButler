/*
* =====================================
* frontend/uniboSupports/mqttUtils.js
* =====================================
*/
const mqtt   = require ('mqtt');  //npm install --save mqtt
const topic  = "unibo/qak/events";

//var mqttAddr = 'mqtt://localhost'
var mqttAddr = 'mqtt://192.168.43.61'
//var mqttAddr = 'mqtt://iot.eclipse.org'

var client   = mqtt.connect(mqttAddr);
var io  ; 	//Upgrade for socketIo;
var robotModel    = "none";
var sonarModel    = "none";

console.log("mqtt client= " + client );

exports.setIoSocket = function ( iosock ) {
 	io    = iosock;
	console.log("mqtt SETIOSOCKET io=" + io);
}


client.on('connect', function () {
	  client.subscribe( topic );
	  console.log('client has connected successfully with ' + mqttAddr);
});

//The message usually arrives as buffer, so I had to convert it to string data type;
client.on('message', function (topic, message){
  //console.log("mqtt io="+ io );
  //msg(modelContent,event,resourcemodel,none,content(robot(state(5))),74)
  console.log("mqtt RECEIVES:"+ message.toString()); //if toString is not given, the message comes as buffer
  var msgStr          = message.toString();
  var spRobot         = msgStr.indexOf("robot");
  var spSonarRobot    = msgStr.indexOf("sonarRobot");
  var sp1    = msgStr.indexOf("state");
  var msgStr = msgStr.substr(sp1);
  var sp2    = msgStr.indexOf("))");
  if( spRobot > 0 ) 
  var msg    = "";
  var content =  message.toString().substr(sp1,sp2+1);
  if( spRobot > 0 )      { msg = "robot - "; robotModel=msg+content        }
  if( spSonarRobot > 0 ) { msg = "sonarRobot - "; sonarModel = msg+content }
  msg = msg + content// message.toString().substr(sp1,sp2+1);
  io.sockets.send( msg );
});

exports.publish = function( msg, topic ){
	//console.log('mqtt publish ' + client);
	client.publish(topic, msg);
}

exports.getrobotmodel = function(){
	return robotModel;
}
exports.getsonarrobotmodel = function(){
	return sonarModel;
}