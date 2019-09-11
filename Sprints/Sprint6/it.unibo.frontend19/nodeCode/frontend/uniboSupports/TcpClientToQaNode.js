/*
* =====================================
* TcpClientToQaNode.js
* =====================================
*/
var   net    = require('net');
//var ansi   = require('ansi');
//var cursor = ansi(process.stdout);

var socket = null;

//var host = process.argv[2];
//var port = Number(process.argv[3]);		//23 for telnet

//console.log('connect to ' + host + ":" + port);
//var socket = net.connect({ port: localhost, host: 8021 });



function sendMsg( msg ){
 		if( socket == null ){
 	  		console.log('TcpClientToQaNode ATTEMPTS CONNECTION ...'  );
			socket = net.connect({ port: 8021, host: "localhost" });
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
			socket.on('error',function() {
				console.log('	TcpClientToQaNode WARNING connection with qa not possible');
			});
		
		}else
		socket.write(msg+"\n");
 }

//===============================================================

process.on('exit', function(code){
	console.log("Exiting code= " + code );
});

//See https://coderwall.com/p/4yis4w/node-js-uncaught-exceptions
process.on('uncaughtException', function (err) {
//cursor.reset().fg.red();
	console.error('TcpClientToQaNode got uncaught exception:', err.message);
//cursor.reset();
//	process.exit(1);		//MANDATORY!!!
});

//===============================================================



module.exports = sendMsg; 

/*
console.log("SENDING  event 1 "  );
setTimeout(function(){sendMsg("msg(alarm,event,jsSource,none,alarm(fire),1)")}, 1000);
console.log("SENDING  message 1 "  );
socket.write("msg(info,dispatch,jsSource,qareceiver,info(ok1),2)\n");
console.log("SENDING  message 2 "  );
socket.write("msg(info,dispatch,jsSource,qareceiver,info(ok2),3)\n");
console.log("SENDING  event 2 "  );
setTimeout(function(){sendMsg("msg(alarm,event,jsSource,none,alarm(obstacle),2)")}, 500);
//socket.end();		//the node of the qa receiver gets some null .... 
*/

/*
----------------------------------------------
USAGE	(SERVER qa)
node TcpClientToQaNode.js localhost 8021
----------------------------------------------
*/