/*
===============================================================
robotPosResolver.pl
===============================================================
*/
robotPosX(0).
robotPosY(0).
robotOrientation(0).

convertIntToOrientation(O):-robotOrientation(X), X = 0, O = "sud",!.
convertIntToOrientation(O):-robotOrientation(X), X = 1, O = "ovest",!.
convertIntToOrientation(O):-robotOrientation(X), X = 2, O = "nord",!.
convertIntToOrientation(O):-robotOrientation(X), X = 3, O = "est",!.
actualRobotPos(X,Y,O):-robotPosX(X),robotPosY(Y),convertIntToOrientation(O).

updateRobotPosX(X):-retract(robotPosX(_)), assert(robotPosX(X)).
updateRobotPosY(Y):-retract(robotPosY(_)), assert(robotPosY(Y)).
updateRobotOrientation(O):-retract(robotOrientation(_)), assert(robotOrientation(O)).

fixRobotOrientation:-robotOrientation(O),O >3, updateRobotOrientation(0),!.
fixRobotOrientation:-robotOrientation(O),O <0, updateRobotOrientation(3),!.
fixRobotOrientation.
updateRobotStateFromMove("a"):-!,robotOrientation(O1),O2 is O1-1,updateRobotOrientation(O2),fixRobotOrientation.
updateRobotStateFromMove("d"):-!,robotOrientation(O1),O2 is O1+1,updateRobotOrientation(O2),fixRobotOrientation.
updateRobotStateFromMove("w"):-robotOrientation(0),!,robotPosY(Y),Y2 is Y+1,updateRobotPosY(Y2).
updateRobotStateFromMove("w"):-robotOrientation(1),!,robotPosX(X),X2 is X-1,updateRobotPosX(X2).
updateRobotStateFromMove("w"):-robotOrientation(2),!,robotPosY(Y),Y2 is Y-1,updateRobotPosY(Y2).
updateRobotStateFromMove("w"):-robotOrientation(3),!,robotPosX(X),X2 is X+1,updateRobotPosX(X2).
updateRobotStateFromMove("s"):-robotOrientation(0),!,robotPosY(Y),Y2 is Y-1,updateRobotPosY(Y2).
updateRobotStateFromMove("s"):-robotOrientation(1),!,robotPosX(X),X2 is X+1,updateRobotPosX(X2).
updateRobotStateFromMove("s"):-robotOrientation(2),!,robotPosY(Y),Y2 is Y+1,updateRobotPosY(Y2).
updateRobotStateFromMove("s"):-robotOrientation(3),!,robotPosX(X),X2 is X-1,updateRobotPosX(X2).

/*
===============================================================
obstaclePosResolver.pl
===============================================================
*/
pos(home,0,0).
pos(table,5,3).
pos(fridge,5,0).
pos(pantry,0,4).
pos(dishwasher,4,5).
updatePos(NAME,X,Y):-retract(pos(NAME,_,_)), assert(pos(NAME,X,Y)).