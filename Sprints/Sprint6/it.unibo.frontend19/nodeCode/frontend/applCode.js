/*
frontend/uniboSupports/applCode
*/
const express = require('express');
const path = require('path');
//const favicon     = require('serve-favicon');
const logger = require('morgan');	//see 10.1 of nodeExpressWeb.pdf;
//const cookieParser= require('cookie-parser');
const bodyParser = require('body-parser');
const fs = require('fs');
const index = require('./appServer/routes/index');
var io; 	//Upgrade for socketIo;

//for delegate
const mqttUtils = require('./uniboSupports/mqttUtils');
const coapCToRM = require('./uniboSupports/coapClientToResourceModel');
const coapCToF = require('./uniboSupports/coapClientToFridge');
const coapCToD = require('./uniboSupports/coapClientToDishwasher');
const coapCToP = require('./uniboSupports/coapClientToPantry');

//require("node-coap-client").CoapClient; 

var app = express();

// view engine setup;
app.set('views', path.join(__dirname, 'appServer', 'views'));
app.set('view engine', 'ejs');

//create a write stream (in append mode) ;
var accessLogStream = fs.createWriteStream(path.join(__dirname, 'morganLog.log'), { flags: 'a' })
app.use(logger("short", { stream: accessLogStream }));

//Creates a default route. Overloads app.use('/', index);
//app.get("/", function(req,res){ res.send("Welcome to frontend Server"); } );

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));				//shows commands, e.g. GET /pi 304 23.123 ms - -;
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
//app.use(cookieParser());

app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'jsCode'))); //(***)

//DEFINE THE ROUTES ;
//app.use('/', index);		 

//Creates a default route for /pi;
app.get('/info', function (req, res) {
	res.send('This is the frontend-Unibo!')
});

app.get('/', function (req, res) {
	res.render("index");
});

app.get('/fridgemodel', function (req, res) {
	res.send(mqttUtils.getfridgemodel())
});
app.get('/robotmodel', function (req, res) {
	res.send(mqttUtils.getrobotmodel())
});
app.get('/sonarrobotmodel', function (req, res) {
	res.send(mqttUtils.getsonarrobotmodel())
});
app.get('/dishwashermodel', function (req, res) {
	res.send(mqttUtils.getdishwashermodel())
});
app.get('/pantrymodel', function (req, res) {
	res.send(mqttUtils.getpantrymodel())
});

/*
 * ====================== COMMANDS ================
 */
app.post("/addFood1-1", function (req, res, next) { superHandlePostMove("addFood1-1", "adding food 1", req, res, next); });
app.post("/addFood1-2", function (req, res, next) { superHandlePostMove("addFood1-2", "adding food 1", req, res, next); });
app.post("/addFood1-3", function (req, res, next) { superHandlePostMove("addFood1-3", "adding food 1", req, res, next); });
app.post("/addFood2-1", function (req, res, next) { superHandlePostMove("addFood2-1", "adding food 2", req, res, next); });
app.post("/addFood2-2", function (req, res, next) { superHandlePostMove("addFood2-2", "adding food 2", req, res, next); });
app.post("/addFood2-3", function (req, res, next) { superHandlePostMove("addFood2-3", "adding food 2", req, res, next); });
app.post("/prepare", function (req, res, next) { superHandlePostMove("prepare", "preparing room", req, res, next); });
app.post("/clear", function (req, res, next) { superHandlePostMove("clear", "clearing room", req, res, next); });


//PER ANDREA FERRARI: LEGGI LE DUE SEGUENTI LINEE
app.post("/stop", function (req, res, next) { superHandlePostMove("stop", "freezing time", req, res, next); });
app.post("/reactivate", function (req, res, next) { superHandlePostMove("reactivate", "resuming time", req, res, next); });
app.post("/home", function (req, res, next) { superHandlePostMove("home", "going to home", req, res, next); });
app.post("/exploreroom", function (req, res, next) { superHandlePostMove("exploreroom", "explore bunds", req, res, next); });
app.post("/exploretable", function (req, res, next) { superHandlePostMove("exploretable", "explore for table", req, res, next); });

