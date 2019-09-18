/*
===============================================================
resourceModel.pl
===============================================================
*/
 
model( actuator, robot,      state(stopped) ).   %% initial state
model( sensor,   sonarRobot, state(unknown) ).   %% initial state

action(robot, move(w)) :- changeModel( actuator, robot, movingForward  ).
action(robot, move(s)) :- changeModel( actuator, robot, movingBackward ).
action(robot, move(a)) :- changeModel( actuator, robot, rotateLeft     ).
action(robot, move(d)) :- changeModel( actuator, robot, rotateRight    ).
action(robot, move(h)) :- changeModel( actuator, robot, stopped        ).

action(sonarRobot, V)  :- changeModel( sensor, sonarRobot, V  ).
 

changeModel( CATEG, NAME, VALUE ) :-
   replaceRule( model(CATEG,NAME,_),  model(CATEG,NAME,state(VALUE)) ),
   showResourceModel.	%% at each change, show the model

showResourceModel :- 
	output("RESOURCE MODEL ---------- "),
	showResources,
	output("--------------------------").
		
showResources :- 
 	model( CATEG, NAME, STATE ),
 	output( model( CATEG, NAME, STATE ) ),
	fail.
showResources.			

output( M ) :- stdout <- println( M ).


initResourceTheory :- output("resourceModel loaded").
:- initialization(initResourceTheory).
		
		
		