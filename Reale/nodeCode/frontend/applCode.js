/*
frontend/uniboSupports/applCode
*/
const express     	= require('express');
const path         	= require('path');
//const favicon     = require('serve-favicon');
const logger       	= require('morgan');	//see 10.1 of nodeExpressWeb.pdf;
//const cookieParser= require('cookie-parser');
const bodyParser   	= require('body-parser');
const fs           	= require('fs');
const index         = require('./appServer/routes/index');				 
var io              ; 	//Upgrade for socketIo;

//for delegate
const mqttUtils     = require('./uniboSupports/mqttUtils');  
const coap          = require('./uniboSupports/coapClientToResourceModel');  
//require("node-coap-client").CoapClient; 

var app              = express();


// view engine setup;
app.set('views', path.join(__dirname, 'appServer', 'views'));	 
app.set('view engine', 'ejs');

//create a write stream (in append mode) ;
var accessLogStream = fs.createWriteStream(path.join(__dirname, 'morganLog.log'), {flags: 'a'})
app.use(logger("short", {stream: accessLogStream}));

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

app.get('/', function(req, res) {
	res.render("index");
});	

app.get('/robotmodel', function(req, res) {
	res.send( mqttUtils.getrobotmodel() )
});	
app.get('/sonarrobotmodel', function(req, res) {
	res.send( mqttUtils.getsonarrobotmodel() )
});	

app.get('/appl', function(req, res) {
	res.render("indexAppl");
});	

/*
 * ====================== COMMANDS ================
 */
	app.post("/w", function(req, res,next) { handlePostMove("w","moving forward", req,res,next); });	
	app.post("/s", function(req, res,next) { handlePostMove("s","moving backward",req,res,next); });
	app.post("/a", function(req, res,next) { handlePostMove("a","moving left",    req,res,next); });	
	app.post("/d", function(req, res,next) { handlePostMove("d","moving right",   req,res,next); });
	app.post("/h", function(req, res,next) { handlePostMove("h","stopped",        req,res,next); });	
 
  	//APPLICATION
	app.post("/startappl", function(req, res,next) {
  		delegateForAppl( "startAppl", req, res );
  		next();
 	});		
	app.post("/stopappl", function(req, res,next) {
  		delegateForAppl( "stopAppl",  req, res );
  		next();
 	});		

function handlePostMove( cmd, msg, req, res, next ){
		result = "Web server done: " + cmd
 		delegate( cmd, msg, req, res);	
  		next();
} 	
//=================== UTILITIES =========================

var result = "";

app.setIoSocket = function( iosock ){
 	io    = iosock;
 	mqttUtils.setIoSocket(iosock);
	console.log("app SETIOSOCKET io=" + io);
}

function delegate( cmd, newState, req, res ){
 	//publishMsgToRobotmind(cmd);                  //interaction with the robotmind 
	//publishEmitUserCmd(cmd);                     //interaction with the basicrobot
	//publishMsgToResourceModel("robot",cmd);	    //for hexagonal mind
	changeResourceModelCoap(cmd);		            //for hexagonal mind RESTful m2m
 } 
function delegateForAppl( cmd, req, res, next ){
     console.log("app delegateForAppl cmd=" + cmd); 
     result = "Web server delegateForAppl: " + cmd;
 	 publishMsgToRobotapplication( cmd );		     
} 

/*
 * ============ TO THE BUSINESS LOGIC =======
 */
 
var publishMsgToRobotmind = function( cmd ){  
  	var msgstr = "msg(robotCmd,dispatch,js,robotmind,robotCmd("+cmd +"),1)"  ;  
  	console.log("publishMsgToRobotmind forward> "+ msgstr);
   	mqttUtils.publish( msgstr, "unibo/qak/robotmind" );
}

var publishMsgToResourceModel = function( target, cmd ){  
  	var msgstr = "msg(modelChange,dispatch,js,resourcemodel,modelChange("+target+", "+cmd +"),1)"  ;  
  	console.log("publishMsgToResourceModel forward> "+ msgstr); 	
   	mqttUtils.publish( msgstr, "unibo/qak/resourcemodel" );
}

var changeResourceModelCoap = function( cmd ){  
    console.log("coap PUT> "+ cmd);
	coap.coapPut(cmd);
}

var publishEmitUserCmd = function( cmd ){  
 	var eventstr = "msg(userCmd,event,js,none,userCmd("+cmd +"),1)"  ;  
    console.log("emits> "+ eventstr);
 	mqttUtils.publish( eventstr, "unibo/qak/events" );	 
}

var publishMsgToRobotapplication = function (cmd){
   	var msgstr = "msg(" + cmd + ",dispatch,js,robotmind,"+ cmd +"(go),1)"  ;  
  	console.log("publishMsgToRobotapplication forward> "+ msgstr);
   	mqttUtils.publish( msgstr, "unibo/qak/robotmindapplication" );
}

/*
* ====================== REPRESENTATION ================
*/
app.use( function(req,res){
	console.info("SENDING THE ANSWER " + result + " json:" + req.accepts('josn') );
	try{
	    console.log("answer> "+ result  );
	    /*
	   if (req.accepts('json')) {
	       return res.send(result);		//give answer to curl / postman
	   } else {
	       return res.render('index' );
	   };
	   */
	   return res.render('index' );
	}catch(e){console.info("SORRY ..." + e);}
	} 
);

//app.use(converter());

/*
 * ============ ERROR HANDLING =======
 */

// catch 404 and forward to error handler;
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler;
app.use(function(err, req, res, next) {
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