/*
* SPAZIETTINO PRIVATO DI LUCA PER TESTING
*/

app.post("/test", function (req, res, next) {
	testHandlePostMove("test", "testing button", req, res, next);
});

function testHandlePostMove(cmd, msg, req, res, next) {
	result = "Web server done: " + cmd
	consssssole.log("CARNEFICE OKKKKKKKKK");
	mqttUtils.publish("",msg); //e' sbagliato ma si tenta.
	next();
}

//------------------------------------------------------------------------------------------------------------------


/*
*
*	COMANDI DI NATALI
*/
app.post("/w", function (req, res, next) { handlePostMove("w", "moving forward", req, res, next); });
app.post("/s", function (req, res, next) { handlePostMove("s", "moving backward", req, res, next); });
app.post("/a", function (req, res, next) { handlePostMove("a", "moving left", req, res, next); });
app.post("/d", function (req, res, next) { handlePostMove("d", "moving right", req, res, next); });
app.post("/h", function (req, res, next) { handlePostMove("h", "stopped", req, res, next); });

//APPLICATION
app.post("/startappl", function (req, res, next) {
	delegateForAppl("startAppl", req, res);
	next();
});
app.post("/stopappl", function (req, res, next) {
	delegateForAppl("stopAppl", req, res);
	next();
});


function handlePostMove(cmd, msg, req, res, next) {
	result = "Web server done: " + cmd
	delegate(cmd, msg, req, res);
	next();
}

//SuperHandlePostMove and SuperDelegate are made by Luca C.
function superHandlePostMove(cmd, msg, req, res, next) {
	result = "Web server done: " + cmd
	superDelegate(cmd, msg, req, res);
	next();
}

//=================== UTILITIES =========================

var result = "";

app.setIoSocket = function (iosock) {
	io = iosock;
	mqttUtils.setIoSocket(iosock);
	console.log("app SETIOSOCKET io=" + io);
}

function superDelegate(cmd, newState, req, res) {
	publishMsg(cmd)
}

function delegate(cmd, newState, req, res) {
	//publishMsgToRobotmind(cmd);                  //interaction with the robotmind 
	//publishEmitUserCmd(cmd);                     //interaction with the basicrobot
	//publishMsgToResourceModel("robot",cmd);	    //for hexagonal mind
	changeResourceModelCoap(cmd);		            //for hexagonal mind RESTful m2m
}

function delegateForAppl(cmd, req, res, next) {
	console.log("app delegateForAppl cmd=" + cmd);
	result = "Web server delegateForAppl: " + cmd;
	publishMsgToRobotapplication(cmd);
}

/*
 * ============ TO THE BUSINESS LOGIC =======
 */


// We are going to communicate only with the Explorer so we're most likely to use only this method
// Note: So far it goes ALWAYS to (3,0). CMD is IGNORED, needs to be modified so that the method can be used to do many things.
var publishMsg = function (cmd) {
	switch (cmd) {
		case "addFood1-1":
			var msgstr = "msg(addFood,dispatch,js,missionsolver,addFood(1,1),1)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/missionsolver");
			break;

		case "addFood1-2":
			var msgstr = "msg(addFood,dispatch,js,missionsolver,addFood(1,2),2)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/missionsolver");
			break;

		case "addFood1-3":
			var msgstr = "msg(addFood,dispatch,js,missionsolver,addFood(1,3),3)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/missionsolver");
			break;

		case "addFood2-1":
			var msgstr = "msg(addFood,dispatch,js,missionsolver,addFood(2,1),1)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/missionsolver");
			break;

		case "addFood2-2":
			var msgstr = "msg(addFood,dispatch,js,missionsolver,addFood(2,2),2)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/missionsolver");
			break;

		case "addFood2-3":
			var msgstr = "msg(addFood,dispatch,js,missionsolver,addFood(2,3),3)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/missionsolver");
			break;

		case "prepare":
			var msgstr = "msg(prepare,dispatch,js,missionsolver,prepare(X),1)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/missionsolver");
			break;

		case "clear":
			var msgstr = "msg(clear,dispatch,js,missionsolver,clear(X),1)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/missionsolver");
			break;

		case "home":
			var msgstr = "msg(home,dispatch,js,missionsolver,home(X),1)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/missionsolver");
			break;

		case "stop":
			var msgstr = "msg(stop,dispatch,js,onerotateforward,stop(X),1)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/onerotateforward");
			msgstr = "msg(stop,dispatch,js,onecellforward,stop(X),1)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/onecellforward");
			break;

		case "reactivate":
			var msgstr = "msg(reactivate,dispatch,js,onerotateforward,reactivate(X),1)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/onerotateforward");
			msgstr = "msg(reactivate,dispatch,js,onecellforward,reactivate(X),1)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/onecellforward");
			break;

		case "exploreroom":
			var msgstr = "msg(doExplore,dispatch,js,explorer,doExplore(bound),1)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/explorer");
			break;

		case "exploretable":
			var msgstr = "msg(doExplore,dispatch,js,explorer,doExplore(table),1)";
			console.log("publishMsg forward> " + msgstr);
			mqttUtils.publish(msgstr, "unibo/qak/explorer");
			break;

		default:
			console.log("error, cmd not found in switch case publishMsg, check code.")
	}
}

