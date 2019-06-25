%% cell(X,Y,STATE).	%%	STATE 0 means obstacle, 1 means visited

%%robotdirection( D ). 		%% D= sud, east, west, north
%%cell( X,Y, STATE )		 

initMap(DIRECTION) :-
	output("initMap  "),
	assign(x,0),
	assign(y,0),
	assert( robotdirection(DIRECTION) ),
	retractall( cell(X,Y,STATE) ), 	%%to clean in case of restart
	addRule( cell(0,0,1) ).		
 
changeDirection:- robotdirection( D ), output( changeDirection( D ) ), changeDirection( D ).
changeDirection( sud   ):- foundPantry,     replaceRule( robotdirection( sud ), robotdirection( east ) ).
changeDirection( east  ):- foundDishwasher, replaceRule( robotdirection( east ), robotdirection( north ) ).
changeDirection( north ):- foundFridge,     replaceRule( robotdirection( north ), robotdirection( west ) ).
changeDirection( west  ):- replaceRule( robotdirection( west ), robotdirection( sud ) ).

foundPantry.
foundDishwasher.
foundFridge :- output("FRIGO TROVATO :)))").

	
updateMapAfterStep :-
	robotdirection( D ),
	updateMap(D,X,Y),output( updateMap(D,X,Y) ),
	addRule( cell(X,Y,1) ).
	
updateMap(sud,X,Y):-
	getVal(x,X),
	getVal(y,LASTY),
	Y is LASTY+1,
	assign(y,Y). 
updateMap(east,X,Y):-
	getVal(y,Y),
	getVal(x,LASTX),
	X is LASTX + 1,
	assign(x,X).
updateMap(north,X,Y):-
	getVal(x,X),
	getVal(y,LASTY),
	Y is LASTY-1,
	assign(y,Y). 
updateMap(west,X,Y):-
	getVal(y,Y),
	getVal(x,LASTX),
	X is LASTX - 1,
	assign(x,X).
   
showMap :- 
	output("%%%%%%%%%%%%%%%%%%%%%% "),
	showCells,
	output("%%%%%%%%%%%%%%%%%%%%%% ").


showCells :-
 	cell( X,Y,STATE ),
 	output( cell( X,Y, STATE ) ),
	fail.
showCells.		
 
output( M ) :- stdout <- println( M ).

%-------------------------------------------------
%  TuProlo FEATURES of the QActor mind
%-------------------------------------------------
dialog( FileName ) :-  
	java_object('javax.swing.JFileChooser', [], Dialog),
	Dialog <- showOpenDialog(_),
	Dialog <- getSelectedFile returns File,
	File <- getName returns FileName. 		 

 
:- initialization(initMap(sud)).
 