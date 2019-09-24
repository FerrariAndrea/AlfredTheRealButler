/*
* =====================================
* ClientToRobotExecutor.js
* =====================================
*/
var net  = require('net');
var host = process.argv[2];
var port = Number(process.argv[3]);		//23 for telnet
/*
===============================================================
 * CONNECTION
===============================================================
 */
console.log('connect to ' + host + ":" + port);
var socket = net.connect({ port: port, host: host });
console.log('connect socket allowHalfOpen= ' + socket.allowHalfOpen );
socket.setEncoding('utf8');

// when receive data back, print to console
socket.on('data',function(data) {
	console.log(data);
});
// when server closed
socket.on('close',function() {
	console.log('connection is closed');
});
socket.on('end',function() {
	console.log('connection is ended');
});
/*
 * TERMINATION
 */ 
process.on('exit', function(code){
	console.log("Exiting code= " + code );
});
//See https://coderwall.com/p/4yis4w/node-js-uncaught-exceptions
process.on('uncaughtException', function (err) {
	console.error('got uncaught exception:', err.message);
	process.exit(1);		//MANDATORY!!!
});
/*
===============================================================
Interaction
===============================================================
*/
function sendMsg( msg ){
 	try{
 		socket.write(msg+"\n");
	}catch(e){ 
		console.log(" ERROR "  + e );
 	}
}
function sendMsgAfterTime( msg, time ){
	setTimeout(function(){ 
		console.log("SENDING..." + msg  );
		sendMsg( msg );
	},  time);
} 
/*
===============================================================
Application
===============================================================
*/ 
var msgNum=1;
sendMsgAfterTime("msg(robotCmd,dispatch,js,robot,robotCmd(turnRight)," + msgNum++ +")", 500);
sendMsgAfterTime("msg(robotCmd,dispatch,producer,robot,robotCmd(turnLeft)," + msgNum++ +")", 1000);
sendMsgAfterTime("msg(robotCmd,dispatch,producer,robot,robotCmd(forward),"   + msgNum++ +")", 1500);
sendMsgAfterTime("msg(robotCmd,dispatch,producer,robot,robotCmd(backward)," + msgNum++ +")", 2500);
sendMsgAfterTime("msg(robotCmd,dispatch,producer,robot,robotCmd(stop)," + msgNum++ +")", 3500);

setTimeout(function(){ console.log("SOCKET END");socket.close(); }, 4000);
 
/*
----------------------------------------------
USAGE
activate MainExecutor
node ClientToRobotExecuotr.js localhost 8010
----------------------------------------------
*/