var publishMsgToRobotmind = function (cmd) {
	var msgstr = "msg(robotCmd,dispatch,js,robotmind,robotCmd(" + cmd + "),1)";
	console.log("publishMsgToRobotmind forward> " + msgstr);
	mqttUtils.publish(msgstr, "unibo/qak/robotmind");
}

var publishMsgToResourceModel = function (target, cmd) {
	var msgstr = "msg(modelChange,dispatch,js,resourcemodel,modelChange(" + target + ", " + cmd + "),1)";
	console.log("publishMsgToResourceModel forward> " + msgstr);
	mqttUtils.publish(msgstr, "unibo/qak/resourcemodel");
}

var changeResourceModelCoap = function (cmd) {
	console.log("coap PUT> " + cmd);
	coapCToRM.coapPut(cmd); //Client to ResourceModel
}

var changeFridgeCoap = function (cmd) {
	console.log("coap PUT> " + cmd);
	coapCToF.coapPut(cmd); //Client to Fridge
}

var changeDishwasherCoap = function (cmd) {
	console.log("coap PUT> " + cmd);
	coapCToD.coapPut(cmd); //Client to Dishwasher
}

var changePantryCoap = function (cmd) {
	console.log("coap PUT> " + cmd);
	coapCToP.coapPut(cmd); //Client to Pantry
}

var publishEmitUserCmd = function (cmd) {
	var eventstr = "msg(userCmd,event,js,none,userCmd(" + cmd + "),1)";
	console.log("emits> " + eventstr);
	mqttUtils.publish(eventstr, "unibo/qak/events");
}

var publishMsgToRobotapplication = function (cmd) {
	var msgstr = "msg(" + cmd + ",dispatch,js,robotmind," + cmd + "(go),1)";
	console.log("publishMsgToRobotapplication forward> " + msgstr);
	mqttUtils.publish(msgstr, "unibo/qak/robotmindapplication");
}

/*
* ====================== REPRESENTATION ================
*/
app.use(function (req, res) {
	console.info("SENDING THE ANSWER " + result + " json:" + req.accepts('josn'));
	try {
		console.log("answer> " + result);
	    /*
	   if (req.accepts('json')) {
	       return res.send(result);		//give answer to curl / postman
	   } else {
	       return res.render('index' );
	   };
	   */
		return res.render('index');
	} catch (e) { console.info("SORRY ..." + e); }
}
);

//app.use(converter());

/*
 * ============ ERROR HANDLING =======
 */

// catch 404 and forward to error handler;
app.use(function (req, res, next) {
	var err = new Error('Not Found');
	err.status = 404;
	next(err);
});

// error handler;
app.use(function (err, req, res, next) {
	// set locals, only providing error in development
	res.locals.message = err.message;
	res.locals.error = req.app.get('env') === 'development' ? err : {};

	// render the error page;
	res.status(err.status || 500);
	res.render('error');
});


/*
 * ========= EXPORTS =======
 */

module.exports = app;